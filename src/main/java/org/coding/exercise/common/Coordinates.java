package org.coding.exercise.common;

public class Coordinates {

    private String name;

    private double latitude;
    private double longitude;
    private double elevation;

    public Coordinates(String name, double latitude, double longitude, double elevation) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getElevation() {
        return elevation;
    }
}
