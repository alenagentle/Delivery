package com.guavapay.delivery.dto.response;

import com.guavapay.delivery.entity.enums.OrderingStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class OrderingResponse {

    private Long id;
    private List<ItemResponse> items;
    private Long userId;
    private String destination;
    private Instant orderingDate;
    private OrderingStatus orderingStatus;
    private Double cost;

}
