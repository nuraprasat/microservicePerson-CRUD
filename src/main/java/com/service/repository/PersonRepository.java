package com.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.model.PersonDetails;

public interface PersonRepository extends JpaRepository<PersonDetails, Integer>{


}
