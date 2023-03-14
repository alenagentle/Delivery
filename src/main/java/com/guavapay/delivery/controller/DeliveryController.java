package com.guavapay.delivery.controller;

import com.guavapay.delivery.dto.request.DeliveryRequest;
import com.guavapay.delivery.dto.response.DeliveryResponse;
import com.guavapay.delivery.service.api.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public List<DeliveryResponse> findAllDeliveries() {
        return deliveryService.findAllDeliveries();
    }

    @PreAuthorize("hasRole('ROLE_COURIER')")
    @GetMapping("/courier")
    public List<DeliveryResponse> findAllDeliveriesAssignedToCourier() {
        return deliveryService.findAllDeliveriesAssignedToCourier();
    }

    @GetMapping("/user")
    public List<DeliveryResponse> findAllDeliveriesAssignedToUser() {
        return deliveryService.findAllDeliveriesAssignedToUser();
    }

}
