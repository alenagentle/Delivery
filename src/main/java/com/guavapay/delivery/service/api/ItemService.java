package com.guavapay.delivery.service.api;

import com.guavapay.delivery.dto.request.ItemRequest;
import com.guavapay.delivery.dto.response.ItemResponse;

import java.util.List;

public interface ItemService {

    ItemResponse createItem(ItemRequest itemRequest);

}
