package com.guavapay.delivery.controller;

import com.guavapay.delivery.dto.request.ItemRequest;
import com.guavapay.delivery.dto.response.ItemResponse;
import com.guavapay.delivery.service.api.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@CrossOrigin
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ItemResponse createProduct(@RequestBody @Valid ItemRequest request) {
        return itemService.createItem(request);
    }

}
