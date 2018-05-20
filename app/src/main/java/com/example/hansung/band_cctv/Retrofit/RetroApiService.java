package com.example.hansung.band_cctv.Retrofit;

import com.example.hansung.band_cctv.Retrofit.Model.Request_App_member;
import com.example.hansung.band_cctv.Retrofit.Model.Request_Band_member;
import com.example.hansung.band_cctv.Retrofit.Model.Request_Location;
import com.example.hansung.band_cctv.Retrofit.Model.Request_Login2;
import com.example.hansung.band_cctv.Retrofit.Model.Request_Token;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Band_Info;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Check;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Info;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Location;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Login;
import com.example.hansung.band_cctv.Retrofit.Model.Response_MaxIndex;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Sensor;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetroApiService {
    final String Base_URL = "http://192.168.0.6:4000";
    //final String Base_URL = "http://113.198.82.79:80";
    //@@회원가입
    @POST("/insert_app_member")
    Call<Response_Check> Insert_App_Member(@Body Request_App_member request_App_member);

    @POST("/insert_band_member")
    Call<Response_Check> Insert_Band_Member(@Body Request_Band_member request_band_member);

    //@@앱유저 정보가져오기
    @GET("/getinfo")
    Call<List<Response_Info>> GetInfo(@Query("id") String id);

    //@@밴드유저 정보가져오기
    @GET("/getbanduserinfo")
    Call<List<Response_Band_Info>> GetInfo_Band(@Query("index") int index);

    //@@로그인할때 아이디,비밀번호 일치확인
    @POST("/logincheck2")
    Call<Response_Login> Login(@Body Request_Login2 request_login2);

    //@@센서값받기
    @GET("/getsensor")
    Call<Response_Sensor> GetSensor(@Query("index") int index);

    //@@테이블의 마지막 인덱스값 가져오기
    @GET("/getmaxindex")
    Call<Response_MaxIndex> GetMaxIndex();

    //@@좌표값넣기
    @POST("/location_info")
    Call<Response_Check> Put_Location(@Body Request_Location request_location);

    //@@좌표값받기
    @GET("/location_info")
    Call<List<Response_Location>> Get_Location();

    //fcm
    @POST("/fcmtoken")
    Call<Response_Check> UserToken(@Body Request_Token request_token);

    @GET("/gettoken")
    Call<List<Response_Token>> GetToken(@Query("user_id") String user_id);
}
