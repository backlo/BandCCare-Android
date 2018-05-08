package com.example.hansung.band_cctv.Retrofit.Model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Request_Band_member {
    @SerializedName("BandUserInfo_name")
    public String BandUserInfo_name;
    @SerializedName("BandUserInfo_sex")
    public int BandUserInfo_sex;
    @SerializedName("BandUserInfo_phone")
    public String BandUserInfo_phone;
    @SerializedName("BandUserInfo_birth")
    public String BandUserInfo_birth;
    @SerializedName("BandUserInfo_address")
    public String BandUserInfo_address;

    public Request_Band_member(HashMap<String, Object> parameters){
        this.BandUserInfo_name = (String)parameters.get("BandUserInfo_name");
        this.BandUserInfo_sex = (int)parameters.get("BandUserInfo_sex");
        this.BandUserInfo_phone = (String)parameters.get("BandUserInfo_phone");
        this.BandUserInfo_birth = (String)parameters.get("BandUserInfo_birth");
        this.BandUserInfo_address = (String)parameters.get("BandUserInfo_address");
    }

    public String getBandUserInfo_name() {
        return BandUserInfo_name;
    }

    public void setBandUserInfo_name(String bandUserInfo_name) {
        BandUserInfo_name = bandUserInfo_name;
    }

    public int getBandUserInfo_sex() {
        return BandUserInfo_sex;
    }

    public void setBandUserInfo_sex(int bandUserInfo_sex) {
        BandUserInfo_sex = bandUserInfo_sex;
    }

    public String getBandUserInfo_phone() {
        return BandUserInfo_phone;
    }

    public void setBandUserInfo_phone(String bandUserInfo_phone) {
        BandUserInfo_phone = bandUserInfo_phone;
    }

    public String getBandUserInfo_birth() {
        return BandUserInfo_birth;
    }

    public void setBandUserInfo_birth(String bandUserInfo_birth) {
        BandUserInfo_birth = bandUserInfo_birth;
    }

    public String getBandUserInfo_address() {
        return BandUserInfo_address;
    }

    public void setBandUserInfo_address(String bandUserInfo_address) {
        BandUserInfo_address = bandUserInfo_address;
    }
}
