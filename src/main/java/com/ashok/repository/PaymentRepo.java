package com.ashok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashok.entity.Payment;


@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long>{

}
