package com.fashion_store.service;

import com.fashion_store.dto.address.request.AddressRequest;
import com.fashion_store.dto.address.request.AddressUpdateRequest;
import com.fashion_store.dto.address.response.AddressResponse;
import com.fashion_store.entity.Address;
import com.fashion_store.entity.Customer;
import com.fashion_store.exception.AppException;
import com.fashion_store.exception.ErrorCode;
import com.fashion_store.mapper.AddressMapper;
import com.fashion_store.repository.AddressRepository;
import com.fashion_store.repository.CustomerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressService extends GenerateService<Address, Long> {
    AddressRepository addressRepository;
    AddressMapper addressMapper;
    CustomerRepository customerRepository;
    private RestTemplate restTemplate;

    @Override
    JpaRepository<Address, Long> getRepository() {
        return addressRepository;
    }

    public AddressResponse create(AddressRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        if (request.getIsDefault()) {
            List<Address> addresses = customer.getAddresses();
            addresses.forEach(address -> address.setIsDefault(false));
            addressRepository.saveAll(addresses);
        }
        Address address = addressMapper.toAddress(request);
        address.setCustomer(customer);
        address = addressRepository.save(address);
        return addressMapper.toAddressResponse(address);
    }

    public AddressResponse getInfo(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        return addressMapper.toAddressResponse(address);
    }

    public AddressResponse update(AddressUpdateRequest request, Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        // Kiểm tra address này có thuộc customer ID trong request không
        if (!address.getCustomer().getId().equals(request.getCustomerId())) {
            throw new AppException(ErrorCode.INVALID_CUSTOMER_ID);
        }

        if (request.getIsDefault()) {
            Customer customer = address.getCustomer();
            customer.getAddresses().forEach(addr -> addr.setIsDefault(false));
            addressRepository.saveAll(customer.getAddresses());
        }

        addressMapper.updateAddress(address, request);
        address = addressRepository.save(address);
        return addressMapper.toAddressResponse(address);
    }

    @Override
    public void destroy(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));

        Customer customer = address.getCustomer();
        addressRepository.delete(address);
        List<Address> remainingAddresses = addressRepository.findAllByCustomerId(customer.getId());

        if (!remainingAddresses.isEmpty()) {
            Address firstAddress = remainingAddresses.getFirst();
            firstAddress.setIsDefault(true);
            addressRepository.save(firstAddress);
        }
    }

    // lấy data địa chỉ từ api ngoài
    public String getProvinces() {
        String url = "https://provinces.open-api.vn/api/p/";
        return restTemplate.getForObject(url, String.class);
    }

    public String getDistricts(String provinceCode) {
        String url = "https://provinces.open-api.vn/api/p/" + provinceCode + "?depth=2";
        return restTemplate.getForObject(url, String.class);
    }

    public String getWards(String districtCode) {
        String url = "https://provinces.open-api.vn/api/d/" + districtCode + "?depth=2";
        return restTemplate.getForObject(url, String.class);
    }
}
