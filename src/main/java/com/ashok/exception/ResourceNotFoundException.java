package com.ashok.exception;

public class ResourceNotFoundException extends RuntimeException {
	String resourceName;
	String fieldName;
	 long fieldValue;

	public ResourceNotFoundException(String resourceName, String fieldName,long fieldValue) {
		super(String.format("%s not found in %s : %d", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	

}