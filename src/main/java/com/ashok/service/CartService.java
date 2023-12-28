package com.ashok.service;

import java.util.List;

import com.ashok.payload.CartDTO;



public interface CartService {
CartDTO addProductToCart(Long cartId, Long productId, Integer quantity);
	
	List<CartDTO> getAllCarts();
}
