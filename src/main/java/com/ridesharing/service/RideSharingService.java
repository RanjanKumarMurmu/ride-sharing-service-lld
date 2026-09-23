package com.ridesharing.service;

import com.ridesharing.enums.DriverStatus;
import com.ridesharing.enums.RideType;
import com.ridesharing.model.Driver;
import com.ridesharing.model.Location;
import com.ridesharing.model.Rider;
import com.ridesharing.model.Trip;
import com.ridesharing.model.User;
import com.ridesharing.observer.TripObserver;
import com.ridesharing.payment.PaymentService;
import com.ridesharing.strategy.DriverMatchingStrategy;
import com.ridesharing.strategy.PricingStrategy;
import com.ridesharing.state.RequestedState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RideSharingService {

    private final PricingStrategy pricingStrategy;

    private final DriverMatchingStrategy driverMatchingStrategy;

    private final PaymentService paymentService;

    private final Map<String, User> users =
            new ConcurrentHashMap<>();

    private final Map<String, Driver> drivers =
            new ConcurrentHashMap<>();

    private final Map<String, Trip> trips =
            new ConcurrentHashMap<>();

    public RideSharingService(
            PricingStrategy pricingStrategy,
            DriverMatchingStrategy driverMatchingStrategy
    ) {

        this.pricingStrategy = pricingStrategy;
        this.driverMatchingStrategy = driverMatchingStrategy;
        this.paymentService = new PaymentService();
    }

    // -------------------------------------------------
    // USER
    // -------------------------------------------------

    public void registerUser(User user) {
        users.put(user.getId(), user);
    }

    // -------------------------------------------------
    // DRIVER
    // -------------------------------------------------

    public void registerDriver(Driver driver) {

        drivers.put(driver.getId(), driver);

        driver.setStatus(DriverStatus.ONLINE);
    }

    public Driver registerDriver(
            String id,
            String name,
            com.ridesharing.model.Vehicle vehicle,
            Location location
    ) {

        Driver driver =
                new Driver(
                        id,
                        name,
                        vehicle,
                        location
                );

        registerDriver(driver);

        return driver;
    }

    // -------------------------------------------------
    // REQUEST RIDE
    // -------------------------------------------------

    public Trip requestRide(
            String passengerId,
            Location pickup,
            Location destination,
            RideType rideType
    ) {

        User passenger = users.get(passengerId);

        if (passenger == null) {
            throw new IllegalArgumentException(
                    "Passenger not found."
            );
        }

        String tripId =
                UUID.randomUUID().toString();

        Trip trip =
                new Trip(
                        tripId,
                        passenger,
                        pickup,
                        destination,
                        rideType,
                        new RequestedState()
                );

        double fare =
                pricingStrategy.calculateFare(
                        pickup,
                        destination,
                        rideType
                );

        trip.setFare(fare);

        TripObserver rider =
                new Rider(passenger.getId());

        trip.addObserver(rider);

        passenger.addTrip(trip);

        trips.put(tripId, trip);

        trip.notifyObservers();

        return trip;
    }

    // -------------------------------------------------
    // FIND AVAILABLE DRIVERS
    // -------------------------------------------------

    public List<Driver> findDrivers(Trip trip) {

        return driverMatchingStrategy.findDrivers(
                new ArrayList<>(drivers.values()),
                trip.getPickupLocation(),
                trip.getRideType()
        );
    }

    // -------------------------------------------------
    // ACCEPT RIDE
    // -------------------------------------------------

    public synchronized void acceptRide(
            String driverId,
            String tripId
    ) {

        Driver driver = drivers.get(driverId);
        Trip trip = trips.get(tripId);

        if (driver == null) {
            throw new IllegalArgumentException(
                    "Driver not found."
            );
        }

        if (trip == null) {
            throw new IllegalArgumentException(
                    "Trip not found."
            );
        }

        if (!driver.isAvailable()) {
            throw new IllegalStateException(
                    "Driver is not available."
            );
        }

        // Critical concurrency check.
        // Two drivers cannot accept the same trip.
        if (trip.getDriver() != null) {
            throw new IllegalStateException(
                    "Trip already accepted by another driver."
            );
        }

        trip.assignDriver(driver);
    }

    // -------------------------------------------------
    // START TRIP
    // -------------------------------------------------

    public void startTrip(String tripId) {

        Trip trip = getTrip(tripId);

        trip.startTrip();
    }

    // -------------------------------------------------
    // UPDATE LOCATION
    // -------------------------------------------------

    public void updateDriverLocation(
            String tripId,
            Location location
    ) {

        Trip trip = getTrip(tripId);

        trip.updateDriverLocation(location);
    }

    // -------------------------------------------------
    // END TRIP
    // -------------------------------------------------

    public void endTrip(String tripId) {

        Trip trip = getTrip(tripId);

        trip.endTrip();

        paymentService.processPayment(trip);
    }

    // -------------------------------------------------
    // GET TRIP
    // -------------------------------------------------

    public Trip getTrip(String tripId) {

        Trip trip = trips.get(tripId);

        if (trip == null) {
            throw new IllegalArgumentException(
                    "Trip not found: " + tripId
            );
        }

        return trip;
    }

    // -------------------------------------------------
    // AVAILABLE REQUESTS
    // -------------------------------------------------

    public List<Trip> getAvailableTrips() {

        return trips.values()
                .stream()
                .filter(trip ->
                        trip.getStatus()
                                == com.ridesharing.enums.TripStatus.REQUESTED
                )
                .toList();
    }
}
