package com.guavapay.delivery.controller;

import com.guavapay.delivery.config.group.OnCreateGroup;
import com.guavapay.delivery.dto.request.OrderingRequest;
import com.guavapay.delivery.dto.response.OrderingResponse;
import com.guavapay.delivery.service.api.OrderingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordering")
@RequiredArgsConstructor
@CrossOrigin
public class OrderingController {

    private final OrderingService orderingService;

    @PostMapping
    @Validated(OnCreateGroup.class)
    public OrderingResponse createOrdering(@RequestBody OrderingRequest request) {
        return orderingService.createOrdering(request);
    }

    @PutMapping("/{id}")
    public OrderingResponse updateOrdering(@PathVariable
                                           @Positive Long id,
                                           @RequestBody @Valid OrderingRequest request) {
        return orderingService.updateOrdering(id, request);
    }

    @PutMapping("/cancel/{id}")
    public OrderingResponse cancelOrdering(@PathVariable @Positive Long id) {
        return orderingService.cancelOrdering(id);
    }

}
