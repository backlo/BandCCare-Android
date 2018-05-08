package com.example.hansung.band_cctv.Retrofit;

import android.util.Log;

import com.example.hansung.band_cctv.Retrofit.Model.Request_DB;
import com.example.hansung.band_cctv.Retrofit.Model.Request_Login;
import com.example.hansung.band_cctv.Retrofit.Model.Request_Login2;
import com.example.hansung.band_cctv.Retrofit.Model.Request_exit_PI;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Check;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Login;
import com.example.hansung.band_cctv.Retrofit.Model.Response_MaxIndex;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Sensor;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    private RetroApiService apiService;
    public static String baseUrl = RetroApiService.Base_URL;
    private static Retrofit retrofit;
    public static RetroClient instance;

    public static RetroClient getInstance() {
        if (instance == null)
            instance = new RetroClient();
        return instance;
    }

    public RetroClient() {
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl).build();
    }

    public RetroClient createBaseApi() {
        apiService = create(RetroApiService.class);
        return this;
    }

    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    public void InsertDB(HashMap<String, Object> parameters, final RetroCallback callback) {
        apiService.InsertDB(new Request_DB(parameters)).enqueue(new Callback<Response_Check>() {
            @Override
            public void onResponse(Call<Response_Check> call, Response<Response_Check> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Response_Check> call, Throwable t) {
            }
        });
    }

    public void Login(HashMap<String, Object> parameter, final RetroCallback callback){
        apiService.Login(new Request_Login2(parameter)).enqueue(new Callback<Response_Login>() {
            @Override
            public void onResponse(Call<Response_Login> call, Response<Response_Login> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                    Log.e("login success","success");
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Response_Login> call, Throwable t) {
                Log.e("login onfailure","fail->"+t.toString());
            }
        });
    }

    public void CheckID(String id, final RetroCallback callback) {
        apiService.CheckID(id).enqueue(new Callback<Response_Check>() {
            @Override
            public void onResponse(Call<Response_Check> call, Response<Response_Check> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Response_Check> call, Throwable t) {

            }
        });
    }

    public void LoginID(HashMap<String, Object> parameters, final RetroCallback callback) {
        apiService.LoginID(new Request_Login(parameters)).enqueue(new Callback<Response_Login>() {
            @Override
            public void onResponse(Call<Response_Login> call, Response<Response_Login> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Response_Login> call, Throwable t) {

            }
        });
    }

    public void GetSensor(int index, final RetroCallback callback) {
        apiService.GetSensor(index).enqueue(new Callback<Response_Sensor>() {
            @Override
            public void onResponse(Call<Response_Sensor> call, Response<Response_Sensor> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Response_Sensor> call, Throwable t) {
                Log.e("onfailure@@",t.toString());
            }
        });
    }

    public void GetMaxIndex(final RetroCallback callback){
        apiService.GetMaxIndex().enqueue(new Callback<Response_MaxIndex>() {
            @Override
            public void onResponse(Call<Response_MaxIndex> call, Response<Response_MaxIndex> response) {
                if (response.isSuccessful()) {
                    Log.e("onsuccess","success");
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Response_MaxIndex> call, Throwable t) {
                Log.e("maxindex onfailure",t.toString());
            }
        });
    }

    public void Exit_PI(HashMap<String, Object> parameters,final RetroCallback callback){
        apiService.Exit_PI(new Request_exit_PI(parameters)).enqueue(new Callback<Request_exit_PI>() {
            @Override
            public void onResponse(Call<Request_exit_PI> call, Response<Request_exit_PI> response) {
                if (response.isSuccessful()) {
                    Log.e("onsuccess","success");
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
}
