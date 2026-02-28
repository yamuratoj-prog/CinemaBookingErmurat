package com.example.CinemaTicketBookingErmurat.controller;

import com.example.CinemaTicketBookingErmurat.model.Ticket;
import com.example.CinemaTicketBookingErmurat.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST контроллер для управления билетами
 * Автор: Ермурат
 */
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // 1. Получить все билеты
    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    // 2. Получить билет по ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketService.getTicketById(id);
        if (ticket.isPresent()) {
            return ResponseEntity.ok(ticket.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Билет с ID " + id + " не найден"));
    }

    // 3. Забронировать билет
    @PostMapping("/book")
    public ResponseEntity<?> bookTicket(@RequestParam Long viewerId,
                                        @RequestParam Long movieId,
                                        @RequestParam String seatNumber) {
        try {
            Ticket ticket = ticketService.bookTicket(viewerId, movieId, seatNumber);
            return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 4. Отменить бронирование билета
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelTicket(@PathVariable Long id) {
        boolean cancelled = ticketService.cancelTicket(id);
        if (cancelled) {
            return ResponseEntity.ok(Map.of("message", "Билет с ID " + id + " успешно отменён"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Билет с ID " + id + " не найден или уже отменён"));
    }

    // 5. Получить все билеты зрителя
    @GetMapping("/viewer/{viewerId}")
    public ResponseEntity<?> getTicketsByViewer(@PathVariable Long viewerId) {
        List<Ticket> tickets = ticketService.getTicketsByViewer(viewerId);
        if (tickets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Билеты для зрителя ID " + viewerId + " не найдены"));
        }
        return ResponseEntity.ok(tickets);
    }

    // 6. Получить все билеты на фильм
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<?> getTicketsByMovie(@PathVariable Long movieId) {
        List<Ticket> tickets = ticketService.getTicketsByMovie(movieId);
        if (tickets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Билеты на фильм ID " + movieId + " не найдены"));
        }
        return ResponseEntity.ok(tickets);
    }
}

