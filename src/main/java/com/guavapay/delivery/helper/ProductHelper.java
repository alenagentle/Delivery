package com.guavapay.delivery.helper;

import com.guavapay.delivery.entity.Product;
import com.guavapay.delivery.exception.NotFoundException;
import com.guavapay.delivery.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class ProductHelper {

    private final ProductRepository productRepository;

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id %d not found", id)));
    }

}
