package com.service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.domain.Person;
import com.service.exception.PersonNotFoundException;
import com.service.model.ErrorModel;
import com.service.model.Hobby;
import com.service.model.PersonDetails;
import com.service.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepository;

	public List<Person> findAllPerson() {
		List<PersonDetails> personDetails =  personRepository.findAll();
		List<Person> personList = new ArrayList<>();
		for(PersonDetails p : personDetails) {
			Person person = getPerson(p);
			personList.add(person);
		}
		return personList;
	}

	
	public Person findById(Integer id) {
		Optional<PersonDetails> personDetails = personRepository.findById(id);
		Person person = null;
		if(personDetails.isPresent()) {
			person = getPerson(personDetails.get());
		}
		return person;
	}

	public void deleteById(Integer id) {
		personRepository.deleteById(id);
	}

	public PersonDetails save(PersonDetails personDetails) {
		return personRepository.save(personDetails);
	}

	public ResponseEntity<Object> updatePerson(PersonDetails personDetails, Integer id) {
		Optional<PersonDetails> personOptional = personRepository.findById(id);
		if (!personOptional.isPresent())
			return ResponseEntity.notFound().build();
		
		personDetails.setPersonId(personOptional.get().getPersonId());
		personRepository.save(personDetails);
		
		return ResponseEntity.ok().build();
	}

	public PersonDetails updatePerson(String email, Integer id) throws PersonNotFoundException {
		Optional<PersonDetails> userOptional = personRepository.findById(id);
		if (!userOptional.isPresent()) {
			ErrorModel em = new ErrorModel(HttpStatus.NOT_FOUND.value(), "user id not found");
			throw new PersonNotFoundException(em);
		}
		PersonDetails userInformation = userOptional.get();
		//userInformation.setEmail(email);
		personRepository.save(userInformation);

		return userInformation;
	}

	private Person getPerson(PersonDetails p) {
		List<String> hobbyList = p.getHobby().stream().map(Hobby::getHobbyName).collect(Collectors.toList());
		return new Person(p.getPersonId(), p.getFirstName(), p.getLastName(), p.getAge(), p.getFavouriteColour(), hobbyList);
	}


	public Person findByParameter(String firstName, String lastName, int age, String favouriteColor, String hobby) {
		// TODO Auto-generated method stub
		return null;
	}
}
