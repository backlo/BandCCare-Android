package com.example.hansung.band_cctv.Retrofit.Model;

public class Response_lastPulse {
    public int sensor_data;
    public String touch_data;

    public int getSensor_data() {
        return sensor_data;
    }

    public void setSensor_data(int sensor_data) {
        this.sensor_data = sensor_data;
    }

    public String getTouch_data() {
        return touch_data;
    }

    public void setTouch_data(String touch_data) {
        this.touch_data = touch_data;
    }
}
