package com.guavapay.delivery.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "{name.not-blank}")
    private String name;

    @Positive(message = "{amount.positive}")
    private Integer amount;

    @Positive(message = "{price.positive}")
    private Double price;
}
