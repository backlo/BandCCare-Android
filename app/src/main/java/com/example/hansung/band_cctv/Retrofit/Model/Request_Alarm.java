package com.example.hansung.band_cctv.Retrofit.Model;

import java.util.HashMap;

public class Request_Alarm {
    public String alarm;

    public Request_Alarm(HashMap<String,Object> parameters) {
        this.alarm = (String)parameters.get("alarm");
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }
}
