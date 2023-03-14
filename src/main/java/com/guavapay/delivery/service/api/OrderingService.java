package com.guavapay.delivery.service.api;

import com.guavapay.delivery.dto.request.OrderingRequest;
import com.guavapay.delivery.dto.response.OrderingResponse;

public interface OrderingService {

    OrderingResponse createOrdering(OrderingRequest orderingRequest);
    OrderingResponse updateOrdering(Long id, OrderingRequest orderingRequest);
    OrderingResponse cancelOrdering(Long id);

}
