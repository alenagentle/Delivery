package com.guavapay.delivery.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
public class ProductRequestTest {

    private Validator validator;
    private Set<ConstraintViolation<ProductRequest>> violations;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void whenEmptyNameProduct_thenValidationFails() {
        var productRequest = buildProductWithEmptyName();
        violations = validator.validate(productRequest);
        assertEquals(1, violations.size());
        assertEquals("{name.not-blank}", violations.iterator()
                .next()
                .getMessage());
    }

    @Test
    public void whenZeroAmountProduct_thenValidationFails() {
        var productRequest = buildProductWithZeroAmount();
        violations = validator.validate(productRequest);
        assertEquals(1, violations.size());
        assertEquals("{amount.positive}", violations.iterator()
                .next()
                .getMessage());
    }

    private ProductRequest buildProductWithZeroAmount() {
        ProductRequest request = new ProductRequest();
        request.setName("name");
        request.setAmount(0);
        request.setPrice(500.0);
        return request;
    }

    private ProductRequest buildProductWithEmptyName() {
        ProductRequest request = new ProductRequest();
        request.setName("");
        request.setAmount(2);
        request.setPrice(500.0);
        return request;
    }
}

