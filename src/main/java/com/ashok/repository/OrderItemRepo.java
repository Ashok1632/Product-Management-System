package com.ashok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashok.entity.OrderItem;



@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

}
