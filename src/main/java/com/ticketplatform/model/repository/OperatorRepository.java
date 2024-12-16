package com.ticketplatform.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticketplatform.model.Operator;

public interface OperatorRepository extends JpaRepository<Operator, Integer> {
	
    List<Operator> findByAvailableTrue();

	Optional<Operator> findById(Long id);
}