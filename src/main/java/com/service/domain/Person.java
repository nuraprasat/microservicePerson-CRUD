package com.service.domain;

import java.util.List;

import com.service.model.ErrorModel;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Person {
	
	@NonNull private Integer person_id;
	@NonNull private String first_name;
	@NonNull private String last_name;
	@NonNull private Integer age;
	@NonNull private String favourite_colour;
	@NonNull private List<String> hobby;
	
	private ErrorModel errorModel;

}
