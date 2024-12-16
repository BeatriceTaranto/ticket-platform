package com.ticketplatform.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticketplatform.model.Note;
import com.ticketplatform.model.Ticket;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByTicket(Ticket ticket);
}
