package com.guavapay.delivery.service.impl;

import com.guavapay.delivery.dto.request.OrderingRequest;
import com.guavapay.delivery.dto.response.OrderingResponse;
import com.guavapay.delivery.entity.Item;
import com.guavapay.delivery.entity.Ordering;
import com.guavapay.delivery.entity.UserData;
import com.guavapay.delivery.entity.enums.DeliveryStatus;
import com.guavapay.delivery.entity.enums.OrderingStatus;
import com.guavapay.delivery.exception.ItemAlreadyTakenException;
import com.guavapay.delivery.helper.OrderingHelper;
import com.guavapay.delivery.helper.UserHelper;
import com.guavapay.delivery.mapper.OrderingMapper;
import com.guavapay.delivery.repository.OrderingRepository;
import com.guavapay.delivery.service.api.OrderingService;
import com.guavapay.delivery.service.enums.OrderingOperationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderingServiceImpl implements OrderingService {

    private final OrderingMapper orderingMapper;
    private final OrderingRepository orderingRepository;
    private final OrderingHelper orderingHelper;
    private final UserHelper userHelper;

    @Override
    @Transactional
    public OrderingResponse createOrdering(OrderingRequest orderingRequest) {
        Ordering ordering = orderingMapper.mapToEntity(orderingRequest);
        ordering.getItems().forEach(item -> userHelper.checkUserAccess(item.getUser()));
        ordering.getItems().forEach(this::checkItem);
        ordering.getItems().forEach(item -> item.getProduct()
                .setAmount(getActualProductAmount(item, OrderingOperationStatus.STATUS_CREATE)));
        Ordering savedOrdering = orderingRepository.save(ordering);
        ordering.getItems().forEach(item -> item.setOrdering(savedOrdering));
        log.info("New ordering saved. Id = {}", savedOrdering.getId());
        return orderingMapper.mapToResponse(savedOrdering);
    }

    @Override
    @Transactional
    public OrderingResponse updateOrdering(Long id, OrderingRequest orderingRequest) {
        Ordering ordering = orderingHelper.findOrderingById(id);
        userHelper.checkUserAccess(ordering.getUser());
        checkAndUpdateFields(ordering, orderingRequest);
        Ordering savedOrdering = orderingRepository.save(ordering);
        log.info("Ordering with id {} updated", id);
        return orderingMapper.mapToResponse(savedOrdering);
    }

    @Override
    @Transactional
    public OrderingResponse cancelOrdering(Long id) {
        Ordering ordering = orderingHelper.findOrderingById(id);
        userHelper.checkUserAccess(ordering.getUser());
        ordering.setOrderingStatus(OrderingStatus.STATUS_CANCELLED);
        if (ordering.getDelivery() != null)
            ordering.getDelivery().setDeliveryStatus(DeliveryStatus.STATUS_CANCELED);
        ordering.getItems().forEach(item -> item.getProduct()
                .setAmount(getActualProductAmount(item, OrderingOperationStatus.STATUS_CANCEL)));
        Ordering savedOrdering = orderingRepository.save(ordering);
        log.info("Ordering with id {} canceled", id);
        return orderingMapper.mapToResponse(savedOrdering);
    }

    @Override
    @Transactional
    public List<OrderingResponse> findAllOrderings() {
        List<Ordering> orderings = orderingRepository.findAll();
        UserData currentUser = userHelper.getCurrentUserData();
        List<Ordering> userOrderingList = orderings.stream()
                .filter(ordering -> ordering.getUser().equals(currentUser)).toList();
        return orderingMapper.mapToResponses(userOrderingList);
    }

    @Override
    @Transactional
    public OrderingResponse findOrderingById(Long id) {
        Ordering ordering = orderingHelper.findOrderingById(id);
        userHelper.checkUserAccess(ordering.getUser());
        return orderingMapper.mapToResponse(ordering);
    }

    private int getActualProductAmount(Item item, OrderingOperationStatus status) {
        return switch (status) {
            case STATUS_CREATE -> item.getProduct().getAmount() - item.getAmount();
            case STATUS_CANCEL -> item.getProduct().getAmount() + item.getAmount();
        };
    }

    private void checkItem(Item item) {
        if (item.getOrdering() != null)
            throw new ItemAlreadyTakenException(String.format("Item with id %d is already taken in another ordering", item.getId()));
    }

    private void checkAndUpdateFields(Ordering orderingToUpdate, OrderingRequest request) {
        if (request.getDestination() != null) {
            orderingToUpdate.setDestination(request.getDestination());
        }
    }

}
