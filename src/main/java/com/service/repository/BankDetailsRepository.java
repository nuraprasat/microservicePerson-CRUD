package com.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.model.Hobby;

public interface BankDetailsRepository extends JpaRepository<Hobby, Integer> {

}
