package com.example.CinemaTicketBookingErmurat.service;

import com.example.CinemaTicketBookingErmurat.model.Cinema;
import com.example.CinemaTicketBookingErmurat.model.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CinemaService {

    private final List<Cinema> cinemas = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public CinemaService() {
        // Предзаполненные данные — Ермурат
        cinemas.add(new Cinema(idCounter.getAndIncrement(), "Кинотеатр Ермурат IMAX",
                "ул. Абая 10", "Алматы", "+7-727-123-45-67", 200));
        cinemas.add(new Cinema(idCounter.getAndIncrement(), "Ермурат Синема Плюс",
                "пр. Достык 55", "Алматы", "+7-727-987-65-43", 150));
        cinemas.add(new Cinema(idCounter.getAndIncrement(), "Ермурат Мегаплекс",
                "ул. Сатпаева 30", "Астана", "+7-717-111-22-33", 300));
    }

    public List<Cinema> getAllCinemas() {
        return new ArrayList<>(cinemas);
    }

    public Optional<Cinema> getCinemaById(Long id) {
        return cinemas.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public Cinema createCinema(Cinema cinema) {
        cinema.setId(idCounter.getAndIncrement());
        cinemas.add(cinema);
        return cinema;
    }

    public Optional<Cinema> updateCinema(Long id, Cinema updatedCinema) {
        return cinemas.stream().filter(c -> c.getId().equals(id)).findFirst().map(c -> {
            c.setName(updatedCinema.getName());
            c.setAddress(updatedCinema.getAddress());
            c.setCity(updatedCinema.getCity());
            c.setPhone(updatedCinema.getPhone());
            c.setTotalSeats(updatedCinema.getTotalSeats());
            return c;
        });
    }

    public boolean deleteCinema(Long id) {
        return cinemas.removeIf(c -> c.getId().equals(id));
    }

    public List<Cinema> getCinemasByCity(String city) {
        List<Cinema> result = new ArrayList<>();
        for (Cinema c : cinemas) {
            if (c.getCity().equalsIgnoreCase(city)) {
                result.add(c);
            }
        }
        return result;
    }
}

