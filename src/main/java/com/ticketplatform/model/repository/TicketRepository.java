package com.ticketplatform.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketplatform.model.Operator;
import com.ticketplatform.model.Status;
import com.ticketplatform.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	List<Ticket> findByStatus(Status status);

	List<Ticket> findByCategoryId(Long categoryId);

	List<Ticket> findByOperator(Operator operator);

	List<Ticket> findByTitleContaining(String keyword);

	Long countByOperatorId(Long operatorId);
	
	Long countByOperatorIdAndStatusIn(Long operatorId, List<Status> statuses);
}
