package com.example.CinemaTicketBookingErmurat.service;

import com.example.CinemaTicketBookingErmurat.model.Movie;
import com.example.CinemaTicketBookingErmurat.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TicketService {

    private final List<Ticket> tickets = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    private final MovieService movieService;

    @Autowired
    public TicketService(MovieService movieService) {
        this.movieService = movieService;
    }

    public List<Ticket> getAllTickets() {
        return new ArrayList<>(tickets);
    }

    public Optional<Ticket> getTicketById(Long id) {
        return tickets.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    public Ticket bookTicket(Long viewerId, Long movieId, String seatNumber) {
        Optional<Movie> movieOpt = movieService.getMovieById(movieId);
        if (movieOpt.isEmpty()) {
            throw new RuntimeException("Фильм с ID " + movieId + " не найден");
        }
        Movie movie = movieOpt.get();
        if (movie.getAvailableSeats() <= 0) {
            throw new RuntimeException("Нет свободных мест на фильм: " + movie.getTitle());
        }

        // Проверяем занятость места
        boolean seatTaken = tickets.stream()
                .anyMatch(t -> t.getMovieId().equals(movieId)
                        && t.getSeatNumber().equalsIgnoreCase(seatNumber)
                        && "BOOKED".equals(t.getStatus()));
        if (seatTaken) {
            throw new RuntimeException("Место " + seatNumber + " уже занято");
        }

        movie.setAvailableSeats(movie.getAvailableSeats() - 1);

        String bookingTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Ticket ticket = new Ticket(
                idCounter.getAndIncrement(),
                viewerId,
                movieId,
                movie.getCinemaId(),
                seatNumber,
                movie.getPrice(),
                bookingTime,
                "BOOKED"
        );
        tickets.add(ticket);
        return ticket;
    }

    public boolean cancelTicket(Long ticketId) {
        Optional<Ticket> ticketOpt = tickets.stream()
                .filter(t -> t.getId().equals(ticketId)).findFirst();
        if (ticketOpt.isEmpty()) return false;

        Ticket ticket = ticketOpt.get();
        if ("CANCELLED".equals(ticket.getStatus())) return false;

        ticket.setStatus("CANCELLED");

        // Возвращаем место
        movieService.getMovieById(ticket.getMovieId()).ifPresent(m ->
                m.setAvailableSeats(m.getAvailableSeats() + 1));
        return true;
    }

    public List<Ticket> getTicketsByViewer(Long viewerId) {
        List<Ticket> result = new ArrayList<>();
        for (Ticket t : tickets) {
            if (t.getViewerId().equals(viewerId)) {
                result.add(t);
            }
        }
        return result;
    }

    public List<Ticket> getTicketsByMovie(Long movieId) {
        List<Ticket> result = new ArrayList<>();
        for (Ticket t : tickets) {
            if (t.getMovieId().equals(movieId)) {
                result.add(t);
            }
        }
        return result;
    }
}

