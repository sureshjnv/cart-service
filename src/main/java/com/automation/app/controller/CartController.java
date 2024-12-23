package com.automation.app.controller;

import com.automation.app.entity.Cart;
import com.automation.app.entity.CartItemDto;
import com.automation.app.service.CartService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}/add")
    public ResponseEntity<Cart> addItemToCart(
            @PathVariable Long userId,
            @RequestParam Integer productId,
            @RequestParam Integer quantity) {
        Cart cart = cartService.addItemToCart(userId, productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<Cart> removeItemFromCart(
            @PathVariable Long userId,
            @RequestParam Integer productId) {
        Cart cart = cartService.removeItemFromCart(userId, productId);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    
    @GetMapping("/{userId}/items")
    public ResponseEntity<List<CartItemDto>> getCartItems(@PathVariable Long userId) {
        List<CartItemDto> cartItems = cartService.getCartItemsByUserId(userId);  
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }
    
    
    
     

}
