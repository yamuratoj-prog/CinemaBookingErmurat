package com.example.CinemaTicketBookingErmurat.service;

import com.example.CinemaTicketBookingErmurat.model.Viewer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ViewerService {

    private final List<Viewer> viewers = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public ViewerService() {
        // Предзаполненные данные
        viewers.add(new Viewer(idCounter.getAndIncrement(), "Ермурат", "Досов",
                "ermurat@mail.ru", "+7-705-111-22-33", 22));
        viewers.add(new Viewer(idCounter.getAndIncrement(), "Айгерим", "Бекова",
                "aigrim@mail.ru", "+7-705-444-55-66", 25));
        viewers.add(new Viewer(idCounter.getAndIncrement(), "Нурлан", "Абенов",
                "nurlan@mail.ru", "+7-707-777-88-99", 30));
    }

    public List<Viewer> getAllViewers() {
        return new ArrayList<>(viewers);
    }

    public Optional<Viewer> getViewerById(Long id) {
        return viewers.stream().filter(v -> v.getId().equals(id)).findFirst();
    }

    public Viewer createViewer(Viewer viewer) {
        viewer.setId(idCounter.getAndIncrement());
        viewers.add(viewer);
        return viewer;
    }

    public Optional<Viewer> updateViewer(Long id, Viewer updatedViewer) {
        return viewers.stream().filter(v -> v.getId().equals(id)).findFirst().map(v -> {
            v.setFirstName(updatedViewer.getFirstName());
            v.setLastName(updatedViewer.getLastName());
            v.setEmail(updatedViewer.getEmail());
            v.setPhone(updatedViewer.getPhone());
            v.setAge(updatedViewer.getAge());
            return v;
        });
    }

    public boolean deleteViewer(Long id) {
        return viewers.removeIf(v -> v.getId().equals(id));
    }

    public Optional<Viewer> getViewerByEmail(String email) {
        return viewers.stream().filter(v -> v.getEmail().equalsIgnoreCase(email)).findFirst();
    }
}

