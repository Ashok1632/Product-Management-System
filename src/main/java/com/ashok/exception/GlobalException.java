package com.ashok.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ashok.payload.APIResponse;
import com.ashok.payload.Response;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalException {
	@ExceptionHandler(APIException.class)
	public ResponseEntity<APIResponse> ApiExeception(APIException ex) {
		APIResponse error = new APIResponse();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<APIResponse>(error, HttpStatus.BAD_REQUEST);

	}
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Response> resourceNOTFoundException(ResourceNotFoundException ex)
	{
		String message=ex.getMessage();
		Response apiResponse=new Response(message, false);
		
		return new ResponseEntity<Response>(apiResponse, HttpStatus.NOT_FOUND);
		
		
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		Map<String, String> res = new HashMap<>();

		e.getBindingResult().getAllErrors().forEach(err -> {
			String fieldName = ((FieldError) err).getField();
			String message = err.getDefaultMessage();

			res.put(fieldName, message);
		});

		return new ResponseEntity<Map<String, String>>(res, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> myConstraintsVoilationException(ConstraintViolationException e) {
		Map<String, String> res = new HashMap<>();

		e.getConstraintViolations().forEach(voilation -> {
			String fieldName = voilation.getPropertyPath().toString();
			String message = voilation.getMessage();

			res.put(fieldName, message);
		});

		return new ResponseEntity<Map<String, String>>(res, HttpStatus.BAD_REQUEST);
	}
}
