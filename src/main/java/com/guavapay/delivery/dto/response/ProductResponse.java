package com.guavapay.delivery.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    private Long id;
    private String name;
    private Integer amount;
    private Double price;

}
