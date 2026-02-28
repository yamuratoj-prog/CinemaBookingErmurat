package com.example.CinemaTicketBookingErmurat.model;

import java.util.ArrayList;
import java.util.List;

public class Cinema {

    private Long id;
    private String name;
    private String address;
    private String city;
    private String phone;
    private int totalSeats;
    private List<Movie> movies = new ArrayList<>();

    public Cinema() {}

    public Cinema(Long id, String name, String address, String city, String phone, int totalSeats) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.totalSeats = totalSeats;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }

    public List<Movie> getMovies() { return movies; }
    public void setMovies(List<Movie> movies) { this.movies = movies; }
}
