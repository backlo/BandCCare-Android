package com.example.hansung.band_cctv.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retroClient = RetroClient.getInstance().createBaseApi();

        final EditText id_et = (EditText) findViewById(R.id.id_et);
        final EditText pw_et = (EditText) findViewById(R.id.pw_et);
        Button login_btn = (Button) findViewById(R.id.login_btn);
        Button signUp_btn = (Button) findViewById(R.id.signUp_btn);

        final String id = id_et.getText().toString();
        final String pw = pw_et.getText().toString();
        Intent intent;

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("id",id);
                parameters.put("password",pw);

                retroClient.LoginID(parameters, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {
                        Response_Login data = (Response_Login)receivedData;
                        if(data.getSuccess().equals("success")){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
