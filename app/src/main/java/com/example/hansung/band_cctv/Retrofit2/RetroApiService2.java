package com.example.hansung.band_cctv.Retrofit2;


import com.example.hansung.band_cctv.Retrofit2.Model2.Request_Alarm;
import com.example.hansung.band_cctv.Retrofit2.Model2.Request_Motor;
import com.example.hansung.band_cctv.Retrofit2.Model2.Request_exit_PI;
import com.example.hansung.band_cctv.Retrofit2.Model2.Response_Detect;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetroApiService2{
    final String Base_URL2 = "http://192.168.0.6:5000";
    //final String Base_URL = "http://172.30.1.31:4000";

    //@@PI프로세스 죽이기
    @POST("/exit")
    Call<Request_exit_PI> Exit_PI(@Body Request_exit_PI request_exit_pi);

    //@@모터제어
    @POST("/post")
    Call<Request_Motor> Motor_Controller(@Body Request_Motor request_motor);

    @POST("/post1")
    Call<Request_Motor> Motor_Controller2(@Body Request_Motor request_motor);

    //@@위험알람
    @POST("/alarm")
    Call<Request_Alarm> Send_Alarm(@Body Request_Alarm request_alarm);

    //@@움직임체크
    @GET("/detect")
    Call<Response_Detect> Detect();

}
