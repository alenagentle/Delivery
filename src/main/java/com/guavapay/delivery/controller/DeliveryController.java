package com.guavapay.delivery.controller;

import com.guavapay.delivery.dto.request.AssignDeliveryRequest;
import com.guavapay.delivery.dto.request.DeliveryRequest;
import com.guavapay.delivery.dto.response.DeliveryFullResponse;
import com.guavapay.delivery.dto.response.DeliveryResponse;
import com.guavapay.delivery.service.api.DeliveryService;
import jakarta.validation.constraints.Positive;
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
    public DeliveryResponse createDelivery(AssignDeliveryRequest assignDeliveryRequest) {
        return deliveryService.createDelivery(assignDeliveryRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public List<DeliveryFullResponse> findAllDeliveries() {
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

    @GetMapping("/{id}")
    public DeliveryResponse findDeliveryById(@PathVariable
                                             @Positive Long id) {
        return deliveryService.findDeliveryById(id);
    }

    @PreAuthorize("hasRole('ROLE_COURIER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public DeliveryResponse updateDelivery(@PathVariable
                                           @Positive Long id,
                                           @RequestBody DeliveryRequest request) {
        return deliveryService.updateDelivery(id, request);
    }

}
