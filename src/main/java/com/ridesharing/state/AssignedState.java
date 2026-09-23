package com.ridesharing.state;

import com.ridesharing.model.Driver;
import com.ridesharing.model.Trip;

public class AssignedState implements TripState {

    @Override
    public void request(Trip trip) {
        throw new IllegalStateException(
                "Trip has already been assigned."
        );
    }

    @Override
    public void assign(Trip trip, Driver driver) {
        throw new IllegalStateException(
                "Driver already assigned."
        );
    }

    @Override
    public void start(Trip trip) {

        trip.setState(new InProgressState());

        trip.getDriver().setStatus(
                com.ridesharing.enums.DriverStatus.IN_TRIP
        );

        trip.setStartTime(System.currentTimeMillis());

        System.out.println(
                "Trip " + trip.getId() + " started."
        );

        trip.notifyObservers();
    }

    @Override
    public void end(Trip trip) {
        throw new IllegalStateException(
                "Trip hasn't started yet."
        );
    }
}
