package com.example.CinemaTicketBookingErmurat.controller;

import com.example.CinemaTicketBookingErmurat.model.Viewer;
import com.example.CinemaTicketBookingErmurat.service.ViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST контроллер для управления зрителями
 * Автор: Ермурат
 */
@RestController
@RequestMapping("/api/viewers")
public class ViewerController {

    private final ViewerService viewerService;

    @Autowired
    public ViewerController(ViewerService viewerService) {
        this.viewerService = viewerService;
    }

    // 1. Получить всех зрителей
    @GetMapping
    public ResponseEntity<List<Viewer>> getAllViewers() {
        return ResponseEntity.ok(viewerService.getAllViewers());
    }

    // 2. Получить зрителя по ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getViewerById(@PathVariable Long id) {
        Optional<Viewer> viewer = viewerService.getViewerById(id);
        if (viewer.isPresent()) {
            return ResponseEntity.ok(viewer.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Зритель с ID " + id + " не найден"));
    }

    // 3. Зарегистрировать нового зрителя
    @PostMapping
    public ResponseEntity<Viewer> createViewer(@RequestBody Viewer viewer) {
        Viewer created = viewerService.createViewer(viewer);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // 4. Обновить данные зрителя
    @PutMapping("/{id}")
    public ResponseEntity<?> updateViewer(@PathVariable Long id,
                                          @RequestBody Viewer viewer) {
        Optional<Viewer> updated = viewerService.updateViewer(id, viewer);
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Зритель с ID " + id + " не найден"));
    }

    // 5. Удалить зрителя
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteViewer(@PathVariable Long id) {
        boolean deleted = viewerService.deleteViewer(id);
        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Зритель с ID " + id + " успешно удалён"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Зритель с ID " + id + " не найден"));
    }

    // 6. Найти зрителя по email
    @GetMapping("/search")
    public ResponseEntity<?> getViewerByEmail(@RequestParam String email) {
        Optional<Viewer> viewer = viewerService.getViewerByEmail(email);
        if (viewer.isPresent()) {
            return ResponseEntity.ok(viewer.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Зритель с email \"" + email + "\" не найден"));
    }
}
