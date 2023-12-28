package com.ashok.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashok.entity.Address;
import com.ashok.exception.APIException;
import com.ashok.exception.ResourceNotFoundException;
import com.ashok.payload.AddressDTO;
import com.ashok.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ModelMapper modelmapper;

	@Override
	public AddressDTO createAddress(AddressDTO addressDTO) {
		// TODO Auto-generated method stub
		String country = addressDTO.getCountry();
		String state = addressDTO.getState();
		String city = addressDTO.getCity();
		String pincode = addressDTO.getPincode();
		String street = addressDTO.getStreet();
		String buildingName = addressDTO.getBuildingName();
		Address addressDb = addressRepository.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(country,
				state, city, pincode, street, buildingName);
		if (addressDb != null) {
			throw new APIException("address already exist with addressId", +addressDb.getAddressId());
		}
		Address address = this.modelmapper.map(addressDTO, Address.class);
		Address saveAddress = addressRepository.save(address);
		AddressDTO addressDto=this.modelmapper.map(saveAddress, AddressDTO.class);
		return addressDto;
	}

	@Override
	public List<AddressDTO> getAddresses() {
		// TODO Auto-generated method stub
		List<Address> addresses = addressRepository.findAll();
		List<AddressDTO> addressDTO = addresses.stream().map(address -> modelmapper.map(address, AddressDTO.class))
				.collect(Collectors.toList());
		;
		return addressDTO;
	}

	@Override
	public AddressDTO getAddress(Long addressId) {
		// TODO Auto-generated method stub
		Address address = addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));
		return this.modelmapper.map(address, AddressDTO.class);
	}

	@Override
	public AddressDTO updateAddress(Long addressId, Address address) {
		// TODO Auto-generated method stub
		Address addressDb = addressRepository.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(
				address.getCountry(), address.getState(), address.getCity(), address.getPincode(), address.getStreet(),
				address.getBuildingName());

		addressDb = addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

		addressDb.setCountry(address.getCountry());
		addressDb.setState(address.getState());
		addressDb.setCity(address.getCity());
		addressDb.setPincode(address.getPincode());
		addressDb.setStreet(address.getStreet());
		addressDb.setBuildingName(address.getBuildingName());

		Address updatedAddress = addressRepository.save(addressDb);

		return modelmapper.map(updatedAddress, AddressDTO.class);

	}

	@Override
	public String deleteAddress(Long addressId) {
		// TODO Auto-generated method stub
		Address addressDb = addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));
		addressRepository.delete(addressDb);
		return "address deleted successfully" + addressId;
	}

}
