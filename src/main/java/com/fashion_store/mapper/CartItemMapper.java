package com.fashion_store.mapper;

import com.fashion_store.dto.cart.request.CartItemRequest;
import com.fashion_store.dto.cart.response.CartItemResponse;
import com.fashion_store.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = VariantMapper.class)
public interface CartItemMapper {
    CartItem toCartItem(CartItemRequest cartItemRequest);

    CartItemResponse toCartItemResponse(CartItem cartItem);
}
