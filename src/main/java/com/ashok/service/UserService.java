package com.ashok.service;

import java.util.List;

import com.ashok.payload.UserDTO;
import com.ashok.payload.UserResponse;

public interface UserService  {
UserDTO registerUser(UserDTO userDTO);
	
	UserResponse getAllUsers(Integer pageNumber,Integer pageSize);
	
	UserDTO getUserById(Long userId);
	
	UserDTO updateUser(Long userId, UserDTO userDTO);
	
	String deleteUser(Long userId);
}
