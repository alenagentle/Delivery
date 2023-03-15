package com.guavapay.delivery.service.api;

import com.guavapay.delivery.dto.request.AssignDeliveryRequest;
import com.guavapay.delivery.dto.request.DeliveryRequest;
import com.guavapay.delivery.dto.response.DeliveryFullResponse;
import com.guavapay.delivery.dto.response.DeliveryResponse;

import java.util.List;

public interface DeliveryService {

    DeliveryResponse createDelivery(AssignDeliveryRequest assignDeliveryRequest);

    List<DeliveryFullResponse> findAllDeliveries();

    List<DeliveryResponse> findAllDeliveriesAssignedToCourier();

    List<DeliveryResponse> findAllDeliveriesAssignedToUser();

    DeliveryResponse updateDelivery(Long id, DeliveryRequest request);

    DeliveryResponse findDeliveryById(Long id);

}
