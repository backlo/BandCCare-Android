package com.example.hansung.band_cctv.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Login;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit.RetroClient;
import com.example.hansung.band_cctv.activity.MainActivity;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    RetroClient retroClient;

    EditText editText_id;
    EditText editText_pw;
    Button login_btn;
    Button signUp_btn;

    HashMap<String, Object> parameter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retroClient = RetroClient.getInstance().createBaseApi();

        parameter.put("AppUserInfo_id",editText_id.getText().toString());
        parameter.put("AppUserInfo_password",editText_pw.getText().toString());

        editText_id = (EditText)findViewById(R.id.login_id_et);
        editText_pw = (EditText)findViewById(R.id.login_pw_et);

        Button login_btn = (Button) findViewById(R.id.login_btn);
        Button signUp_btn = (Button) findViewById(R.id.signUp_btn);

        parameter = new HashMap<>();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parameter.put("AppUserInfo_id",editText_id.getText().toString());
                parameter.put("AppUserInfo_password",editText_pw.getText().toString());

                Log.e("input id","id->"+editText_id.getText().toString());
                Log.e("input password","password->"+editText_pw.getText().toString());

                retroClient.Login(parameter, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {
                        Response_Login data = (Response_Login)receivedData;
                        Log.e("login get data","data->"+data.getSuccess());
                        if(data.getSuccess().equals("success")){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            Toast.makeText(LoginActivity.this, "로그인성공", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }else if(data.getSuccess().equals("nomatch")){
                            Toast.makeText(LoginActivity.this, "비밀번호 혹은 아이디를 확인하세요", Toast.LENGTH_SHORT).show();
                        }else{
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
                startActivity(intent);
            }
        });

    }

}
