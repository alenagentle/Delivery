package com.guavapay.delivery.service;

import com.guavapay.delivery.dto.request.OrderingRequest;
import com.guavapay.delivery.entity.Item;
import com.guavapay.delivery.entity.Ordering;
import com.guavapay.delivery.entity.Product;
import com.guavapay.delivery.entity.UserData;
import com.guavapay.delivery.exception.ItemAlreadyTakenException;
import com.guavapay.delivery.helper.ItemHelper;
import com.guavapay.delivery.helper.OrderingHelper;
import com.guavapay.delivery.helper.UserHelper;
import com.guavapay.delivery.mapper.OrderingMapper;
import com.guavapay.delivery.mapper.OrderingMapperImpl;
import com.guavapay.delivery.repository.OrderingRepository;
import com.guavapay.delivery.service.impl.OrderingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest()
class OrderingServiceTest {

    private final ItemHelper itemHelper = mock(ItemHelper.class);
    private final OrderingHelper orderingHelper = mock(OrderingHelper.class);
    private final UserHelper userHelper = mock(UserHelper.class);
    private final OrderingRepository orderingRepository = mock(OrderingRepository.class);
    private OrderingMapper orderingMapper;
    private OrderingServiceImpl orderingService;

    @BeforeEach
    void setup() {
        orderingMapper = new OrderingMapperImpl();
        orderingMapper.setItemHelper(itemHelper);
        orderingMapper.setUserHelper(userHelper);

        orderingService = new OrderingServiceImpl(
                orderingMapper,
                orderingRepository,
                orderingHelper,
                userHelper);
    }

    private final List<Long> itemsAssignedToOrdering = List.of(3L, 5L, 6L);
    private final Long itemId = 1L;

    @Test
    void whenItemIdsIsNotTaken() {
        OrderingRequest request = new OrderingRequest();
        request.setItemIds(List.of(1L, 2L));
        when(itemHelper.findItemsByIds(request.getItemIds())).thenReturn(buildItems(request.getItemIds()));
        when(userHelper.getCurrentUserData()).thenReturn(buildUser());
        when(orderingRepository.save(any())).thenReturn(buildOrdering());
        orderingService.createOrdering(request);
    }

    @Test
    void whenItemIdsIsTaken_thenItemAlreadyTakenException() {
        OrderingRequest request = new OrderingRequest();
        request.setItemIds(List.of(3L));
        when(itemHelper.findItemsByIds(request.getItemIds())).thenReturn(buildItems(request.getItemIds()));
        when(userHelper.getCurrentUserData()).thenReturn(buildUser());
        when(orderingRepository.save(any())).thenReturn(buildOrdering());
        ItemAlreadyTakenException thrown = assertThrows(
                ItemAlreadyTakenException.class,
                () -> orderingService.createOrdering(request));
        assertTrue(thrown.getMessage()
                .contentEquals(String.format("Item with id %d is already taken in another ordering", itemId)));
    }

    @Test
    void whenCalculateCost() {
        OrderingRequest request = new OrderingRequest();
        request.setItemIds(List.of(3L));
        when(itemHelper.findItemsByIds(request.getItemIds())).thenReturn(buildItems(request.getItemIds()));
        when(userHelper.getCurrentUserData()).thenReturn(buildUser());
        when(orderingRepository.save(any())).thenReturn(buildOrdering());
        Double expectedPrice = 1000.0;
        var actualOrdering = orderingMapper.mapToEntity(request);
        assertEquals(expectedPrice, actualOrdering.getCost());
    }

    private List<Item> buildItems(List<Long> requestItemsIds) {
        List<Item> items = new ArrayList<>();
        requestItemsIds.forEach(itemId -> items.add(buildItem(itemId)));
        return items;
    }

    private Item buildItem(Long id) {
        Item item = new Item();
        if (itemsAssignedToOrdering.contains(id))
            item.setOrdering(new Ordering());
        item.setId(itemId);
        item.setUser(buildUser());
        item.setAmount(2);
        item.setProduct(buildProduct());
        return item;
    }

    private Ordering buildOrdering() {
        Ordering savedOrdering = new Ordering();
        savedOrdering.setId(1L);
        return savedOrdering;
    }

    private Product buildProduct() {
        Product product = new Product();
        product.setName("pizza");
        product.setPrice(500.0);
        return product;
    }

    private UserData buildUser() {
        UserData userData = new UserData();
        userData.setEmail("email");
        userData.setPassword("passwd");
        return userData;
    }

}