package com.guavapay.delivery.helper;

import com.guavapay.delivery.entity.Delivery;
import com.guavapay.delivery.exception.NotFoundException;
import com.guavapay.delivery.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class DeliveryHelper {

    private final DeliveryRepository deliveryRepository;

    public Delivery findDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Delivery with id %d not found", id)));
    }

}
