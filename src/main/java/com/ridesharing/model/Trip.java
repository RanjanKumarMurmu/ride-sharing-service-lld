package com.ridesharing.model;

import com.ridesharing.enums.RideType;
import com.ridesharing.enums.TripStatus;
import com.ridesharing.observer.TripObserver;
import com.ridesharing.state.TripState;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Trip {

    private final String id;

    private final User passenger;

    private Driver driver;

    private final Location pickupLocation;
    private final Location dropoffLocation;

    private final RideType rideType;

    private double fare;

    private TripState state;

    private long startTime;
    private long endTime;

    private final List<TripObserver> observers =
            new CopyOnWriteArrayList<>();

    public Trip(
            String id,
            User passenger,
            Location pickupLocation,
            Location dropoffLocation,
            RideType rideType,
            TripState initialState
    ) {

        this.id = id;
        this.passenger = passenger;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.rideType = rideType;
        this.state = initialState;
    }

    public String getId() {
        return id;
    }

    public User getPassenger() {
        return passenger;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public Location getDropoffLocation() {
        return dropoffLocation;
    }

    public RideType getRideType() {
        return rideType;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public TripState getState() {
        return state;
    }

    public synchronized void setState(TripState state) {
        this.state = state;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public TripStatus getStatus() {

        if (state instanceof com.ridesharing.state.RequestedState)
            return TripStatus.REQUESTED;

        if (state instanceof com.ridesharing.state.AssignedState)
            return TripStatus.ASSIGNED;

        if (state instanceof com.ridesharing.state.InProgressState)
            return TripStatus.IN_PROGRESS;

        if (state instanceof com.ridesharing.state.CompletedState)
            return TripStatus.COMPLETED;

        return TripStatus.CANCELLED;
    }

    public void addObserver(TripObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {

        for (TripObserver observer : observers) {
            observer.onUpdate(this);
        }
    }

    public synchronized void assignDriver(Driver driver) {
        state.assign(this, driver);
    }

    public synchronized void startTrip() {
        state.start(this);
    }

    public synchronized void endTrip() {
        state.end(this);
    }

    public void updateDriverLocation(Location location) {

        if (driver != null) {
            driver.updateLocation(location);
            notifyObservers();
        }
    }

    @Override
    public String toString() {

        return "Trip{" +
                "id='" + id + '\'' +
                ", passenger=" + passenger.getName() +
                ", driver=" +
                (driver == null ? "None" : driver.getName()) +
                ", status=" + getStatus() +
                ", fare=" + fare +
                '}';
    }
}
