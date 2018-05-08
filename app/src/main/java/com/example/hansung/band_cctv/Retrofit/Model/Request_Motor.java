package com.example.hansung.band_cctv.Retrofit.Model;

import java.util.HashMap;

public class Request_Motor {
    public String rsl;

    public Request_Motor(HashMap<String, Object> parameters) {
        this.rsl = (String)parameters.get("rsl");
    }

    public String getRsl() {
        return rsl;
    }

    public void setRsl(String rsl) {
        this.rsl = rsl;
    }
}
