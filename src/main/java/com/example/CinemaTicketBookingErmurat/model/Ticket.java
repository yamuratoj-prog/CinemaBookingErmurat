package com.example.CinemaTicketBookingErmurat.model;

public class Ticket {

    private Long id;
    private Long viewerId;
    private Long movieId;
    private Long cinemaId;
    private String seatNumber;
    private double totalPrice;
    private String bookingTime;
    private String status; // BOOKED, CANCELLED

    public Ticket() {}

    public Ticket(Long id, Long viewerId, Long movieId, Long cinemaId,
                  String seatNumber, double totalPrice, String bookingTime, String status) {
        this.id = id;
        this.viewerId = viewerId;
        this.movieId = movieId;
        this.cinemaId = cinemaId;
        this.seatNumber = seatNumber;
        this.totalPrice = totalPrice;
        this.bookingTime = bookingTime;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getViewerId() { return viewerId; }
    public void setViewerId(Long viewerId) { this.viewerId = viewerId; }

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public Long getCinemaId() { return cinemaId; }
    public void setCinemaId(Long cinemaId) { this.cinemaId = cinemaId; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getBookingTime() { return bookingTime; }
    public void setBookingTime(String bookingTime) { this.bookingTime = bookingTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

