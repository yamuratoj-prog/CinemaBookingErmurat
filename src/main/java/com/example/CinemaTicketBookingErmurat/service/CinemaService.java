package com.example.CinemaTicketBookingErmurat.service;

import com.example.CinemaTicketBookingErmurat.model.Cinema;
import com.example.CinemaTicketBookingErmurat.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;

    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    public Optional<Cinema> getCinemaById(Long id) {
        return cinemaRepository.findById(id);
    }

    public Cinema createCinema(Cinema cinema) {
        return cinemaRepository.save(cinema);
    }

    public Optional<Cinema> updateCinema(Long id, Cinema updatedCinema) {
        return cinemaRepository.findById(id).map(cinema -> {
            cinema.setName(updatedCinema.getName());
            cinema.setAddress(updatedCinema.getAddress());
            cinema.setCity(updatedCinema.getCity());
            cinema.setPhone(updatedCinema.getPhone());
            cinema.setTotalSeats(updatedCinema.getTotalSeats());
            return cinemaRepository.save(cinema);
        });
    }

    public boolean deleteCinema(Long id) {
        if (cinemaRepository.existsById(id)) {
            cinemaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Cinema> getCinemasByCity(String city) {
        return cinemaRepository.findByCityIgnoreCase(city);
    }
}
