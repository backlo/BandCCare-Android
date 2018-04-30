package com.example.hansung.band_cctv.Retrofit.Model;

import com.google.gson.annotations.SerializedName;

public class Response_Sensor {
    @SerializedName("sensor_data")
    public int sensor_data;

    public int getSensor_data() {
        return sensor_data;
    }

    public void setSensor_data(int sensor_data) {
        this.sensor_data = sensor_data;
    }
}
