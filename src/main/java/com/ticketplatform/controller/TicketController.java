package com.ticketplatform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ticketplatform.model.Category;
import com.ticketplatform.model.Operator;
import com.ticketplatform.model.Ticket;
import com.ticketplatform.model.repository.CategoryRepository;
import com.ticketplatform.model.repository.OperatorRepository;
import com.ticketplatform.model.repository.TicketRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private OperatorRepository operatorRepository;

	@GetMapping
	public String index(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
		List<Ticket> allTickets;
		if (keyword != null && !keyword.isBlank()) {
			model.addAttribute("keyword", keyword);
			allTickets = ticketRepository.findByTitleContaining(keyword);
		} else {
			allTickets = ticketRepository.findAll();
		}

		model.addAttribute("tickets", allTickets);

		return "tickets";
	}

	@GetMapping("/create")
	public String createTicketForm(Model model) {
		Ticket ticket = new Ticket();
		List<Operator> availableOperators = operatorRepository.findByAvailableTrue();
		List<Category> categories = categoryRepository.findAll();

		model.addAttribute("ticket", ticket);
		model.addAttribute("operators", availableOperators);
		model.addAttribute("categories", categories);
		return "ticket_form";
	}

	@PostMapping
	public String saveTicket(@Valid @ModelAttribute Ticket ticket, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryRepository.findAll());
			model.addAttribute("operators", operatorRepository.findByAvailableTrue());
			return "ticket_form";
		}

		ticketRepository.save(ticket);
		return "redirect:/tickets";
	}

	@GetMapping("/edit/{id}")
	public String editTicket(@PathVariable Long id, Model model) {
		Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
		model.addAttribute("ticket", ticket);
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("operators", operatorRepository.findByAvailableTrue());
		return "ticket_form";
	}

	@PostMapping("/update/{id}")
	public String updateTicket(@PathVariable Long id, @Valid @ModelAttribute Ticket updatedTicket,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryRepository.findAll());
			model.addAttribute("operators", operatorRepository.findByAvailableTrue());
			return "ticket_form"; 
		}

		Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
		ticket.setTitle(updatedTicket.getTitle());
		ticket.setDescription(updatedTicket.getDescription());
		ticket.setStatus(updatedTicket.getStatus());
		ticket.setOperator(updatedTicket.getOperator());
		ticket.setCategory(updatedTicket.getCategory());
		ticketRepository.save(ticket);

		return "redirect:/tickets";
	}

	@PostMapping("/delete/{id}")
	public String deleteTicket(@PathVariable Long id) {
		ticketRepository.deleteById(id);
		return "redirect:/tickets";
	}

	@GetMapping("/show/{id}")
	public String showTicket(@PathVariable Long id, Model model) {
		Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
		model.addAttribute("ticket", ticket);
		return "ticket_details";
	}
}