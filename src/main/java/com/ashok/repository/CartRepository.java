package com.ashok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ashok.entity.Cart;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	@Query("SELECT c FROM Cart c WHERE c.user.email = ?1 AND c.id = ?2")
	Cart findCartByEmailAndCartId(String email, Long cartId);
}
