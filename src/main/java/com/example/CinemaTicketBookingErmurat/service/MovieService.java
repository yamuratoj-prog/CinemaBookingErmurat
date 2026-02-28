package com.example.CinemaTicketBookingErmurat.service;

import com.example.CinemaTicketBookingErmurat.model.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MovieService {

    private final List<Movie> movies = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public MovieService() {
        // Предзаполненные данные
        movies.add(new Movie(idCounter.getAndIncrement(), "Побег из Шоушенка",
                "Драма", "Фрэнк Дарабонт", 142, 1500.0,
                "2025-03-01 18:00", 50, 1L));
        movies.add(new Movie(idCounter.getAndIncrement(), "Интерстеллар",
                "Научная фантастика", "Кристофер Нолан", 169, 2000.0,
                "2025-03-01 20:00", 80, 1L));
        movies.add(new Movie(idCounter.getAndIncrement(), "Мстители: Финал",
                "Боевик", "Братья Руссо", 181, 2500.0,
                "2025-03-02 15:00", 120, 2L));
        movies.add(new Movie(idCounter.getAndIncrement(), "Паразиты",
                "Триллер", "Пон Джун-хо", 132, 1800.0,
                "2025-03-02 19:00", 60, 2L));
        movies.add(new Movie(idCounter.getAndIncrement(), "Дюна",
                "Научная фантастика", "Дени Вильнёв", 155, 2200.0,
                "2025-03-03 17:00", 100, 3L));
    }

    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies);
    }

    public Optional<Movie> getMovieById(Long id) {
        return movies.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public Movie createMovie(Movie movie) {
        movie.setId(idCounter.getAndIncrement());
        movies.add(movie);
        return movie;
    }

    public Optional<Movie> updateMovie(Long id, Movie updatedMovie) {
        return movies.stream().filter(m -> m.getId().equals(id)).findFirst().map(m -> {
            m.setTitle(updatedMovie.getTitle());
            m.setGenre(updatedMovie.getGenre());
            m.setDirector(updatedMovie.getDirector());
            m.setDurationMinutes(updatedMovie.getDurationMinutes());
            m.setPrice(updatedMovie.getPrice());
            m.setShowTime(updatedMovie.getShowTime());
            m.setAvailableSeats(updatedMovie.getAvailableSeats());
            m.setCinemaId(updatedMovie.getCinemaId());
            return m;
        });
    }

    public boolean deleteMovie(Long id) {
        return movies.removeIf(m -> m.getId().equals(id));
    }

    public List<Movie> getMoviesByGenre(String genre) {
        List<Movie> result = new ArrayList<>();
        for (Movie m : movies) {
            if (m.getGenre().equalsIgnoreCase(genre)) {
                result.add(m);
            }
        }
        return result;
    }

    public List<Movie> getMoviesByCinema(Long cinemaId) {
        List<Movie> result = new ArrayList<>();
        for (Movie m : movies) {
            if (m.getCinemaId().equals(cinemaId)) {
                result.add(m);
            }
        }
        return result;
    }
}

