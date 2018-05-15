package com.example.hansung.band_cctv.Retrofit;

import android.util.Log;

import com.example.hansung.band_cctv.Retrofit.Model.Request_Alarm;
import com.example.hansung.band_cctv.Retrofit.Model.Request_App_member;
import com.example.hansung.band_cctv.Retrofit.Model.Request_Band_member;
import com.example.hansung.band_cctv.Retrofit.Model.Request_DB;
import com.example.hansung.band_cctv.Retrofit.Model.Request_Location;
import com.example.hansung.band_cctv.Retrofit.Model.Request_Login;
import com.example.hansung.band_cctv.Retrofit.Model.Request_Login2;
import com.example.hansung.band_cctv.Retrofit.Model.Request_Motor;
import com.example.hansung.band_cctv.Retrofit.Model.Request_exit_PI;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Band_Info;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Check;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Info;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Location;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Login;
import com.example.hansung.band_cctv.Retrofit.Model.Response_MaxIndex;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Sensor;

import java.util.HashMap;
import java.util.List;

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

    public void Insert_App_Member(HashMap<String, Object> parameters, final RetroCallback callback){
        apiService.Insert_App_Member(new Request_App_member(parameters)).enqueue(new Callback<Response_Check>() {
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

    public void Insert_Band_Member(HashMap<String,Object> parameters,final RetroCallback callback){
        apiService.Insert_Band_Member(new Request_Band_member(parameters)).enqueue(new Callback<Response_Check>() {
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
                    Log.e("GetMaxIndex onsuccess","success");
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
        apiService.Send_Alarm(new Request_Alarm(parameters)).enqueue(new Callback<Request_Alarm>() {
            @Override
            public void onResponse(Call<Request_Alarm> call, Response<Request_Alarm> response) {
                if (response.isSuccessful()) {
                    Log.e("Exit PI onsuccess","success");
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
        apiService.Motor_Controller(new Request_Motor(parameters)).enqueue(new Callback<Request_Motor>() {
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

    public void GetInfo(String id, final RetroCallback callback){
        apiService.GetInfo(id).enqueue(new Callback<List<Response_Info>>() {
            @Override
            public void onResponse(Call<List<Response_Info>> call, Response<List<Response_Info>> response) {
                if (response.isSuccessful()) {
                    Log.e("GetInfo success","success");
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Response_Info>> call, Throwable t) {

            }
        });
    }

    public void GetInfo_Band(int index, final RetroCallback callback){
        apiService.GetInfo_Band(index).enqueue(new Callback<List<Response_Band_Info>>() {
            @Override
            public void onResponse(Call<List<Response_Band_Info>> call, Response<List<Response_Band_Info>> response) {
                if (response.isSuccessful()) {
                    Log.e("Get band Info success","success");
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Response_Band_Info>> call, Throwable t) {
                Log.e("onfailure getinfo band","error"+t.toString());
            }
        });
    }

    public void Get_Location(final RetroCallback callback){
        apiService.Get_Location().enqueue(new Callback<List<Response_Location>>() {
            @Override
            public void onResponse(Call<List<Response_Location>> call, Response<List<Response_Location>> response) {
                if (response.isSuccessful()) {
                    Log.e("Get location success","success");
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Response_Location>> call, Throwable t) {

            }
        });
    }



    public void Put_Location(HashMap<String,Double> parameters, final RetroCallback callback){
        apiService.Put_Location(new Request_Location(parameters)).enqueue(new Callback<Response_Check>() {
            @Override
            public void onResponse(Call<Response_Check> call, Response<Response_Check> response) {
                if (response.isSuccessful()) {
                    Log.e("Put Location success","success");
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






}
