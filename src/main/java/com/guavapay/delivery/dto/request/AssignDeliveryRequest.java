package com.guavapay.delivery.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssignDeliveryRequest {

    List<@Positive(message = "{ordering-id.positive}") Long> orderIds;

    @Positive(message = "{user-id.positive}")
    private Long courierId;

}
