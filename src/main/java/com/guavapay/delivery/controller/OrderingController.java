package com.guavapay.delivery.controller;

import com.guavapay.delivery.dto.request.OrderingRequest;
import com.guavapay.delivery.dto.response.OrderingResponse;
import com.guavapay.delivery.service.api.OrderingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordering")
@RequiredArgsConstructor
@CrossOrigin
public class OrderingController {

    private final OrderingService orderingService;

    @PostMapping
    public OrderingResponse createOrdering(@RequestBody OrderingRequest request) {
        return orderingService.createOrdering(request);
    }
}
