package com.example.CinemaTicketBookingErmurat.config;

import com.example.CinemaTicketBookingErmurat.model.Cinema;
import com.example.CinemaTicketBookingErmurat.model.Movie;
import com.example.CinemaTicketBookingErmurat.model.User;
import com.example.CinemaTicketBookingErmurat.model.Viewer;
import com.example.CinemaTicketBookingErmurat.repository.CinemaRepository;
import com.example.CinemaTicketBookingErmurat.repository.MovieRepository;
import com.example.CinemaTicketBookingErmurat.repository.UserRepository;
import com.example.CinemaTicketBookingErmurat.repository.ViewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ViewerRepository viewerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Создать админа
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User("admin", passwordEncoder.encode("admin"), "admin@ermurat.com", Set.of("ADMIN"));
            admin.setEnabled(true);
            userRepository.save(admin);
        }

        // Предзаполнить кинотеатры
        if (cinemaRepository.count() == 0) {
            cinemaRepository.save(new Cinema(null, "Кинотеатр Ермурат IMAX", "ул. Абая 10", "Алматы", "+7-727-123-45-67", 200));
            cinemaRepository.save(new Cinema(null, "Ермурат Синема Плюс", "пр. Достык 55", "Алматы", "+7-727-987-65-43", 150));
            cinemaRepository.save(new Cinema(null, "Ермурат Мегаплекс", "ул. Сатпаева 30", "Астана", "+7-717-111-22-33", 300));
        }

        // Предзаполнить фильмы
        if (movieRepository.count() == 0) {
            movieRepository.save(new Movie(null, "Побег из Шоушенка", "Драма", "Фрэнк Дарабонт", 142, 1500.0, "2025-03-01 18:00", 50, 1L));
            movieRepository.save(new Movie(null, "Интерстеллар", "Научная фантастика", "Кристофер Нолан", 169, 2000.0, "2025-03-01 20:00", 80, 1L));
            movieRepository.save(new Movie(null, "Мстители: Финал", "Боевик", "Братья Руссо", 181, 2500.0, "2025-03-02 15:00", 120, 2L));
            movieRepository.save(new Movie(null, "Паразиты", "Триллер", "Пон Джун-хо", 132, 1800.0, "2025-03-02 19:00", 60, 2L));
            movieRepository.save(new Movie(null, "Дюна", "Научная фантастика", "Дени Вильнёв", 155, 2200.0, "2025-03-03 17:00", 100, 3L));
        }

        // Предзаполнить зрителей
        if (viewerRepository.count() == 0) {
            viewerRepository.save(new Viewer(null, "Ермурат", "Досов", "ermurat@mail.ru", "+7-705-111-22-33", 22));
            viewerRepository.save(new Viewer(null, "Айгерим", "Бекова", "aigrim@mail.ru", "+7-705-444-55-66", 25));
            viewerRepository.save(new Viewer(null, "Нурлан", "Абенов", "nurlan@mail.ru", "+7-707-777-88-99", 30));
        }
    }
}
