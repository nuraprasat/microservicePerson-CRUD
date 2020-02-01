package com.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.model.Hobby;

public interface HobbyRepository extends JpaRepository<Hobby, Integer> {

	public Hobby findByHobbyName(String s);

}
