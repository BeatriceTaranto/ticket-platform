package com.ticketplatform.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketplatform.model.Status;
import com.ticketplatform.model.Ticket;
import com.ticketplatform.model.repository.TicketRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketsRestController {

	@Autowired
	private TicketRepository ticketRepository;

	@GetMapping
	public ResponseEntity<List<Ticket>> getAllTickets() {
	    List<Ticket> tickets = ticketRepository.findAll();
	    if (tickets.isEmpty()) {
	        return ResponseEntity.noContent().build(); // HTTP 204: No Content
	    }
	    return ResponseEntity.ok(tickets); // HTTP 200: OK
	}

	@GetMapping("/category/{categoryId}")
	public List<Ticket> getTicketsByCategory(@PathVariable Long categoryId) {
		return ticketRepository.findByCategoryId(categoryId);
	}

	@GetMapping("/status/{status}")
	public ResponseEntity<List<Ticket>> getTicketsByStatus(@PathVariable String status) {
		try {
			
			Status ticketStatus = Status.valueOf(status.toUpperCase());

			List<Ticket> tickets = ticketRepository.findByStatus(ticketStatus);

			return ResponseEntity.ok(tickets);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
