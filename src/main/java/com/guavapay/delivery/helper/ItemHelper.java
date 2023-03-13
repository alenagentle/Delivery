package com.guavapay.delivery.helper;

import com.guavapay.delivery.entity.Item;
import com.guavapay.delivery.exception.ItemAlreadyTakenException;
import com.guavapay.delivery.exception.NotFoundException;
import com.guavapay.delivery.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class ItemHelper {

    private final ItemRepository itemRepository;

    public List<Item> findItemsByIds(Collection<Long> ids) {
        List<Item> items = new ArrayList<>();
        ids.forEach(id -> items.add(findItemById(id)));
        return items;
    }

    public Item findItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Item with id %d not found", id)));
        if (item.getOrdering() != null)
            throw new ItemAlreadyTakenException(String.format("Item with id %d is already taken in another ordering", id));
        return item;
    }

}
