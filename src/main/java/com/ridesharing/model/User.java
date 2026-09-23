package com.ridesharing.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final String id;
    private final String name;
    private final String contact;

    private final List<Trip> tripHistory = new ArrayList<>();

    public User(String id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public List<Trip> getTripHistory() {
        return tripHistory;
    }

    public void addTrip(Trip trip) {
        tripHistory.add(trip);
    }

    public void addTripHistory(Trip trip) {
        tripHistory.add(trip);
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
