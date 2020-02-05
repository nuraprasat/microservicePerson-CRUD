package com.service.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
import com.service.repository.HobbyRepository;
import com.service.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepository;

	@Autowired
	HobbyRepository hobbyRepository;

	public List<Person> findAllPerson() {
		List<PersonDetails> personDetails = personRepository.findAll();
		List<Person> personList = new ArrayList<>();
		for (PersonDetails p : personDetails) {
			Person person = getPerson(p);
			personList.add(person);
		}
		return personList;
	}

	public Person findById(Integer id) {
		Optional<PersonDetails> personDetails = personRepository.findById(id);
		Person person = null;
		if (personDetails.isPresent()) {
			person = getPerson(personDetails.get());
		}
		return person;
	}

	public void deleteById(Integer id) throws PersonNotFoundException {
		Optional<PersonDetails> personDetails = personRepository.findById(id);
		if (!personDetails.isPresent()) {
			ErrorModel em = new ErrorModel(HttpStatus.NOT_FOUND.value(), "person id-"+id+" not found");
			throw new PersonNotFoundException(em);
		}
		personRepository.deleteById(id);
	}

	public PersonDetails save(Person person) {
		Set<Hobby> hobbySet = getHobbySet(person);

		PersonDetails personDetails = new PersonDetails(person.getFirst_name(), person.getLast_name(), person.getAge(),
				person.getFavourite_colour(), hobbySet);

		return personRepository.save(personDetails);
	}

	public ResponseEntity<Object> updatePerson(Person person, Integer id) {
		Optional<PersonDetails> personOptional = personRepository.findById(id);
		if (!personOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Set<Hobby> hobbySet = getHobbySet(person);

		PersonDetails personDetail = personOptional.get();
		personDetail.setFirstName(person.getFirst_name());
		personDetail.setLastName(person.getLast_name());
		personDetail.setAge(person.getAge());
		personDetail.setFavouriteColour(person.getFavourite_colour());
		personDetail.setHobbys(hobbySet);
		personRepository.save(personDetail);

		return ResponseEntity.ok().build();
	}

	private Set<Hobby> getHobbySet(Person person) {
		Set<Hobby> hobbySet = new HashSet<>();
		for (String s : person.getHobby()) {
			Hobby hobby = hobbyRepository.findByHobbyName(s);
			if (hobby != null) {
				hobbySet.add(hobby);
			} else {
				Hobby ho = new Hobby();
				ho.setHobbyName(s);
				hobbySet.add(ho);
			}
		}

		return hobbySet;
	}

	private Person getPerson(PersonDetails p) {
		List<String> hobbyList = p.getHobbys().stream().map(Hobby::getHobbyName).collect(Collectors.toList());
		return new Person(p.getPersonId(), p.getFirstName(), p.getLastName(), p.getAge(), p.getFavouriteColour(),
				hobbyList, null);
	}

}
