package com.guavapay.delivery.dto.request;

import com.guavapay.delivery.entity.enums.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryRequest {

    private DeliveryStatus status;
    private String mapsUrl;

}
