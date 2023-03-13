package com.guavapay.delivery.service.impl;

import com.guavapay.delivery.dto.request.ProductRequest;
import com.guavapay.delivery.dto.response.ProductResponse;
import com.guavapay.delivery.entity.Product;
import com.guavapay.delivery.mapper.ProductMapper;
import com.guavapay.delivery.repository.ProductRepository;
import com.guavapay.delivery.service.api.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productMapper.mapToEntity(productRequest);
        Product savedProduct = productRepository.save(product);
        log.info("New product saved. Id = {}", savedProduct.getId());
        return productMapper.mapToResponse(savedProduct);
    }

}
