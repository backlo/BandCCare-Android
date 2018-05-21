package com.example.hansung.band_cctv.login;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Login;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit.RetroClient;
import com.example.hansung.band_cctv.ServiceThread.SV_Data;
import com.example.hansung.band_cctv.activity.MainActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private static LoginActivity instance;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    RetroClient retroClient;
    Intent intent1;

    EditText editText_id;
    EditText editText_pw;
    Button login_btn;
    Button signUp_btn;
    String id;

    HashMap<String, Object> parameter;
    ImageView username_img;
    ImageView password_img;

    SV_Data sv_data;

    public static LoginActivity getInstance() {
        if (instance == null)
            instance = new LoginActivity();
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = getSharedPreferences("logininfo", MODE_PRIVATE);
        editor = pref.edit();

        parameter = new HashMap<>();
        editText_id = findViewById(R.id.login_id_et);
        editText_pw = findViewById(R.id.login_pw_et);

        Button signUp_btn = findViewById(R.id.signUp_btn);
        Button login_btn = findViewById(R.id.login_btn);

        retroClient = RetroClient.getInstance().createBaseApi();

        parameter.put("AppUserInfo_id", editText_id.getText().toString());
        parameter.put("AppUserInfo_password", editText_pw.getText().toString());

        login_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 서버 안통할때 용 코드 (지우지마세용)
/*
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/

                parameter.put("AppUserInfo_id", editText_id.getText().toString());
                parameter.put("AppUserInfo_password", editText_pw.getText().toString());

                Log.e("input id", "id->" + editText_id.getText().toString());
                Log.e("input password", "password->" + editText_pw.getText().toString());

                retroClient.Login(parameter, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {
                       Response_Login data = (Response_Login) receivedData;
                        Log.e("login get data", "data->" + data.getSuccess());
                        if (data.getSuccess().equals("success")) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);


                            editor.putString("id",editText_id.getText().toString());
                            editor.putString("pw",editText_pw.getText().toString());
                            editor.commit();

                            FirebaseInstanceId.getInstance().getToken();

                            if (FirebaseInstanceId.getInstance().getToken() != null) {
                                Log.d("token", "token = " + FirebaseInstanceId.getInstance().getToken());
                            }
                            sharedPreferences = getSharedPreferences("logininfo", MODE_PRIVATE);
                            id = sharedPreferences.getString("id", "null");


                            HashMap<String, Object> tokenmap = new HashMap<>();
                            tokenmap.put("user_id",id);
                            tokenmap.put("user_token",FirebaseInstanceId.getInstance().getToken());
                            retroClient.UserToken(tokenmap, new RetroCallback() {
                                @Override
                                public void onError(Throwable t) {

                                }

                                @Override
                                public void onSuccess(int code, Object receivedData) {

                                }

                                @Override
                                public void onFailure(int code) {

                                }
                            });
                        } else if (data.getSuccess().equals("nomatch")) {
                            Toast.makeText(LoginActivity.this, "비밀번호 혹은 아이디를 확인하세요", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "해당아이디가 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int code) {

                    }
                });
            }
        });


        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(Build.VERSION.SDK_INT > 23){
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED ||
                    ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED ||
                    ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                    ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_DENIED ||
                    ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {

                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{
                                Manifest.permission.GET_ACCOUNTS,
                                Manifest.permission.CALL_PHONE,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA},
                        0);
            }
        } else{
            Log.e("SDK:",Build.VERSION.SDK_INT+"<-마쉬멜로 밑 버전");
        }

    }

}
