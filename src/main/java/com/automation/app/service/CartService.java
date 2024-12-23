package com.automation.app.service;

import com.automation.app.entity.Cart;
import com.automation.app.entity.CartItem;
import com.automation.app.entity.CartItemDto;
import com.automation.app.feign.ProductServiceClient;
import com.automation.app.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    ProductServiceClient productServiceClient;

    public Cart addItemToCart(Long userId, Integer productId, Integer quantity) {
        // Retrieve the user's cart
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        Cart cart = optionalCart.orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            return cartRepository.save(newCart);
        });

        // Check if the item is already in the cart
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            // Update quantity if the item already exists
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            // Add a new item to the cart
            CartItem newItem = new CartItem();
            newItem.setProductId(productId);
            newItem.setQuantity(quantity);
            cart.addItem(newItem);
        }

        return cartRepository.save(cart);  // Save changes to the cart
    }

    public Cart removeItemFromCart(Long userId, Integer productId) {
        // Retrieve the user's cart
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()) {
            throw new RuntimeException("Cart not found");
        }

        Cart cart = optionalCart.get();

        // Remove the item from the cart
        cart.getItems().removeIf(item -> item.getProductId().equals(productId));

        return cartRepository.save(cart);
    }

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

	public List<CartItemDto> getCartItemsByUserId(Long userId) {
		Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
		List<CartItem> cartItems = cart.getItems();
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        
        for (CartItem item : cartItems) {
        	
        	CartItemDto dto = new CartItemDto();
        	dto.setProductId(item.getProductId());
        	dto.setProductName(productServiceClient.getProductById(item.getProductId()).getName());
        	dto.setPrice(productServiceClient.getProductById(item.getProductId()).getPrice());
        	dto.setQuantity(item.getQuantity());
        	
        	cartItemDtos.add(dto);
        	
        }
        
        return cartItemDtos;
	}
}
