package com.ridesharing.model;

import com.ridesharing.enums.DriverStatus;

public class Driver {

    private final String id;
    private final String name;
    private final Vehicle vehicle;

    private Location currentLocation;
    private DriverStatus status;

    public Driver(
            String id,
            String name,
            Vehicle vehicle,
            Location currentLocation
    ) {
        this.id = id;
        this.name = name;
        this.vehicle = vehicle;
        this.currentLocation = currentLocation;
        this.status = DriverStatus.OFFLINE;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public void updateLocation(Location location) {
        this.currentLocation = location;
    }

    public boolean isAvailable() {
        return status == DriverStatus.ONLINE;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", vehicle=" + vehicle +
                ", status=" + status +
                '}';
    }
}
