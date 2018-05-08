package com.example.hansung.band_cctv.Retrofit.Model;

import java.util.HashMap;

public class Request_noexit_PI {
    public String exit;

    public Request_noexit_PI(HashMap<String,Object> parameters) {
        this.exit = (String)parameters.get("exit");
    }

    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }
}
