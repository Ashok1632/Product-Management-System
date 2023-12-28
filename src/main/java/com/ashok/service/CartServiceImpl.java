package com.ashok.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashok.entity.Cart;
import com.ashok.entity.CartItem;
import com.ashok.entity.Product;
import com.ashok.exception.ResourceNotFoundException;
import com.ashok.payload.CartDTO;
import com.ashok.payload.ProductDTO;
import com.ashok.repository.CartItemRepository;
import com.ashok.repository.CartRepository;
import com.ashok.repository.ProductRepository;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CartItemRepository cartItemRepository;
	

	@Override
	public CartDTO addProductToCart(Long cartId, Long productId, Integer quantity) {
		// TODO Auto-generated method stub
		  Cart cart = cartRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Cart","cartId", cartId));
		Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId ));
		CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);
		CartItem newCartItem = new CartItem();

		newCartItem.setProduct(product);
		newCartItem.setCart(cart);
		newCartItem.setQuantity(quantity);
		newCartItem.setDiscount(product.getDiscount());
		newCartItem.setProductPrice(product.getSpecialPrice());
		cartItemRepository.save(newCartItem);

		product.setQuantity(product.getQuantity() - quantity);

		cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));

		CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

		List<ProductDTO> productDTOs = cart.getCartItems().stream()
				.map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());

		cartDTO.setProducts(productDTOs);

		return cartDTO;

		

	}

	@Override
	public List<CartDTO> getAllCarts() {
		// TODO Auto-generated method stub
		return null;
	}

}
