package com.fashion_store.mapper;

import com.fashion_store.dto.request.VoucherRequest;
import com.fashion_store.dto.request.VoucherUpdateRequest;
import com.fashion_store.dto.response.VoucherResponse;
import com.fashion_store.entity.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VoucherMapper {
    Voucher toVoucher(VoucherRequest voucherRequest);

    VoucherResponse toVoucherResponse(Voucher voucher);

    void updateVoucher(@MappingTarget Voucher voucher, VoucherUpdateRequest voucherUpdateRequest);
}
