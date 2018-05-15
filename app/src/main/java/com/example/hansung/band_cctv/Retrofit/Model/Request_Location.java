package com.example.hansung.band_cctv.Retrofit.Model;

import java.util.HashMap;

public class Request_Location {
    public double latitude;
    public double longitude;

    public Request_Location(HashMap<String, Double> parameters){
        this.latitude = (double) parameters.get("latitude");
        this.longitude = (double) parameters.get("longitude");
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
