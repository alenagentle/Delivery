package com.guavapay.delivery.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderingRequest {

    private List<@Positive(message = "{item-id.positive}") Long> ItemIds;

    @NotBlank(message = "{destination.not-blank}")
    private String destination;
}
