package com.example.hansung.band_cctv.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
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
import com.example.hansung.band_cctv.Service.Service_Notification;
import com.example.hansung.band_cctv.activity.MainActivity;
import com.example.hansung.band_cctv.activity.VideoFragment;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private static LoginActivity instance;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static Boolean isAppUser;

    RetroClient retroClient;
    Intent intent1;

    EditText editText_id;
    EditText editText_pw;
    Button login_btn;
    Button signUp_btn;

    HashMap<String, Object> parameter;
    ImageView username_img;
    ImageView password_img;

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
        editText_id = (EditText) findViewById(R.id.login_id_et);
        editText_pw = (EditText) findViewById(R.id.login_pw_et);

        Button signUp_btn = (Button) findViewById(R.id.signUp_btn);
        Button login_app_btn = (Button) findViewById(R.id.login_app_btn);
        Button login_band_btn = (Button) findViewById(R.id.login_band_btn);

        username_img = (ImageView) findViewById(R.id.username_img);
        password_img = (ImageView) findViewById(R.id.password_img);

        editText_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText_id.getText().toString().length() == 0) {
                    username_img.setImageResource(R.drawable.username);
                } else {
                    username_img.setImageResource(R.drawable.username_full);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText_pw.getText().toString().length() == 0) {
                    password_img.setImageResource(R.drawable.password);
                } else {
                    password_img.setImageResource(R.drawable.password_full);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        retroClient = RetroClient.getInstance().createBaseApi();

        parameter.put("AppUserInfo_id", editText_id.getText().toString());
        parameter.put("AppUserInfo_password", editText_pw.getText().toString());

        login_app_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 서버 안통할때 용 코드 (지우지마세용)

                isAppUser = true;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("isAppUser", isAppUser);
                startActivity(intent);
                Toast.makeText(LoginActivity.this, "" + isAppUser, Toast.LENGTH_SHORT).show();
/*
                parameter.put("AppUserInfo_id", editText_id.getText().toString());
                parameter.put("AppUserInfo_password", editText_pw.getText().toString());

                Log.e("input id", "id->" + editText_id.getText().toString());
                Log.e("input password", "password->" + editText_pw.getText().toString());
*/
                retroClient.Login(parameter, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {
                       Response_Login data = (Response_Login) receivedData;
                        Log.e("login get data", "data->" + data.getSuccess());
                        if (data.getSuccess().equals("success")) {

/*
                            isAppUser = true;
                            Toast.makeText(LoginActivity.this, "" + isAppUser, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("isAppUser", isAppUser);
                              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            intent1 = new Intent(getApplicationContext(), Service_Notification.class);
                            startService(intent1);

                            editor.putString("id",editText_id.getText().toString());
                            editor.putString("pw",editText_pw.getText().toString());
                            editor.commit();*/
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

        login_band_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 서버 안통할때용 코드 (지우지마세요!)

                isAppUser = false;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                intent.putExtra("isAppUser", isAppUser);
                startActivity(intent);
                Toast.makeText(LoginActivity.this, "" + isAppUser, Toast.LENGTH_SHORT).show();
/*
                parameter.put("AppUserInfo_id", editText_id.getText().toString());
                parameter.put("AppUserInfo_password", editText_pw.getText().toString());

                Log.e("input id", "id->" + editText_id.getText().toString());
                Log.e("input password", "password->" + editText_pw.getText().toString());*/
               retroClient.Login(parameter, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {
                        Response_Login data = (Response_Login) receivedData;
                        Log.e("login get data", "data->" + data.getSuccess());
                        if (data.getSuccess().equals("success")) {
                         /*   isAppUser = false;
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            Toast.makeText(LoginActivity.this, "로그인성공", Toast.LENGTH_SHORT).show();
                              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("isAppUser", isAppUser);
                            startActivity(intent);
                            intent1 = new Intent(getApplicationContext(), Service_Notification.class);
                            startService(intent1);*/
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

}
