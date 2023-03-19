package com.guavapay.delivery.service.impl;

import com.guavapay.delivery.dto.request.AssignDeliveryRequest;
import com.guavapay.delivery.dto.request.DeliveryRequest;
import com.guavapay.delivery.dto.response.DeliveryFullResponse;
import com.guavapay.delivery.dto.response.DeliveryResponse;
import com.guavapay.delivery.entity.Delivery;
import com.guavapay.delivery.entity.Ordering;
import com.guavapay.delivery.entity.UserData;
import com.guavapay.delivery.entity.enums.DeliveryStatus;
import com.guavapay.delivery.entity.enums.RoleName;
import com.guavapay.delivery.exception.DifferentAddressesException;
import com.guavapay.delivery.exception.NotFoundException;
import com.guavapay.delivery.exception.OrderingAlreadyTakenException;
import com.guavapay.delivery.helper.DeliveryHelper;
import com.guavapay.delivery.helper.OrderingHelper;
import com.guavapay.delivery.helper.UserHelper;
import com.guavapay.delivery.mapper.DeliveryMapper;
import com.guavapay.delivery.repository.DeliveryRepository;
import com.guavapay.delivery.service.api.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;
    private final UserHelper userHelper;
    private final OrderingHelper orderingHelper;
    private final DeliveryHelper deliveryHelper;

    @Override
    @Transactional
    public DeliveryResponse createDelivery(AssignDeliveryRequest assignDeliveryRequest) {
        checkCourierRole(assignDeliveryRequest.getCourierId());
        if (assignDeliveryRequest.getOrderIds().size() > 1)
            checkAddresses(assignDeliveryRequest);
        Delivery delivery = deliveryMapper.mapToEntity(assignDeliveryRequest);
        delivery.getOrderings().forEach(this::checkOrdering);
        delivery.getOrderings().forEach(ordering -> ordering.setDelivery(delivery));
        Delivery savedDelivery = deliveryRepository.save(delivery);
        log.info("New delivery saved. Id = {}", savedDelivery.getId());
        return deliveryMapper.mapToResponse(savedDelivery);
    }

    @Override
    @Transactional
    public List<DeliveryFullResponse> findAllDeliveries() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        log.info("All deliveries found");
        return deliveryMapper.mapToFullResponses(deliveries);
    }

    @Override
    @Transactional
    public List<DeliveryResponse> findAllDeliveriesAssignedToCourier() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        UserData currentUser = userHelper.getCurrentUserData();
        checkCourierRole(currentUser.getId());
        List<Delivery> couriersDeliveryList = deliveries.stream()
                .filter(delivery -> delivery.getUser().equals(currentUser)).toList();
        return deliveryMapper.mapToResponses(couriersDeliveryList);
    }

    @Override
    @Transactional
    public List<DeliveryResponse> findAllDeliveriesAssignedToUser() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        UserData currentUser = userHelper.getCurrentUserData();
        List<Delivery> usersDeliveryList = deliveries.stream()
                .filter(delivery -> delivery.getOrderings().stream()
                        .anyMatch(ordering -> ordering.getUser().equals(currentUser))).toList();
        return deliveryMapper.mapToResponses(usersDeliveryList);
    }

    @Override
    @Transactional
    public DeliveryResponse updateDelivery(Long id, DeliveryRequest request) {
        Delivery delivery = deliveryHelper.findDeliveryById(id);
        checkAndUpdateFields(delivery, request);
        Delivery savedDelivery = deliveryRepository.save(delivery);
        log.info("Delivery with id {} updated", id);
        return deliveryMapper.mapToResponse(savedDelivery);
    }

    @Override
    @Transactional
    public DeliveryResponse findDeliveryById(Long id) {
        Delivery delivery = deliveryHelper.findDeliveryById(id);
        UserData currentUser = userHelper.getCurrentUserData();
        delivery.getOrderings().stream()
                .filter(ordering -> ordering.getUser().equals(currentUser))
                .findAny()
                .orElseThrow(() -> new NotFoundException(String.format("Delivery for user with id %d not found",
                        currentUser.getId())));
        return deliveryMapper.mapToResponse(delivery);
    }

    private void checkAndUpdateFields(Delivery deliveryToUpdate, DeliveryRequest request) {
        if (request.getStatus() != null) {
            deliveryToUpdate.setDeliveryStatus(request.getStatus());
        }
        if (request.getStatus() != null && (request.getStatus() == DeliveryStatus.STATUS_CANCELED
                || request.getStatus() == DeliveryStatus.STATUS_DELIVERED)) {
            deliveryToUpdate.setMapsUrl(null);
        }
        if (request.getMapsUrl() != null) {
            deliveryToUpdate.setMapsUrl(request.getMapsUrl());
        }
    }

    private void checkAddresses(AssignDeliveryRequest assignDeliveryRequest) {
        List<Ordering> orderingList = orderingHelper.findOrderingsByIds(assignDeliveryRequest.getOrderIds());
        List<String> destinations = new ArrayList<>();
        orderingList.forEach(ordering -> destinations.add(ordering.getDestination()));
        if (Collections.frequency(destinations, destinations.get(0)) != destinations.size())
            throw new DifferentAddressesException("Can't put a few orderings with different addresses into one delivery.");
    }

    private void checkOrdering(Ordering ordering) {
        if (ordering.getDelivery() != null)
            throw new OrderingAlreadyTakenException(String
                    .format("Ordering with id %d is already taken in another delivery", ordering.getId()));
    }

    private void checkCourierRole(Long id) {
        UserData user = userHelper.findUserById(id);
        boolean roleMatches = user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_COURIER));
        if (!roleMatches)
            throw new NotFoundException(String.format("Courier with userId %d not found", id));
    }

}
