package com.ridesharing.model;

import com.ridesharing.enums.RideType;

public class Vehicle {

    private final String licenseNumber;
    private final RideType type;

    public Vehicle(String licenseNumber, RideType type) {
        this.licenseNumber = licenseNumber;
        this.type = type;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public RideType getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + " - " + licenseNumber;
    }
}
