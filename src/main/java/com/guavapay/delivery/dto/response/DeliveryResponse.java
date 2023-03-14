package com.guavapay.delivery.dto.response;

import com.guavapay.delivery.entity.enums.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeliveryResponse {

    private Long id;
    private Long courierId;
    private List<OrderingResponse> orderings;
    private DeliveryStatus deliveryStatus;
    private String mapsUrl;

}
