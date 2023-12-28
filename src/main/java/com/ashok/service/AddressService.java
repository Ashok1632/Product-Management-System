package com.ashok.service;

import java.util.List;

import com.ashok.entity.Address;
import com.ashok.payload.AddressDTO;



public interface AddressService {

	AddressDTO createAddress(AddressDTO addressDTO);
	
	List<AddressDTO> getAddresses();
	
	AddressDTO getAddress(Long addressId);
	
	AddressDTO updateAddress(Long addressId, Address address);
	
	String deleteAddress(Long addressId);
}
