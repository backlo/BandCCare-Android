package com.example.hansung.band_cctv.Retrofit.Model;

import java.util.HashMap;

public class Request_Login {
    public String id;
    public String password;


    public Request_Login(HashMap<String, Object> parameters){
        this.id = (String)parameters.get("id");
        this.password = (String)parameters.get("password");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
