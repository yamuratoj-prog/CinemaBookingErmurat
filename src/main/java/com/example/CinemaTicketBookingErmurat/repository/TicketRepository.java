package com.example.CinemaTicketBookingErmurat.repository;

import com.example.CinemaTicketBookingErmurat.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByViewerId(Long viewerId);
    List<Ticket> findByMovieId(Long movieId);
    Optional<Ticket> findByMovieIdAndSeatNumberAndStatus(Long movieId, String seatNumber, String status);
}
