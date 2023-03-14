package com.guavapay.delivery.service.impl;

import com.guavapay.delivery.dto.request.DeliveryRequest;
import com.guavapay.delivery.dto.response.DeliveryResponse;
import com.guavapay.delivery.entity.Delivery;
import com.guavapay.delivery.entity.Ordering;
import com.guavapay.delivery.entity.UserData;
import com.guavapay.delivery.entity.enums.RoleName;
import com.guavapay.delivery.exception.NotFoundException;
import com.guavapay.delivery.exception.OrderingAlreadyTakenException;
import com.guavapay.delivery.helper.UserHelper;
import com.guavapay.delivery.mapper.DeliveryMapper;
import com.guavapay.delivery.repository.DeliveryRepository;
import com.guavapay.delivery.service.api.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;
    private final UserHelper userHelper;

    @Override
    @Transactional
    public DeliveryResponse createDelivery(DeliveryRequest deliveryRequest) {
        checkCourierRole(deliveryRequest.getCourierId());
        Delivery delivery = deliveryMapper.mapToEntity(deliveryRequest);
        delivery.getOrderings().forEach(this::checkOrdering);
        delivery.getOrderings().forEach(ordering -> ordering.setDelivery(delivery));
        Delivery savedDelivery = deliveryRepository.save(delivery);
        log.info("New delivery saved. Id = {}", savedDelivery.getId());
        return deliveryMapper.mapToResponse(savedDelivery);
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
