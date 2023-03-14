package com.guavapay.delivery.service.api;

import com.guavapay.delivery.dto.request.DeliveryRequest;
import com.guavapay.delivery.dto.response.DeliveryResponse;

import java.util.List;

public interface DeliveryService {

    DeliveryResponse createDelivery(DeliveryRequest deliveryRequest);

    List<DeliveryResponse> findAllDeliveries();

    List<DeliveryResponse> findAllDeliveriesAssignedToCourier();

    List<DeliveryResponse> findAllDeliveriesAssignedToUser();



}
