package com.guavapay.delivery.service.api;

import com.guavapay.delivery.dto.request.DeliveryRequest;
import com.guavapay.delivery.dto.response.DeliveryResponse;

public interface DeliveryService {

    DeliveryResponse createDelivery(DeliveryRequest deliveryRequest);

}
