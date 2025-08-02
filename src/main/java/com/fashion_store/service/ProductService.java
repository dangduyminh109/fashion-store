package com.fashion_store.service;

import com.fashion_store.Utils.GenerateSlugUtils;
import com.fashion_store.dto.request.ProductUpdateRequest;
import com.fashion_store.dto.request.VariantCreateRequest;
import com.fashion_store.dto.request.ProductCreateRequest;
import com.fashion_store.dto.request.VariantUpdateRequest;
import com.fashion_store.dto.response.ProductResponse;
import com.fashion_store.entity.*;
import com.fashion_store.entity.Product;
import com.fashion_store.exception.AppException;
import com.fashion_store.exception.ErrorCode;
import com.fashion_store.mapper.ProductMapper;
import com.fashion_store.mapper.VariantMapper;
import com.fashion_store.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService extends GenerateService<Product, Long> {
    ProductRepository productRepository;
    BrandRepository brandRepository;
    CategoryRepository categoryRepository;
    CloudinaryService cloudinaryService;

    ProductMapper productMapper;
    VariantMapper variantMapper;
    private final AttributeValueRepository attributeValueRepository;
    private final VariantRepository variantRepository;

    @Override
    JpaRepository<Product, Long> getRepository() {
        return productRepository;
    }

    public ProductResponse create(ProductCreateRequest request) {
        Product product = productMapper.toProduct(request);
        Product finalProduct = product;
        // kiểm tra sku có bị trùng hay đã tồn tại không
        Map<String, Long> skuCountMap = request.getVariantList().stream()
                .collect(Collectors.groupingBy(VariantCreateRequest::getSku, Collectors.counting()));
        skuCountMap.forEach((sku, count) -> {
            if (count > 1 || variantRepository.existsBySku(sku)) {
                throw new AppException(ErrorCode.EXISTED);
            }
        });

        // variant
        List<Variant> listVariant = new ArrayList<>();
        request.getVariantList().forEach(variantItem -> {
            Variant variant = variantMapper.toVariant(variantItem);
            if (variantItem.getAttributeValue() != null) {
                // set attributeValue
                List<Long> ListAttributeValueId = variantItem.getAttributeValue().stream()
                        .distinct().toList();
                Set<AttributeValue> listAttributeValue = new HashSet<>();
                ListAttributeValueId.forEach(attributeValueId -> {
                    AttributeValue attributeValue = attributeValueRepository.findById(attributeValueId)
                            .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
                    listAttributeValue.add(attributeValue);
                    attributeValue.getVariants().add(variant);
                });
                variant.setAttributeValues(listAttributeValue);
            }

            variant.setProduct(finalProduct);
            listVariant.add(variant);
        });
        product.setVariants(listVariant);

        // brand
        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
            product.setBrand(brand);
        }

        // category
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
            product.setCategory(category);
        }

        // slug
        String baseSlug = GenerateSlugUtils.generateSlug(product.getName());
        List<Product> existing = productRepository.findBySlugStartingWith(baseSlug);
        String finalSlug = baseSlug;
        if (!existing.isEmpty()) {
            finalSlug = baseSlug + "-" + existing.size();
        }
        product.setSlug(finalSlug);

        // handle image
        List<ProductImage> listProductImage = new ArrayList<>();
        request.getImages().forEach(img -> {
            if (!img.isEmpty()) {
                try {
                    String imageUrl = cloudinaryService.uploadFile(img);
                    ProductImage productImage = ProductImage.builder()
                            .url(imageUrl)
                            .product(finalProduct)
                            .build();
                    listProductImage.add(productImage);
                } catch (IOException e) {
                    throw new AppException(ErrorCode.FILE_SAVE_FAILED);
                }
            }
        });
        product.setProductImages(listProductImage);

        product = productRepository.save(product);
        return productMapper.toProductResponse(product);
    }


    public List<ProductResponse> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getInfo(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        return productMapper.toProductResponse(product);
    }

    public ProductResponse update(ProductUpdateRequest request, Long id) {
        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        Product finalProduct = oldProduct;

        // variant
        // lấy danh sách id variant từ request
        List<Long> listVariantIdRequest = request.getVariantList().stream()
                .map(VariantUpdateRequest::getId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        // kiểm tra nếu có id thì id đó phải thuộc danh sách id củ mới cho cập nhật
        List<Long> oldVariantIds = oldProduct.getVariants().stream().map(BaseModel::getId).toList();
        listVariantIdRequest.forEach(idVariantUpdate -> {
            if (!oldVariantIds.contains(idVariantUpdate))
                throw new AppException(ErrorCode.VARIANT_ID_MISMATCH);
        });

        // kiểm tra sku có bị trùng hay đã tồn tại trong db không
        Map<String, Long> skuCountMap = request.getVariantList().stream()
                .collect(Collectors.groupingBy(VariantUpdateRequest::getSku, Collectors.counting()));

        skuCountMap.forEach((sku, count) -> {
            if (count > 1) {
                throw new AppException(ErrorCode.EXISTED);
            }
        });
        for (VariantUpdateRequest variant : request.getVariantList()) {
            if (variant.getId() != null) {
                if (variantRepository.existsBySkuAndIdNot(variant.getSku(), variant.getId())) {
                    throw new AppException(ErrorCode.EXISTED);
                }
            } else {
                if (variantRepository.existsBySku(variant.getSku())) {
                    throw new AppException(ErrorCode.EXISTED);
                }
            }
        }

        // map các thông tin cơ bản
        productMapper.updateProduct(oldProduct, request);

        List<Variant> newListVariant = new ArrayList<>();

        oldProduct.getVariants().clear();

        request.getVariantList().forEach(variantItem -> {
            if (variantItem.getId() != null) {
                Variant updateVariantItem = variantRepository.findById(variantItem.getId())
                        .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
                variantMapper.updateVariant(updateVariantItem, variantItem);

                List<AttributeValue> newAttributeValueList = new ArrayList<>();
                if (variantItem.getAttributeValue() != null) {
                    // thêm attribute value có trong danh sách attribute value id request
                    variantItem.getAttributeValue().forEach(attributeValueId -> {
                        // trường hợp from gửi lên là variantList[0].AttributeValue[1] mà không có variantList[0].AttributeValue[0] thì continue
                        if (attributeValueId != null) {
                            AttributeValue newAttributeValue = attributeValueRepository.findById(attributeValueId)
                                    .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
                            newAttributeValue.getVariants().add(updateVariantItem);
                            newAttributeValueList.add(newAttributeValue);
                        }
                    });
                }
                // xóa tất cả (lúc này các id trước đó không có trong list id request sẽ bị xóa)
                updateVariantItem.getAttributeValues().clear();
                // cập nhật lại toàn bộ ListVariant;
                updateVariantItem.getAttributeValues().addAll(newAttributeValueList);
                updateVariantItem.setProduct(finalProduct);
                newListVariant.add(updateVariantItem);
            } else {
                Variant newVariant = variantMapper.toVariant(variantItem);
                if (variantItem.getAttributeValue() != null) {
                    // set attributeValue
                    List<Long> ListAttributeValueId = variantItem.getAttributeValue().stream()
                            .distinct().toList();
                    Set<AttributeValue> listAttributeValue = new HashSet<>();
                    ListAttributeValueId.forEach(attributeValueId -> {
                        AttributeValue attributeValue = attributeValueRepository.findById(attributeValueId)
                                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
                        listAttributeValue.add(attributeValue);
                        attributeValue.getVariants().add(newVariant);
                    });
                    newVariant.setAttributeValues(listAttributeValue);
                }
                newVariant.setProduct(finalProduct);
                newListVariant.add(newVariant);
            }
        });

        oldProduct.getVariants().addAll(newListVariant);

        // brand
        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
            oldProduct.setBrand(brand);
        }

        // category
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
            oldProduct.setCategory(category);
        }

        // slug
        String baseSlug = GenerateSlugUtils.generateSlug(oldProduct.getName());
        List<Product> existing = productRepository.findBySlugStartingWith(baseSlug)
                .stream().filter(item -> !item.getId().equals(id)).toList();
        String finalSlug = baseSlug;
        if (!existing.isEmpty()) {
            finalSlug = baseSlug + "-" + existing.size();
        }
        oldProduct.setSlug(finalSlug);

        // handle image
        List<ProductImage> listProductImage = new ArrayList<>();
        if (oldProduct.getProductImages() != null && request.getListDeletedImage() != null) {
            oldProduct.getProductImages().forEach(img -> {
                if (!request.getListDeletedImage().contains(img.getUrl())) {
                    listProductImage.add(img);
                }
            });
            oldProduct.getProductImages().clear();
        }

        request.getImages().forEach(img -> {
            if (!img.isEmpty()) {
                try {
                    String imageUrl = cloudinaryService.uploadFile(img);
                    ProductImage productImage = ProductImage.builder()
                            .url(imageUrl)
                            .product(finalProduct)
                            .build();
                    listProductImage.add(productImage);
                } catch (IOException e) {
                    throw new AppException(ErrorCode.FILE_SAVE_FAILED);
                }
            }
        });
        if (oldProduct.getProductImages() == null) {
            oldProduct.setProductImages(listProductImage);
        } else {
            oldProduct.getProductImages().addAll(listProductImage);
        }

        oldProduct = productRepository.save(oldProduct);
        return productMapper.toProductResponse(oldProduct);
    }

    public void status(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        try {
            product.setStatus(product.getStatus() == null || !product.getStatus());
            productRepository.save(product);
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_EXCEPTION);
        }
    }
}