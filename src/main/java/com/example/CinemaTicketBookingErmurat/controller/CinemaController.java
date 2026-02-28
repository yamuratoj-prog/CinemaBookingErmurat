package com.example.CinemaTicketBookingErmurat.controller;

import com.example.CinemaTicketBookingErmurat.model.Cinema;
import com.example.CinemaTicketBookingErmurat.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST контроллер для управления кинотеатрами
 * Автор: Ермурат
 */
@RestController
@RequestMapping("/api/cinemas")
public class CinemaController {

    private final CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    // 1. Получить все кинотеатры
    @GetMapping
    public ResponseEntity<List<Cinema>> getAllCinemas() {
        List<Cinema> cinemas = cinemaService.getAllCinemas();
        return ResponseEntity.ok(cinemas);
    }

    // 2. Получить кинотеатр по ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCinemaById(@PathVariable Long id) {
        Optional<Cinema> cinema = cinemaService.getCinemaById(id);
        if (cinema.isPresent()) {
            return ResponseEntity.ok(cinema.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Кинотеатр с ID " + id + " не найден"));
    }

    // 3. Создать новый кинотеатр
    @PostMapping
    public ResponseEntity<Cinema> createCinema(@RequestBody Cinema cinema) {
        Cinema created = cinemaService.createCinema(cinema);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // 4. Обновить кинотеатр
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCinema(@PathVariable Long id,
                                          @RequestBody Cinema cinema) {
        Optional<Cinema> updated = cinemaService.updateCinema(id, cinema);
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Кинотеатр с ID " + id + " не найден"));
    }

    // 5. Удалить кинотеатр
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCinema(@PathVariable Long id) {
        boolean deleted = cinemaService.deleteCinema(id);
        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Кинотеатр с ID " + id + " успешно удалён"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Кинотеатр с ID " + id + " не найден"));
    }

    // 6. Найти кинотеатры по городу
    @GetMapping("/search")
    public ResponseEntity<?> getCinemasByCity(@RequestParam String city) {
        List<Cinema> cinemas = cinemaService.getCinemasByCity(city);
        if (cinemas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Кинотеатры в городе \"" + city + "\" не найдены"));
        }
        return ResponseEntity.ok(cinemas);
    }
}
