package com.example.hansung.band_cctv.Retrofit.Model;

import java.util.HashMap;

public class Request_Token {
    String user_id;
    String user_token;

    public Request_Token(HashMap<String, Object> parameters){
        this.user_id = (String)parameters.get("user_id");
        this.user_token = (String)parameters.get("user_token");
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }
}
