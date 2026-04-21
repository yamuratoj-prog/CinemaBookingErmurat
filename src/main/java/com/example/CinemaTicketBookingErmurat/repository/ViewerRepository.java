package com.example.CinemaTicketBookingErmurat.repository;

import com.example.CinemaTicketBookingErmurat.model.Viewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViewerRepository extends JpaRepository<Viewer, Long> {
    Optional<Viewer> findByEmail(String email);
}
