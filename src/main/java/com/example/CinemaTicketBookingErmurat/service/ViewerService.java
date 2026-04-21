package com.example.CinemaTicketBookingErmurat.service;

import com.example.CinemaTicketBookingErmurat.model.Viewer;
import com.example.CinemaTicketBookingErmurat.repository.ViewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ViewerService {

    @Autowired
    private ViewerRepository viewerRepository;

    public List<Viewer> getAllViewers() {
        return viewerRepository.findAll();
    }

    public Optional<Viewer> getViewerById(Long id) {
        return viewerRepository.findById(id);
    }

    public Optional<Viewer> getViewerByEmail(String email) {
        return viewerRepository.findByEmail(email);
    }

    public Viewer createViewer(Viewer viewer) {
        return viewerRepository.save(viewer);
    }

    public Optional<Viewer> updateViewer(Long id, Viewer updatedViewer) {
        return viewerRepository.findById(id).map(viewer -> {
            viewer.setFirstName(updatedViewer.getFirstName());
            viewer.setLastName(updatedViewer.getLastName());
            viewer.setEmail(updatedViewer.getEmail());
            viewer.setPhone(updatedViewer.getPhone());
            viewer.setAge(updatedViewer.getAge());
            return viewerRepository.save(viewer);
        });
    }

    public boolean deleteViewer(Long id) {
        if (viewerRepository.existsById(id)) {
            viewerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
