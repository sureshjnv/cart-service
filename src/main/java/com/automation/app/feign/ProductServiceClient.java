package com.automation.app.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.automation.app.entity.Product;

@FeignClient(name="PRODUCT-SERVICE",url="http://localhost:8080")
public interface ProductServiceClient {
	
	@GetMapping("products/{id}")
	Product getProductById(@PathVariable Integer id);
	
	@PutMapping("products/{id}/reduce-stock")
    ResponseEntity<Product> reduceStock(@PathVariable Integer id, @RequestParam int quantity);

}
