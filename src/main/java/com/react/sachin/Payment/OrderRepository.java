package com.react.sachin.Payment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
   Optional<Order> findByOrderId(String orderId);
}