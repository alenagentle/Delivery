package com.guavapay.delivery.controller;

import com.guavapay.delivery.dto.request.DeliveryRequest;
import com.guavapay.delivery.dto.response.DeliveryResponse;
import com.guavapay.delivery.service.api.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
@CrossOrigin
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public DeliveryResponse createDelivery(DeliveryRequest deliveryRequest) {
        return deliveryService.createDelivery(deliveryRequest);
    }

}
