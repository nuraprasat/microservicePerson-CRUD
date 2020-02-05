package com.service.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.service.domain.Person;
import com.service.exception.PersonNotFoundException;
import com.service.model.Hobby;
import com.service.model.PersonDetails;
import com.service.repository.HobbyRepository;
import com.service.repository.PersonRepository;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {
	
	@InjectMocks
	PersonService personService;
	
	@Mock
	PersonRepository personRepository;

	@Mock
	HobbyRepository hobbyRepository;
	
	@Test
	public void verifyFindAllPersonReturnsList() throws Exception {
		Mockito.when(personRepository.findAll()).thenReturn(getListOfPersonDetails());
		List<Person> personList = personService.findAllPerson();
		assertNotNull(personList);
		assertEquals(getListOfPerson().get(0).getHobby(), personList.get(0).getHobby());
	}
	
	@Test
	public void verifyFindAllPersonReturnsNull() throws Exception {
		Mockito.when(personRepository.findAll()).thenReturn(new ArrayList<>());
		List<Person> personList = personService.findAllPerson();
		assertNotNull(personList);
		assertTrue(personList.isEmpty());
	}
	
	@Test
	public void verifyFindByIdWithNull() throws Exception {
		PersonDetails personDetails = null;
		Mockito.when(personRepository.findById(1)).thenReturn(Optional.ofNullable(personDetails));
		Person person = personService.findById(1);
		assertNull(person);
	}
	
	@Test
	public void verifyFindByIdReturnsPerson() throws Exception {
		Mockito.when(personRepository.findById(1)).thenReturn(Optional.ofNullable(getListOfPersonDetails().get(0)));
		Person person = personService.findById(1);
		assertNotNull(person);
		assertEquals(person.getFirst_name(), getListOfPersonDetails().get(0).getFirstName());
	}
	
	@Test
	public void verifyDeleteByID() throws Exception {
		Mockito.when(personRepository.findById(1)).thenReturn(Optional.ofNullable(getListOfPersonDetails().get(0)));
		personService.deleteById(1);
		Mockito.verify(personRepository, Mockito.times(1)).deleteById(1);
	}
	
	@Test(expected = PersonNotFoundException.class)
	public void verifyDeleteByIDThrowsException() throws Exception {
		PersonDetails personDetails = null;
		Mockito.when(personRepository.findById(1)).thenReturn(Optional.ofNullable(personDetails));
		personService.deleteById(1);
		Mockito.verify(personRepository, Mockito.times(1)).deleteById(0);
	}
	
	@Test
	public void verifyUpdatePersonWithAllValues() throws Exception {
		Mockito.when(personRepository.findById(1)).thenReturn(Optional.ofNullable(getListOfPersonDetails().get(0)));
		Hobby hobby = new Hobby();
		hobby.setHobbyName("cricket");
		hobby.setId(1);
		Mockito.when(hobbyRepository.findByHobbyName(Mockito.anyString())).thenReturn(hobby);
		personService.updatePerson(getListOfPerson().get(0), 1);
		Mockito.verify(personRepository, Mockito.times(1)).save(Mockito.any(PersonDetails.class));
		
	}
	
	@Test
	public void verifyUpdatePersonWithNullHobbyValues() throws Exception {
		Mockito.when(personRepository.findById(1)).thenReturn(Optional.ofNullable(getListOfPersonDetails().get(0)));
		Mockito.when(hobbyRepository.findByHobbyName(Mockito.anyString())).thenReturn(null);
		personService.updatePerson(getListOfPerson().get(0), 1);
		Mockito.verify(personRepository, Mockito.times(1)).save(Mockito.any(PersonDetails.class));
		
	}
	
	@Test
	public void verifyUpdatePersonWithOptionalEmpty() throws Exception {
		PersonDetails personDetails = null;
		Mockito.when(personRepository.findById(1)).thenReturn(Optional.ofNullable(personDetails));
		Mockito.when(hobbyRepository.findByHobbyName(Mockito.anyString())).thenReturn(null);
		personService.updatePerson(getListOfPerson().get(0), 1);
		Mockito.verify(personRepository, Mockito.times(0)).save(Mockito.any(PersonDetails.class));
		
	}
	
	@Test
	public void verifySaveIsInsertingAllValues() throws Exception {
		Mockito.when(hobbyRepository.findByHobbyName(Mockito.anyString())).thenReturn(null);
		personService.save(getListOfPerson().get(0));
		Mockito.verify(personRepository, Mockito.times(1)).save(getListOfPersonDetails().get(0));
		
	}
	
	private List<PersonDetails> getListOfPersonDetails() {
		List<PersonDetails> personDetailList = new ArrayList<>();
		PersonDetails personDetails = new PersonDetails();
		personDetails.setFirstName("a");
		personDetails.setLastName("b");
		personDetails.setAge(23);
		personDetails.setFavouriteColour("green");
		personDetails.setHobbys(getHobbys());
		personDetailList.add(personDetails);
		return personDetailList;
	}
	
	private List<Person> getListOfPerson() {
		List<Person> personDetailList = new ArrayList<>();
		Person person = new Person();
		person.setFirst_name("a");
		person.setLast_name("b");
		person.setAge(23);
		person.setFavourite_colour("green");
		person.setHobby(Arrays.asList("swimming"));
		personDetailList.add(person);
		return personDetailList;
	}

	private Set<Hobby> getHobbys() {
		Set<Hobby> set = new HashSet<>();
		Hobby hobby = new Hobby();
		hobby.setHobbyName("swimming");
		hobby.setId(0);
		set.add(hobby);
		return set;
	}

	
	
}
