package com.service.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.domain.Person;
import com.service.model.Hobby;
import com.service.model.PersonDetails;
import com.service.service.PersonService;

@RunWith(MockitoJUnitRunner.class)
public class ControllerRestTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Mock
	private PersonService personService;
	
	@InjectMocks
	ControllerRest controllerRest;
	
	ObjectMapper ow;
	
	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controllerRest).build();
		ow = new ObjectMapper();
	}
	
	@Test
	public void AllPersonShouldBeReturnedFromService() throws Exception {
		Mockito.when(personService.findAllPerson()).thenReturn(getListOfPerson());
		MvcResult result = this.mockMvc.perform(get("/api/getAllPersons").accept(MediaType.APPLICATION_JSON_VALUE))
														.andExpect(status().is(200))
														.andReturn();
		ObjectMapper mapper = new ObjectMapper();
		List<Person> proList = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Person>>() {});
		assertNotNull(proList.get(0));
		assertEquals(getListOfPerson().get(0), proList.get(0));
	}
	
	@Test
	public void personShouldReturnedbasedOnId() throws Exception {
		Mockito.when(personService.findById(Mockito.anyInt())).thenReturn(getListOfPerson().get(0));
		MvcResult result = this.mockMvc.perform(get("/api/getPersonDetails/{id}",1).accept(MediaType.APPLICATION_JSON_VALUE))
														.andExpect(status().is(200))
														.andReturn();
		ObjectMapper mapper = new ObjectMapper();
		Person proList = mapper.readValue(result.getResponse().getContentAsString(), Person.class);
		assertNotNull(proList);
		assertEquals(getListOfPerson().get(0), proList);
	}
	
	@Test(expected=NestedServletException.class)
	public void personNotReturnedbasedOnId() throws Exception {
		Person person = null;
		Mockito.when(personService.findById(Mockito.anyInt())).thenReturn(person);
		this.mockMvc.perform(get("/api/getPersonDetails/{id}",1).accept(MediaType.APPLICATION_JSON_VALUE))
														.andExpect(status().is(200))
														.andReturn();
	}
	
	@Test
	public void checkDeletePerson() throws Exception {
		Mockito.doNothing().when(personService).deleteById(Mockito.anyInt());
		this.mockMvc.perform(delete("/api/deletePerson/{id}",1).accept(MediaType.APPLICATION_JSON_VALUE))
														.andExpect(status().is(200))
														.andReturn();
	}
	
	@Test(expected = NestedServletException.class)
	public void checkPostCreatePersonWithNull() throws Exception {
		
		String requestJson=ow.writeValueAsString(getListOfPerson().get(0));
		Mockito.when(personService.save(Mockito.any(Person.class))).thenReturn(null);
		this.mockMvc.perform(post("/api/createPerson")
														.contentType(APPLICATION_JSON_UTF8)
														.content(requestJson))
														.andExpect(status().is(500))
														.andReturn();
	}
	
	@Test
	public void checkPostCreatePersonWorking() throws Exception {
		String requestJson=ow.writeValueAsString(getListOfPerson().get(0));
		Mockito.when(personService.save(Mockito.any(Person.class))).thenReturn(getListOfPersonDetails().get(0));
		this.mockMvc.perform(post("/api/createPerson")
														.contentType(APPLICATION_JSON_UTF8)
														.content(requestJson))
														.andExpect(status().is(201))
														.andReturn();
	}
	
	@Test
	public void checkPutUpdatePersonWorking() throws Exception {
		String requestJson=ow.writeValueAsString(getListOfPerson().get(0));
		Mockito.when(personService.updatePerson(Mockito.any(Person.class), Mockito.anyInt())).thenReturn(null);
		this.mockMvc.perform(put("/api/updatePerson/{id}",1)
														.contentType(APPLICATION_JSON_UTF8)
														.content(requestJson))
														.andExpect(status().is(200))
														.andReturn();
		Mockito.verify(personService, Mockito.times(1)).updatePerson(Mockito.any(Person.class), Mockito.anyInt());
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
		person.setFirst_name("b");
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
		hobby.setId(1);
		set.add(hobby);
		return set;
	}

	
}
