package com.example.CinemaTicketBookingErmurat.controller;

import com.example.CinemaTicketBookingErmurat.model.Cinema;
import com.example.CinemaTicketBookingErmurat.model.Movie;
import com.example.CinemaTicketBookingErmurat.model.Ticket;
import com.example.CinemaTicketBookingErmurat.model.Viewer;
import com.example.CinemaTicketBookingErmurat.service.CinemaService;
import com.example.CinemaTicketBookingErmurat.service.MovieService;
import com.example.CinemaTicketBookingErmurat.service.TicketService;
import com.example.CinemaTicketBookingErmurat.service.ViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ViewerService viewerService;

    // Управление кинотеатрами
    @GetMapping("/cinemas")
    public List<Cinema> getAllCinemas() {
        return cinemaService.getAllCinemas();
    }

    @PostMapping("/cinemas")
    public Cinema createCinema(@RequestBody Cinema cinema) {
        return cinemaService.createCinema(cinema);
    }

    @PutMapping("/cinemas/{id}")
    public ResponseEntity<Cinema> updateCinema(@PathVariable Long id, @RequestBody Cinema cinema) {
        return cinemaService.updateCinema(id, cinema)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/cinemas/{id}")
    public ResponseEntity<Void> deleteCinema(@PathVariable Long id) {
        if (cinemaService.deleteCinema(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Управление фильмами
    @GetMapping("/movies")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @PostMapping("/movies")
    public Movie createMovie(@RequestBody Movie movie) {
        return movieService.createMovie(movie);
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        return movieService.updateMovie(id, movie)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (movieService.deleteMovie(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Управление зрителями
    @GetMapping("/viewers")
    public List<Viewer> getAllViewers() {
        return viewerService.getAllViewers();
    }

    @PostMapping("/viewers")
    public Viewer createViewer(@RequestBody Viewer viewer) {
        return viewerService.createViewer(viewer);
    }

    @PutMapping("/viewers/{id}")
    public ResponseEntity<Viewer> updateViewer(@PathVariable Long id, @RequestBody Viewer viewer) {
        return viewerService.updateViewer(id, viewer)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/viewers/{id}")
    public ResponseEntity<Void> deleteViewer(@PathVariable Long id) {
        if (viewerService.deleteViewer(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Управление билетами
    @GetMapping("/tickets")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<Void> cancelTicket(@PathVariable Long id) {
        if (ticketService.cancelTicket(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
