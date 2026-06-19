package com.unir.relatos.orders.repository;

import com.unir.relatos.orders.repository.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find all orders for a specific user, ordered by creation date descending (most recent first)
     */
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    /**
     * Find recent orders for a user (for profile view)
     */
    List<Order> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);
}
