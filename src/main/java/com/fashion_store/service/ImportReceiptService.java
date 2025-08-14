package com.fashion_store.service;

import com.fashion_store.dto.request.ImportReceiptRequest;
import com.fashion_store.dto.request.ImportReceiptUpdateRequest;
import com.fashion_store.dto.response.ImportReceiptResponse;
import com.fashion_store.entity.*;
import com.fashion_store.exception.AppException;
import com.fashion_store.exception.ErrorCode;
import com.fashion_store.mapper.ImportItemMapper;
import com.fashion_store.mapper.ImportReceiptMapper;
import com.fashion_store.repository.ImportReceiptRepository;
import com.fashion_store.repository.SupplierRepository;
import com.fashion_store.repository.VariantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImportReceiptService extends GenerateService<ImportReceipt, Long> {
    ImportReceiptRepository importReceiptRepository;
    SupplierRepository supplierRepository;
    VariantRepository variantRepository;
    ImportReceiptMapper importReceiptMapper;
    ImportItemMapper importItemMapper;

    @Override
    JpaRepository<ImportReceipt, Long> getRepository() {
        return importReceiptRepository;
    }

    public ImportReceiptResponse create(ImportReceiptRequest request) {
        ImportReceipt importReceipt = importReceiptMapper.toImportReceipt(request);
        ImportReceipt finalImportReceipt = importReceipt;
        if (request.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
            importReceipt.setSupplier(supplier);
        }

        List<ImportItem> importItemList = new ArrayList<>();
        request.getImportItemList().forEach(item -> {
            ImportItem importItem = importItemMapper.toImportItem(item);
            Variant variant = variantRepository.findBySku(item.getSku())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
            // tăng tồn kho
            int newInventory = variant.getInventory() + item.getQuantity();
            variant.setInventory(newInventory);

            importItem.setReceipt(finalImportReceipt);
            importItem.setVariant(variant);
            importItemList.add(importItem);

            variantRepository.save(variant);
        });
        importReceipt.setImportItemList(importItemList);

        importReceipt = importReceiptRepository.save(importReceipt);
        return importReceiptMapper.toImportReceiptResponse(importReceipt);
    }

    public List<ImportReceiptResponse> getAll(boolean deleted) {
        return importReceiptRepository.findAll()
                .stream()
                .filter(item -> item.getIsDeleted() == deleted)
                .map(importReceiptMapper::toImportReceiptResponse)
                .collect(Collectors.toList());
    }

    public ImportReceiptResponse getInfo(Long id) {
        ImportReceipt importReceipt = importReceiptRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        return importReceiptMapper.toImportReceiptResponse(importReceipt);
    }

    public ImportReceiptResponse update(ImportReceiptUpdateRequest request, Long id) {
        ImportReceipt importReceipt = importReceiptRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        importReceiptMapper.updateImportReceipt(importReceipt, request);
        if (request.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
            importReceipt.setSupplier(supplier);
        }
        importReceipt = importReceiptRepository.save(importReceipt);
        return importReceiptMapper.toImportReceiptResponse(importReceipt);
    }
}
