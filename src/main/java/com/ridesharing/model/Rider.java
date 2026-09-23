package com.ridesharing.model;

import com.ridesharing.observer.TripObserver;

public class Rider implements TripObserver {

    private final String riderId;

    public Rider(String riderId) {
        this.riderId = riderId;
    }

    @Override
    public void onUpdate(Trip trip) {

        System.out.println(
                "[RIDER NOTIFICATION] Rider "
                        + riderId
                        + " -> Trip "
                        + trip.getId()
                        + " status changed to "
                        + trip.getStatus()
        );
    }
}
