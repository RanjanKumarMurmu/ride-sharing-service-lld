package com.ridesharing.state;

import com.ridesharing.model.Driver;
import com.ridesharing.model.Trip;

public class RequestedState implements TripState {

    @Override
    public void request(Trip trip) {
        System.out.println("Trip already requested.");
    }

    @Override
    public void assign(Trip trip, Driver driver) {

        trip.setDriver(driver);
        trip.setState(new AssignedState());

        System.out.println(
                "Driver " + driver.getName()
                        + " assigned to trip " + trip.getId()
        );

        trip.notifyObservers();
    }

    @Override
    public void start(Trip trip) {
        throw new IllegalStateException(
                "Cannot start a trip before assigning driver."
        );
    }

    @Override
    public void end(Trip trip) {
        throw new IllegalStateException(
                "Cannot end a requested trip."
        );
    }
}
