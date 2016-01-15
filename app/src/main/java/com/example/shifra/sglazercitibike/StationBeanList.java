package com.example.shifra.sglazercitibike;

import java.io.Serializable;

public class StationBeanList implements Serializable {

    private String stationName;
    private int availableDocks;
    private int totalDocks;
    private int availableBikes;
    private double latitude;
    private double longitude;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    private double distance;

    public StationBeanList(String stationName, int availableDocks, int totalDocks, int availableBikes, double latitude, double longitude) {
        this.stationName = stationName;
        this.availableDocks = availableDocks;
        this.totalDocks = totalDocks;
        this.availableBikes = availableBikes;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getAvailableDocks() {
        return availableDocks;
    }

    public void setAvailableDocks(int availableDocks) {
        this.availableDocks = availableDocks;
    }

    public int getTotalDocks() {
        return totalDocks;
    }

    public void setTotalDocks(int totalDocks) {
        this.totalDocks = totalDocks;
    }

    public int getAvailableBikes() {
        return availableBikes;
    }

    public void setAvailableBikes(int availableBikes) {
        this.availableBikes = availableBikes;
    }

}
