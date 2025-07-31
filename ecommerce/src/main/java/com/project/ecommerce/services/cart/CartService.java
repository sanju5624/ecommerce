package com.project.ecommerce.services.cart;

import com.project.ecommerce.dto.AddProductInCartDto;
import com.project.ecommerce.dto.OrderDto;
import com.project.ecommerce.dto.PlaceOrderDTO;
import org.springframework.http.ResponseEntity;

public interface CartService {

    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);

    OrderDto getCartByUserId(Long userId);

    OrderDto increaseProductDto(AddProductInCartDto addProductInCartDto);

    OrderDto decreaseProductDto(AddProductInCartDto addProductInCartDto);

    OrderDto placeOrder(PlaceOrderDTO placeOrderDTO);
}
