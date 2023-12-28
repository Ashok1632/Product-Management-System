package com.ashok.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ashok.entity.Address;
import com.ashok.entity.User;
import com.ashok.exception.APIException;
import com.ashok.exception.ResourceNotFoundException;
import com.ashok.payload.AddressDTO;
import com.ashok.payload.UserDTO;
import com.ashok.payload.UserResponse;
import com.ashok.repository.AddressRepository;
import com.ashok.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AddressRepository addressRepository;

	@Override
	public UserDTO registerUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
try {
		User user = modelMapper.map(userDTO, User.class);

		String country = userDTO.getAddress().getCountry();
		String state = userDTO.getAddress().getState();
		String city = userDTO.getAddress().getCity();
		String pincode = userDTO.getAddress().getPincode();
		String street = userDTO.getAddress().getStreet();
		String buildingName = userDTO.getAddress().getBuildingName();

		Address address = addressRepository.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(
				country, state, city, pincode, street, buildingName);

		if (address == null) {
			address= new Address(country, state, city, pincode, street, buildingName);

			address = addressRepository.save(address);
			
		}
			user.setAddresses(List.of(address));

		User registeredUser = userRepository.save(user);

		// You can convert the registered user back to a UserDTO if needed
		userDTO = modelMapper.map(registeredUser, UserDTO.class);
	
		userDTO.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(), AddressDTO.class));

	    return userDTO;
}
catch(DataIntegrityViolationException e)
{
	throw new APIException("User already exists with emailId: " + userDTO.getEmail());
}
	}

	@Override
	public UserResponse getAllUsers(Integer pageNumber,Integer pageSize) {
		// TODO Auto-generated method stub
		Pageable p=PageRequest.of(pageNumber, pageSize);
		Page<User> pageUsers = userRepository.findAll(p);
		List<User> users=pageUsers.getContent();
		List<UserDTO> userDTO = users.stream().map(user -> modelMapper.map(user, UserDTO.class))
				.collect(Collectors.toList());
		UserResponse userResponse=new UserResponse();
		userResponse.setContent(userDTO);
		userResponse.setPageNumber(pageUsers.getNumber());
		userResponse.setPageSize(pageUsers.getSize());
		userResponse.setTotalElements(pageUsers.getTotalElements());
		userResponse.setTotalPages(pageUsers.getTotalPages());
		userResponse.setLastPage(pageUsers.isLast());
		return userResponse;
	}

	@Override
	public UserDTO getUserById(Long userId) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		return userDTO;
	}

	@Override
	public UserDTO updateUser(Long userId, UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteUser(Long userId) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		userRepository.delete(user);
		return "User with userId " + userId + " deleted successfully!!!";
	}

}
