package com.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.model.PersonDetails;

public interface PersonRepository extends JpaRepository<PersonDetails, Integer>{
	
	public List<PersonDetails> findAll();
	public Optional<PersonDetails> findById(Integer id);

}
