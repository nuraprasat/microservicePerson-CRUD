package com.service.domain;

import java.util.List;

import com.service.model.ErrorModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
	
	private Integer person_id;
	private String first_name;
	private String last_name;
	private Integer age;
	private String favourite_colour;
	private List<String> hobby;
	
	private ErrorModel errorModel;

}
