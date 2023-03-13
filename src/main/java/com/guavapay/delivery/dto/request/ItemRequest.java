package com.guavapay.delivery.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequest {

    @Positive(message = "{product-id.positive}")
    private Long idProduct;

    @Positive(message = "{amount.positive}")
    private Integer amount;

}
