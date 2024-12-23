package com.automation.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.automation.app.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
    Optional<Cart> findByUserId(Long userId);  // Retrieve the cart by user ID

}
