package com.ridesharing.state;

import com.ridesharing.enums.DriverStatus;
import com.ridesharing.model.Driver;
import com.ridesharing.model.Trip;

public class InProgressState implements TripState {

    @Override
    public void request(Trip trip) {
        throw new IllegalStateException(
                "Trip already in progress."
        );
    }

    @Override
    public void assign(Trip trip, Driver driver) {
        throw new IllegalStateException(
                "Trip already has a driver."
        );
    }

    @Override
    public void start(Trip trip) {
        throw new IllegalStateException(
                "Trip already started."
        );
    }

    @Override
    public void end(Trip trip) {

        trip.setEndTime(System.currentTimeMillis());

        trip.setState(new CompletedState());

        if (trip.getDriver() != null) {
            trip.getDriver().setStatus(
                    DriverStatus.ONLINE
            );
        }

        System.out.println(
                "Trip " + trip.getId() + " completed."
        );

        trip.notifyObservers();
    }
}

