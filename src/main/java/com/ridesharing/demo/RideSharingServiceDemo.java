package com.ridesharing.demo;

import com.ridesharing.enums.RideType;
import com.ridesharing.model.Driver;
import com.ridesharing.model.Location;
import com.ridesharing.model.User;
import com.ridesharing.model.Vehicle;
import com.ridesharing.service.RideSharingService;
import com.ridesharing.strategy.NearestDriverMatchingStrategy;
import com.ridesharing.strategy.VehicleBasedPricingStrategy;

import java.util.List;

public class RideSharingServiceDemo {

    public static void main(String[] args) {

        // -----------------------------------------
        // CREATE SERVICE
        // -----------------------------------------

        RideSharingService service =
                new RideSharingService(
                        new VehicleBasedPricingStrategy(),
                        new NearestDriverMatchingStrategy()
                );

        // -----------------------------------------
        // CREATE PASSENGER
        // -----------------------------------------

        User passenger =
                new User(
                        "U1",
                        "Rahul",
                        "9999999999"
                );

        service.registerUser(passenger);

        // -----------------------------------------
        // CREATE VEHICLES
        // -----------------------------------------

        Vehicle vehicle1 =
                new Vehicle(
                        "KA01AB1234",
                        RideType.SEDAN
                );

        Vehicle vehicle2 =
                new Vehicle(
                        "KA01XY5678",
                        RideType.SEDAN
                );

        // -----------------------------------------
        // REGISTER DRIVERS
        // -----------------------------------------

        Driver driver1 =
                service.registerDriver(
                        "D1",
                        "John",
                        vehicle1,
                        new Location(10, 10)
                );

        Driver driver2 =
                service.registerDriver(
                        "D2",
                        "David",
                        vehicle2,
                        new Location(20, 20)
                );

        // -----------------------------------------
        // REQUEST RIDE
        // -----------------------------------------

        Location pickup =
                new Location(11, 11);

        Location destination =
                new Location(15, 15);

        var trip =
                service.requestRide(
                        passenger.getId(),
                        pickup,
                        destination,
                        RideType.SEDAN
                );

        System.out.println();
        System.out.println("Trip created:");
        System.out.println(trip);

        // -----------------------------------------
        // FIND DRIVERS
        // -----------------------------------------

        List<Driver> availableDrivers =
                service.findDrivers(trip);

        System.out.println();
        System.out.println("Available drivers:");

        availableDrivers.forEach(
                System.out::println
        );

        // -----------------------------------------
        // ACCEPT RIDE
        // -----------------------------------------

        if (!availableDrivers.isEmpty()) {

            Driver selectedDriver =
                    availableDrivers.get(0);

            service.acceptRide(
                    selectedDriver.getId(),
                    trip.getId()
            );
        }

        // -----------------------------------------
        // START TRIP
        // -----------------------------------------

        System.out.println();

        service.startTrip(trip.getId());

        // -----------------------------------------
        // UPDATE LOCATION
        // -----------------------------------------

        System.out.println();

        service.updateDriverLocation(
                trip.getId(),
                new Location(13, 13)
        );

        service.updateDriverLocation(
                trip.getId(),
                new Location(14, 14)
        );

        // -----------------------------------------
        // END TRIP
        // -----------------------------------------

        System.out.println();

        service.endTrip(trip.getId());

        // -----------------------------------------
        // FINAL RESULT
        // -----------------------------------------

        System.out.println();
        System.out.println("Final Trip:");
        System.out.println(trip);
    }
}
