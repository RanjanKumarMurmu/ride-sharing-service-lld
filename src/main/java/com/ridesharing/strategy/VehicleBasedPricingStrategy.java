package com.ridesharing.strategy;

import com.ridesharing.enums.RideType;
import com.ridesharing.model.Location;

public class VehicleBasedPricingStrategy implements PricingStrategy {

    private static final double RATE_PER_KM = 10.0;

    @Override
    public double calculateFare(
            Location source,
            Location destination,
            RideType rideType
    ) {

        double distance = source.distanceTo(destination);

        double multiplier;

        switch (rideType) {

            case SUV:
                multiplier = 2.0;
                break;

            case SEDAN:
                multiplier = 1.5;
                break;

            case AUTO:
                multiplier = 1.0;
                break;

            default:
                multiplier = 1.0;
        }

        return 50 + (distance * RATE_PER_KM * multiplier);
    }
}
