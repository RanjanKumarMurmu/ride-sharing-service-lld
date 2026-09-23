package com.ridesharing.strategy;

import com.ridesharing.enums.RideType;
import com.ridesharing.model.Driver;
import com.ridesharing.model.Location;

import java.util.Comparator;
import java.util.List;

public class NearestDriverMatchingStrategy
        implements DriverMatchingStrategy {

    private static final double MAX_DISTANCE = 50.0;

    @Override
    public List<Driver> findDrivers(
            List<Driver> drivers,
            Location pickupLocation,
            RideType rideType
    ) {

        return drivers.stream()
                .filter(Driver::isAvailable)
                .filter(driver ->
                        driver.getVehicle().getType() == rideType)
                .filter(driver ->
                        driver.getCurrentLocation()
                                .distanceTo(pickupLocation)
                                <= MAX_DISTANCE)
                .sorted(
                        Comparator.comparingDouble(
                                driver ->
                                        driver.getCurrentLocation()
                                                .distanceTo(pickupLocation)
                        )
                )
                .toList();
    }
}
