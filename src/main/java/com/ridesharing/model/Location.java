package com.ridesharing.model;

public class Location {

    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void updateLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double distanceTo(Location other) {

        double latDiff = latitude - other.latitude;
        double lonDiff = longitude - other.longitude;

        return Math.sqrt(
                latDiff * latDiff +
                        lonDiff * lonDiff
        );
    }

    @Override
    public String toString() {
        return "(" + latitude + ", " + longitude + ")";
    }
}
