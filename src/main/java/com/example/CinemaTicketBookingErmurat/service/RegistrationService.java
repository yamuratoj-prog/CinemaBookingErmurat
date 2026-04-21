package com.example.CinemaTicketBookingErmurat.service;

import com.example.CinemaTicketBookingErmurat.model.ConfirmationToken;
import com.example.CinemaTicketBookingErmurat.model.User;
import com.example.CinemaTicketBookingErmurat.repository.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RegistrationService {

    @Autowired
    private UserService userService;

    @Autowired
    private ConfirmationTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    public String register(User user) {
        User savedUser = userService.registerUser(user.getUsername(), user.getPassword(), user.getEmail());

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                savedUser
        );
        tokenRepository.save(confirmationToken);

        String link = "http://localhost:8080/api/auth/confirm?token=" + token;
        emailService.sendEmail(user.getEmail(), "Confirm your email",
                "Click the link to confirm your account: " + link);

        return token;
    }

    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        tokenRepository.updateConfirmedAt(token, LocalDateTime.now());
        userService.enableUser(confirmationToken.getUser().getEmail());

        return "Confirmed";
    }
}
