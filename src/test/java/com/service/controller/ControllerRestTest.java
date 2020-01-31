package com.service.controller;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.service.service.PersonService;

@RunWith(MockitoJUnitRunner.class)
public class ControllerRestTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Mock
	private PersonService userInformationService;
	
	@InjectMocks
	ControllerRest controllerRest;
	
	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controllerRest).build();
	}

	
}
