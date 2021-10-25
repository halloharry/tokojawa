package com.test.tokoko.repository;

import com.test.tokoko.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByIdAndIsDeleted(Long id, Integer isDeleted);
}
