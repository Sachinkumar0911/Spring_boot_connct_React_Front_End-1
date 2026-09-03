package com.react.sachin.Payment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TempOrderRepository extends JpaRepository<TempOrder, Long> {
    Optional<TempOrder> findByOrderId(String orderId);
}