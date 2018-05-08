package com.example.hansung.band_cctv.Retrofit.Model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Request_App_member {

        @SerializedName("AppUserInfo_id")
        public  String AppUserInfo_id;
        @SerializedName("AppUserInfo_password")
        public  String AppUserInfo_password;
        @SerializedName("AppUserInfo_phone")
        public  String AppUserInfo_phone;
        @SerializedName("AppUserInfo_birthday")
        public  String AppUserInfo_birthday;
        @SerializedName("AppUserInfo_camera_id")
        public String AppUserInfo_camera_id;
        @SerializedName("AppUserInfo_band_id")
        public String AppUserInfo_band_id;

        public Request_App_member(HashMap<String, Object> parameters){
            this.AppUserInfo_id = (String)parameters.get("AppUserInfo_id");
            this.AppUserInfo_password = (String)parameters.get("AppUserInfo_password");
            this.AppUserInfo_phone = (String)parameters.get("AppUserInfo_phone");
            this.AppUserInfo_birthday = (String)parameters.get("AppUserInfo_birthday");
            this.AppUserInfo_camera_id = (String)parameters.get("AppUserInfo_camera_id");
            this.AppUserInfo_band_id = (String)parameters.get("AppUserInfo_band_id");
        }

    public String getAppUserInfo_id() {
        return AppUserInfo_id;
    }

    public void setAppUserInfo_id(String appUserInfo_id) {
        AppUserInfo_id = appUserInfo_id;
    }

    public String getAppUserInfo_password() {
        return AppUserInfo_password;
    }

    public void setAppUserInfo_password(String appUserInfo_password) {
        AppUserInfo_password = appUserInfo_password;
    }

    public String getAppUserInfo_phone() {
        return AppUserInfo_phone;
    }

    public void setAppUserInfo_phone(String appUserInfo_phone) {
        AppUserInfo_phone = appUserInfo_phone;
    }

    public String getAppUserInfo_birthday() {
        return AppUserInfo_birthday;
    }

    public void setAppUserInfo_birthday(String appUserInfo_birthday) {
        AppUserInfo_birthday = appUserInfo_birthday;
    }

    public String getAppUserInfo_camera_id() {
        return AppUserInfo_camera_id;
    }

    public void setAppUserInfo_camera_id(String appUserInfo_camera_id) {
        AppUserInfo_camera_id = appUserInfo_camera_id;
    }

    public String getAppUserInfo_band_id() {
        return AppUserInfo_band_id;
    }

    public void setAppUserInfo_band_id(String appUserInfo_band_id) {
        AppUserInfo_band_id = appUserInfo_band_id;
    }
}
