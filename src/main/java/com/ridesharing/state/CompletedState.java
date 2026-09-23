package com.ridesharing.state;

import com.ridesharing.model.Driver;
import com.ridesharing.model.Trip;

public class CompletedState implements TripState {

    @Override
    public void request(Trip trip) {
        throw new IllegalStateException(
                "Trip already completed."
        );
    }

    @Override
    public void assign(Trip trip, Driver driver) {
        throw new IllegalStateException(
                "Trip already completed."
        );
    }

    @Override
    public void start(Trip trip) {
        throw new IllegalStateException(
                "Trip already completed."
        );
    }

    @Override
    public void end(Trip trip) {
        throw new IllegalStateException(
                "Trip already completed."
        );
    }
}
