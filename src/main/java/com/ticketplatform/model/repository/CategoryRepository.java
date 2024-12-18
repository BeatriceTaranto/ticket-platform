package com.ticketplatform.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticketplatform.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
}
