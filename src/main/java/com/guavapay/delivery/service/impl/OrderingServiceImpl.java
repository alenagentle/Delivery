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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        ordering.getItems().forEach(item -> checkUserAccess(item.getUser()));
        ordering.getItems().forEach(this::checkItem);
        Ordering savedOrdering = orderingRepository.save(ordering);
        ordering.getItems().forEach(item -> item.setOrdering(savedOrdering));
        log.info("New ordering saved. Id = {}", savedOrdering.getId());
        return orderingMapper.mapToResponse(savedOrdering);
    }

    @Override
    @Transactional
    public OrderingResponse updateOrdering(Long id, OrderingRequest orderingRequest) {
        Ordering ordering = orderingHelper.findOrderingById(id);
        checkUserAccess(ordering.getUser());
        checkAndUpdateFields(ordering, orderingRequest);
        Ordering savedOrdering = orderingRepository.save(ordering);
        log.info("Ordering with id {} updated", id);
        return orderingMapper.mapToResponse(savedOrdering);
    }

    @Override
    @Transactional
    public OrderingResponse cancelOrdering(Long id) {
        Ordering ordering = orderingHelper.findOrderingById(id);
        checkUserAccess(ordering.getUser());
        ordering.setOrderingStatus(OrderingStatus.STATUS_CANCELLED);
        if (ordering.getDelivery() != null)
            ordering.getDelivery().setDeliveryStatus(DeliveryStatus.STATUS_CANCELED);
        Ordering savedOrdering = orderingRepository.save(ordering);
        log.info("Ordering with id {} canceled", id);
        return orderingMapper.mapToResponse(savedOrdering);
    }

    void checkUserAccess(UserData userData) {
        UserData currentUser = userHelper.getCurrentUserData();
        if (!currentUser.equals(userData))
            throw new AccessDeniedException(String.format("User with id %d cant get access to this ordering", currentUser.getId()));
    }

    private void checkItem(Item item) {
        if (item.getOrdering() != null)
            throw new ItemAlreadyTakenException(String.format("Item with id %d is already taken in another ordering", item.getId()));
    }

    private void checkAndUpdateFields(Ordering orderingToUpdate, OrderingRequest request) {
        Ordering ordering = orderingMapper.mapToEntity(request);
        if (request.getDestination() != null) {
            orderingToUpdate.setDestination(ordering.getDestination());
        }
    }

}
