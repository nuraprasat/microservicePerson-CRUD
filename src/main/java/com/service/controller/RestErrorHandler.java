package com.service.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.service.domain.Person;
import com.service.exception.PersonNotFoundException;
import com.service.model.ErrorModel;

@ControllerAdvice
@RequestMapping(produces = "application/json")
public class RestErrorHandler {
	
	private static final String INTERNAL_SERVER_ERROR_LOG = "Internal Server Error - ";
	private static final Logger LOGGER = Logger.getLogger(RestErrorHandler.class.getName());
	
	@ExceptionHandler(PersonNotFoundException.class)
	public ResponseEntity<Person> hadleInternalError(final PersonNotFoundException e) {
		LOGGER.log(Level.WARNING,INTERNAL_SERVER_ERROR_LOG+e.getErrorModel());
		return error(e.getErrorModel());
	}
	
	private ResponseEntity<Person> error(final ErrorModel em) {
		Person p = getProduct(em);
		return new ResponseEntity<Person>(p, HttpStatus.BAD_REQUEST);
	}
	
	private Person getProduct(ErrorModel em) {
		Person p = new Person();
		p.setErrorModel(em);
		return p;
	}

	
}
