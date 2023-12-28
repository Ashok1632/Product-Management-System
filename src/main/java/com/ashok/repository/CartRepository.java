package com.ashok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashok.entity.Cart;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
