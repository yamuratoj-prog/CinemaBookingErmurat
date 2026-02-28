package com.example.CinemaTicketBookingErmurat.model;

public class Movie {

    private Long id;
    private String title;
    private String genre;
    private String director;
    private int durationMinutes;
    private double price;
    private String showTime;
    private int availableSeats;
    private Long cinemaId;

    public Movie() {}

    public Movie(Long id, String title, String genre, String director,
                 int durationMinutes, double price, String showTime,
                 int availableSeats, Long cinemaId) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.director = director;
        this.durationMinutes = durationMinutes;
        this.price = price;
        this.showTime = showTime;
        this.availableSeats = availableSeats;
        this.cinemaId = cinemaId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getShowTime() { return showTime; }
    public void setShowTime(String showTime) { this.showTime = showTime; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public Long getCinemaId() { return cinemaId; }
    public void setCinemaId(Long cinemaId) { this.cinemaId = cinemaId; }
}
