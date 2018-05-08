package com.example.hansung.band_cctv.Retrofit.Model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Request_Login2 {
    @SerializedName("AppUserInfo_id")
    public String AppUserInfo_id;
    @SerializedName("AppUserInfo_password")
    public String AppUserInfo_password;


    public Request_Login2(HashMap<String, Object> parameters){
        this.AppUserInfo_id = (String)parameters.get("AppUserInfo_id");
        this.AppUserInfo_password = (String)parameters.get("AppUserInfo_password");
    }

    public String getId() {
        return AppUserInfo_id;
    }

    public void setId(String id) {
        this.AppUserInfo_id = id;
    }

    public String getPassword() {
        return AppUserInfo_password;
    }

    public void setPassword(String password) {
        this.AppUserInfo_password = password;
    }
}
