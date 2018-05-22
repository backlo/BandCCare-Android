package com.example.hansung.band_cctv.Retrofit2;

import android.util.Log;

import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit2.Model2.Request_Alarm;
import com.example.hansung.band_cctv.Retrofit2.Model2.Request_Motor;
import com.example.hansung.band_cctv.Retrofit2.Model2.Request_exit_PI;
import com.example.hansung.band_cctv.Retrofit2.Model2.Response_Detect;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient2 {
    private RetroApiService2 apiService2;
    public static String baseUrl = RetroApiService2.Base_URL2;
    private static Retrofit retrofit2;
    public static RetroClient2 instance;

    public static RetroClient2 getInstance() {
        if (instance == null)
            instance = new RetroClient2();
        return instance;
    }

    public RetroClient2() {
        retrofit2 = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl).build();
    }

    public RetroClient2 createBaseApi2() {
        apiService2 = create(RetroApiService2.class);
        return this;
    }

    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit2.create(service);
    }


    public void Exit_PI(HashMap<String, Object> parameters,final RetroCallback callback){
        apiService2.Exit_PI(new Request_exit_PI(parameters)).enqueue(new Callback<Request_exit_PI>() {
            @Override
            public void onResponse(Call<Request_exit_PI> call, Response<Request_exit_PI> response) {
                if (response.isSuccessful()) {
                    Log.e("Exit PI onsuccess","success");
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Request_exit_PI> call, Throwable t) {

            }
        });
    }

    public void Send_Alarm(HashMap<String ,Object> parameters , final RetroCallback callback){
        apiService2.Send_Alarm(new Request_Alarm(parameters)).enqueue(new Callback<Request_Alarm>() {
            @Override
            public void onResponse(Call<Request_Alarm> call, Response<Request_Alarm> response) {
                if (response.isSuccessful()) {
                    Log.e("Send Alarm onsuccess","success");
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Request_Alarm> call, Throwable t) {

            }
        });
    }

    public void Motor_Controller(HashMap<String,Object> parameters,final RetroCallback callback){
        apiService2.Motor_Controller(new Request_Motor(parameters)).enqueue(new Callback<Request_Motor>() {
            @Override
            public void onResponse(Call<Request_Motor> call, Response<Request_Motor> response) {
                if (response.isSuccessful()) {
                    Log.e("Motor ctl success","success");
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Request_Motor> call, Throwable t) {

            }
        });
    }

    public void Motor_Controller2(HashMap<String, Object> parameters, final RetroCallback callback){
        apiService2.Motor_Controller2(new Request_Motor(parameters)).enqueue(new Callback<Request_Motor>() {
            @Override
            public void onResponse(Call<Request_Motor> call, Response<Request_Motor> response) {
                if (response.isSuccessful()) {
                    Log.e("Motor ctl2 success","success");
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Request_Motor> call, Throwable t) {

            }
        });
    }
    public void Detect(final RetroCallback callback){
        apiService2.Detect().enqueue(new Callback<Response_Detect>() {
            @Override
            public void onResponse(Call<Response_Detect> call, Response<Response_Detect> response) {
                if (response.isSuccessful()) {
                    Log.e("Detect success","success");
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Response_Detect> call, Throwable t) {

            }
        });
    }
}
