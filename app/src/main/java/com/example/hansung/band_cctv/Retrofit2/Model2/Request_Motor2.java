package com.example.hansung.band_cctv.Retrofit2.Model2;

import java.util.HashMap;

public class Request_Motor2 {
    public String rsl;

    public Request_Motor2(HashMap<String, Object> parameters) {
        this.rsl = (String)parameters.get("rsl");
    }

    public String getRsl() {
        return rsl;
    }

    public void setRsl(String rsl) {
        this.rsl = rsl;
    }
}
