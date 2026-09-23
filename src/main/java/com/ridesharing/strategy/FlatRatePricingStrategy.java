package com.ridesharing.strategy;

import com.ridesharing.enums.RideType;
import com.ridesharing.model.Location;

public class FlatRatePricingStrategy implements PricingStrategy {

    private static final double BASE_FARE = 50.0;

    @Override
    public double calculateFare(
            Location source,
            Location destination,
            RideType rideType
    ) {

        double distance = source.distanceTo(destination);

        return BASE_FARE + distance * 10;
    }
}
