package com.example.hansung.band_cctv.Retrofit.Model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Request_DB {
    @SerializedName("id")
    public  String userid;
    @SerializedName("password")
    public  String password;
    @SerializedName("phone")
    public  String phone;
    @SerializedName("name")
    public  String name;

    public Request_DB(HashMap<String, Object> parameters){
        this.userid = (String)parameters.get("id");
        this.password = (String)parameters.get("password");
        this.phone = (String)parameters.get("phone");
        this.name = (String)parameters.get("name");
    }

    public String getId(){
        return userid;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }


}
