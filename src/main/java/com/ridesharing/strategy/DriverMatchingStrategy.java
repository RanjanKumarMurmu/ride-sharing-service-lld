package com.ridesharing.strategy;

import com.ridesharing.enums.RideType;
import com.ridesharing.model.Driver;
import com.ridesharing.model.Location;

import java.util.List;

public interface DriverMatchingStrategy {

    List<Driver> findDrivers(
            List<Driver> drivers,
            Location pickupLocation,
            RideType rideType
    );
}
