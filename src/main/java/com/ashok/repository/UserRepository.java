package com.ashok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashok.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
