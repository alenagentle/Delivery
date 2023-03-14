package com.guavapay.delivery.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResponse {

    private Long id;
    private Long productId;
    private String name;
    private Integer amount;
    private Long userId;

}
