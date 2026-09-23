package com.ridesharing;

import com.ridesharing.enums.RideType;
import com.ridesharing.model.Location;
import com.ridesharing.model.User;
import com.ridesharing.service.RideSharingService;
import com.ridesharing.strategy.NearestDriverMatchingStrategy;
import com.ridesharing.strategy.VehicleBasedPricingStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RideSharingServiceTest {

    @Test
    void shouldCreateRideSuccessfully() {

        RideSharingService service =
                new RideSharingService(
                        new VehicleBasedPricingStrategy(),
                        new NearestDriverMatchingStrategy()
                );

        User user =
                new User(
                        "U1",
                        "Rahul",
                        "9999999999"
                );

        service.registerUser(user);

        var trip =
                service.requestRide(
                        "U1",
                        new Location(10, 10),
                        new Location(20, 20),
                        RideType.SEDAN
                );

        assertNotNull(trip);
        assertEquals(
                RideType.SEDAN,
                trip.getRideType()
        );

        assertTrue(trip.getFare() > 0);
    }
}
