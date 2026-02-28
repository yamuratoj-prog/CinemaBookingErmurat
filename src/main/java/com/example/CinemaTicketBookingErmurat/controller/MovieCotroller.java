package com.example.CinemaTicketBookingErmurat.controller;

import com.example.CinemaTicketBookingErmurat.model.Movie;
import com.example.CinemaTicketBookingErmurat.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST контроллер для управления фильмами
 * Автор: Ермурат
 */
@RestController
@RequestMapping("/api/movies")
public class MovieCotroller {

    private final MovieService movieService;

    @Autowired
    public MovieCotroller(MovieService movieService) {
        this.movieService = movieService;
    }

    // 1. Получить все фильмы
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    // 2. Получить фильм по ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable Long id) {
        Optional<Movie> movie = movieService.getMovieById(id);
        if (movie.isPresent()) {
            return ResponseEntity.ok(movie.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Фильм с ID " + id + " не найден"));
    }

    // 3. Добавить новый фильм
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie created = movieService.createMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // 4. Обновить фильм
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id,
                                         @RequestBody Movie movie) {
        Optional<Movie> updated = movieService.updateMovie(id, movie);
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Фильм с ID " + id + " не найден"));
    }

    // 5. Удалить фильм
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        boolean deleted = movieService.deleteMovie(id);
        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Фильм с ID " + id + " успешно удалён"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Фильм с ID " + id + " не найден"));
    }

    // 6. Найти фильмы по жанру
    @GetMapping("/genre/{genre}")
    public ResponseEntity<?> getMoviesByGenre(@PathVariable String genre) {
        List<Movie> movies = movieService.getMoviesByGenre(genre);
        if (movies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Фильмы жанра \"" + genre + "\" не найдены"));
        }
        return ResponseEntity.ok(movies);
    }

    // 7. Получить фильмы по кинотеатру
    @GetMapping("/cinema/{cinemaId}")
    public ResponseEntity<?> getMoviesByCinema(@PathVariable Long cinemaId) {
        List<Movie> movies = movieService.getMoviesByCinema(cinemaId);
        if (movies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Фильмы в кинотеатре ID " + cinemaId + " не найдены"));
        }
        return ResponseEntity.ok(movies);
    }
}
