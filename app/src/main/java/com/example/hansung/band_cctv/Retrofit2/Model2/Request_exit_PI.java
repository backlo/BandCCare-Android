package com.example.hansung.band_cctv.Retrofit2.Model2;

import java.util.HashMap;

public class Request_exit_PI {
    public String exit;

    public Request_exit_PI(HashMap<String,Object> parameters) {
        this.exit = (String)parameters.get("exit");
    }

    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

}
