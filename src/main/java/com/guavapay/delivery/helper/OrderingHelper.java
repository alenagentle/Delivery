package com.guavapay.delivery.helper;

import com.guavapay.delivery.entity.Ordering;
import com.guavapay.delivery.exception.NotFoundException;
import com.guavapay.delivery.repository.OrderingRepository;
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
public class OrderingHelper {

    private final OrderingRepository orderingRepository;

    public List<Ordering> findOrderingsByIds(Collection<Long> ids) {
        List<Ordering> orderings = new ArrayList<>();
        ids.forEach(id -> orderings.add(findOrderingById(id)));
        return orderings;
    }

    public Ordering findOrderingById(Long id) {
        return orderingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Ordering with id %d not found", id)));
    }
}
