package com.example.CinemaTicketBookingErmurat.service;

import com.example.CinemaTicketBookingErmurat.model.Movie;
import com.example.CinemaTicketBookingErmurat.model.Ticket;
import com.example.CinemaTicketBookingErmurat.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private MovieService movieService;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
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
        boolean seatTaken = ticketRepository.findByMovieIdAndSeatNumberAndStatus(movieId, seatNumber, "BOOKED").isPresent();
        if (seatTaken) {
            throw new RuntimeException("Место " + seatNumber + " уже занято");
        }

        movie.setAvailableSeats(movie.getAvailableSeats() - 1);
        movieService.createMovie(movie); // обновляем фильм

        String bookingTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Ticket ticket = new Ticket(
                null, // id будет сгенерирован
                viewerId,
                movieId,
                movie.getCinemaId(),
                seatNumber,
                movie.getPrice(),
                bookingTime,
                "BOOKED"
        );
        return ticketRepository.save(ticket);
    }

    public boolean cancelTicket(Long ticketId) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isEmpty()) return false;

        Ticket ticket = ticketOpt.get();
        if ("CANCELLED".equals(ticket.getStatus())) return false;

        ticket.setStatus("CANCELLED");
        ticketRepository.save(ticket);

        // Возвращаем место
        movieService.getMovieById(ticket.getMovieId()).ifPresent(m -> {
            m.setAvailableSeats(m.getAvailableSeats() + 1);
            movieService.createMovie(m);
        });
        return true;
    }

    public List<Ticket> getTicketsByViewer(Long viewerId) {
        return ticketRepository.findByViewerId(viewerId);
    }

    public List<Ticket> getTicketsByMovie(Long movieId) {
        return ticketRepository.findByMovieId(movieId);
    }
}
