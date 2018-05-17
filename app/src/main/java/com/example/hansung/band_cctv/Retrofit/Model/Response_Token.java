package com.example.hansung.band_cctv.Retrofit.Model;

public class Response_Token {
    public String user_token;

    public Response_Token(String user_token) {
        this.user_token = user_token;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }
}
