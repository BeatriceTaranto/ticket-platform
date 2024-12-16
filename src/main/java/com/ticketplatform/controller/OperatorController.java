package com.ticketplatform.controller;

import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ticketplatform.model.Operator;
import com.ticketplatform.model.Status;
import com.ticketplatform.model.Ticket;
import com.ticketplatform.model.repository.OperatorRepository;
import com.ticketplatform.model.repository.TicketRepository;

@Controller
@RequestMapping("/operators")
public class OperatorController {

    @Autowired
    private OperatorRepository operatorRepository;
    
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public String listOperators(@RequestParam(value = "id", required = false) Integer id, Model model) {
        List<Operator> operators = operatorRepository.findAll();
        Map<Long, Long> operatorTicketCounts = operators.stream()
                .collect(Collectors.toMap(
                        Operator::getId, // Chiave: ID dell'operatore
                        operator -> ticketRepository.countByOperatorId(operator.getId()) // Valore: conteggio ticket
                        ));
                
        
        model.addAttribute("operators", operators);
        model.addAttribute("operatorTicketCounts", operatorTicketCounts);

        if (id != null) {
            Operator operator = operatorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Operator not found"));
            List<Ticket> assignedTickets = ticketRepository.findByOperator(operator);

            model.addAttribute("selectedOperator", operator);
            model.addAttribute("tickets", assignedTickets);
        }

        return "operators";
    }
    
    @PostMapping("/{id}/update-availability")
    public String updateAvailability(@PathVariable Long id, @RequestParam boolean available, RedirectAttributes redirectAttributes) {
        Operator operator = operatorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Operator not found"));

        long countOfActiveTickets = ticketRepository.countByOperatorIdAndStatusIn(
            id, List.of(Status.TODO, Status.IN_PROGRESS)
        );

        if (!available && countOfActiveTickets > 0) {
        	redirectAttributes.addFlashAttribute("error", "Cannot set unavailable. Active tickets are assigned to this operator.");
            return "redirect:/operators";
        }

        operator.setAvailable(available);
        operatorRepository.save(operator);

        return "redirect:/operators";
    }
}





