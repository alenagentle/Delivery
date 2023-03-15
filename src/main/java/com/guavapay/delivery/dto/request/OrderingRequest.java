package com.guavapay.delivery.dto.request;

import com.guavapay.delivery.config.validation.group.OnCreateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderingRequest {

    @NotEmpty(groups = OnCreateGroup.class, message = "{item-id.not-empty}")
    private List<@Positive(message = "{item-id.positive}") Long> itemIds;

    @NotBlank(message = "{destination.not-blank}")
    private String destination;

}
