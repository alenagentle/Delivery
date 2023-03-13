package com.guavapay.delivery.controller;

import com.guavapay.delivery.dto.request.ProductRequest;
import com.guavapay.delivery.dto.response.ProductResponse;
import com.guavapay.delivery.service.api.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponse createProduct(@RequestBody @Valid ProductRequest request) {
        return productService.createProduct(request);
    }
}
