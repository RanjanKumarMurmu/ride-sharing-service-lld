package com.ridesharing.state;

import com.ridesharing.model.Driver;
import com.ridesharing.model.Trip;

public interface TripState {

    void request(Trip trip);

    void assign(Trip trip, Driver driver);

    void start(Trip trip);

    void end(Trip trip);
}
