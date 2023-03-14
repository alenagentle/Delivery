package com.guavapay.delivery.repository;

import com.guavapay.delivery.entity.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OrderingRepository extends JpaRepository<Ordering, Long> {

    List<Ordering> findOrderingByIdIn(Collection<Long> id);

}
