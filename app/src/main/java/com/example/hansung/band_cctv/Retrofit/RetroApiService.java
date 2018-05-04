package com.example.hansung.band_cctv.Retrofit;

import com.example.hansung.band_cctv.Retrofit.Model.Request_DB;
import com.example.hansung.band_cctv.Retrofit.Model.Request_Login;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Check;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Login;
import com.example.hansung.band_cctv.Retrofit.Model.Response_MaxIndex;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Sensor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetroApiService {
    final String Base_URL = "http://192.168.0.6:4000";

    //@@회원가입
    @POST("/insertdb")
    Call<Response_Check> InsertDB(@Body Request_DB request_db);

    //@@아이디중복체크
    @GET("/checkid")
    Call<Response_Check> CheckID(@Query("id") String id);

    //@@아이디,비밀번호 일치 확인
    @POST("/logincheck")
    Call<Response_Login> LoginID(@Body Request_Login request_login);

    //@@센서값받기
    @GET("/getsensor")
    Call<Response_Sensor> GetSensor(@Query("index") int index);

    //@@테이블의 마지막 인덱스값 가져오기
    @GET("/getmaxindex")
    Call<Response_MaxIndex> GetMaxIndex();

}
