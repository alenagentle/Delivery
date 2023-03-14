package com.guavapay.delivery.service.impl;

import com.guavapay.delivery.dto.request.DeliveryRequest;
import com.guavapay.delivery.dto.response.DeliveryResponse;
import com.guavapay.delivery.entity.Delivery;
import com.guavapay.delivery.entity.Ordering;
import com.guavapay.delivery.entity.UserData;
import com.guavapay.delivery.entity.enums.RoleName;
import com.guavapay.delivery.exception.DifferentAddressesException;
import com.guavapay.delivery.exception.NotFoundException;
import com.guavapay.delivery.exception.OrderingAlreadyTakenException;
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

    @Override
    @Transactional
    public DeliveryResponse createDelivery(DeliveryRequest deliveryRequest) {
        checkCourierRole(deliveryRequest.getCourierId());
        if (deliveryRequest.getOrderIds().size() > 1)
            checkAddresses(deliveryRequest);
        Delivery delivery = deliveryMapper.mapToEntity(deliveryRequest);
        delivery.getOrderings().forEach(this::checkOrdering);
        delivery.getOrderings().forEach(ordering -> ordering.setDelivery(delivery));
        Delivery savedDelivery = deliveryRepository.save(delivery);
        log.info("New delivery saved. Id = {}", savedDelivery.getId());
        return deliveryMapper.mapToResponse(savedDelivery);
    }

    @Override
    @Transactional
    public List<DeliveryResponse> findAllDeliveries() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        log.info("All deliveries found");
        return deliveryMapper.mapToResponses(deliveries);
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

    private void checkAddresses(DeliveryRequest deliveryRequest) {
        List<Ordering> orderingList = orderingHelper.findOrderingsByIds(deliveryRequest.getOrderIds());
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
