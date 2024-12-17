package com.ticketplatform.controller;

import java.time.LocalDateTime;
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

import com.ticketplatform.model.Note;
import com.ticketplatform.model.Ticket;
import com.ticketplatform.model.repository.NoteRepository;
import com.ticketplatform.model.repository.TicketRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/notes")
public class NoteController {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private TicketRepository ticketRepository;

	// Recupera le note associate al ticket

	@GetMapping("/{ticketId}")
	public String listNotes(@PathVariable Long ticketId, Model model) {
		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
		List<Note> notes = noteRepository.findByTicket(ticket);
		model.addAttribute("ticket", ticket);
		model.addAttribute("notes", notes);
		model.addAttribute("note", new Note());
		return "notes";
	}

	// Aggiunge una nota a un ticket esistente

	@PostMapping("/{ticketId}")
	public String addNote(@PathVariable Long ticketId, @Valid @ModelAttribute Note note, BindingResult bindingResult,
			Model model) {
		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));

		if (bindingResult.hasErrors()) {
			List<Note> notes = noteRepository.findByTicket(ticket);

			model.addAttribute("ticket", ticket);
			model.addAttribute("notes", notes);
			return "notes";
		}

		note.setTicket(ticket);
		note.setCreatedAt(LocalDateTime.now());
		noteRepository.save(note);
		return "redirect:/notes/" + ticketId;
	}
}