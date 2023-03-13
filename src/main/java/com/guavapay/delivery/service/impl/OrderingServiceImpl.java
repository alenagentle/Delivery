package com.guavapay.delivery.service.impl;

import com.guavapay.delivery.dto.request.OrderingRequest;
import com.guavapay.delivery.dto.response.OrderingResponse;
import com.guavapay.delivery.entity.Ordering;
import com.guavapay.delivery.mapper.OrderingMapper;
import com.guavapay.delivery.repository.OrderingRepository;
import com.guavapay.delivery.service.api.OrderingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderingServiceImpl implements OrderingService {

    private final OrderingMapper orderingMapper;
    private final OrderingRepository orderingRepository;

    @Override
    @Transactional
    public OrderingResponse createOrdering(OrderingRequest orderingRequest) {
        Ordering ordering = orderingMapper.mapToEntity(orderingRequest);
        Ordering savedOrdering = orderingRepository.save(ordering);
        ordering.getItems().forEach(item -> item.setOrdering(ordering));
        log.info("New ordering saved. Id = {}", savedOrdering.getId());
        return orderingMapper.mapToResponse(savedOrdering);
    }

}
