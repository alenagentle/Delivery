package com.guavapay.delivery.service.impl;

import com.guavapay.delivery.dto.request.ItemRequest;
import com.guavapay.delivery.dto.response.ItemResponse;
import com.guavapay.delivery.entity.Item;
import com.guavapay.delivery.mapper.ItemMapper;
import com.guavapay.delivery.repository.ItemRepository;
import com.guavapay.delivery.service.api.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    @Transactional
    public ItemResponse createItem(ItemRequest itemRequest) {
        Item item = itemMapper.mapToEntity(itemRequest);
        Item savedItem = itemRepository.save(item);
        log.info("New item saved. Id = {}", savedItem.getId());
        return itemMapper.mapToResponse(savedItem);
    }
}
