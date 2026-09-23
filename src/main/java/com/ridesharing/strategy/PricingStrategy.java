package com.ridesharing.strategy;

import com.ridesharing.enums.RideType;
import com.ridesharing.model.Location;

public interface PricingStrategy {

    double calculateFare(
            Location source,
            Location destination,
            RideType rideType
    );
}
