package com.service.controller;

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.domain.Person;
import com.service.exception.InternalServerError;
import com.service.exception.PersonNotFoundException;
import com.service.model.ErrorModel;
import com.service.model.PersonDetails;
import com.service.service.PersonService;

@RestController
@RequestMapping("/api")
public class ControllerRest {

	@Autowired
	PersonService personService;

	@GetMapping("/getAllPersons")
	public ResponseEntity<List<Person>> retrieveAllPerson() {
		return new ResponseEntity<>(personService.findAllPerson(), HttpStatus.OK);
	}

	@GetMapping("/getPersonDetails/{id}")
	public ResponseEntity<Person> retrievePersonDetails(@Validated @PathVariable @Min(1) int id)
			throws PersonNotFoundException {
		Person person = personService.findById(id);

		if (person == null) {
			ErrorModel em = new ErrorModel(HttpStatus.BAD_REQUEST.value(), "person id - " + id + " not found");
			throw new PersonNotFoundException(em);
		}

		return new ResponseEntity<>(person, HttpStatus.OK);
	}

	@DeleteMapping("/deletePerson/{id}")
	public void deletePerson(@Validated @PathVariable @Min(1) int id) throws PersonNotFoundException {
		personService.deleteById(id);
	}

	@PostMapping("/createPerson")
	public ResponseEntity<Object> createPerson(@RequestBody Person person) throws InternalServerError {
		PersonDetails savedPerson = personService.save(person);

		if (savedPerson == null) {
			ErrorModel em = new ErrorModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error");
			throw new InternalServerError(em);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}

	@PutMapping("/updatePerson/{id}")
	public ResponseEntity<Object> updatePerson(@RequestBody Person person, @PathVariable @Validated @Min(1) int id) {
		return personService.updatePerson(person, id);
	}
}
