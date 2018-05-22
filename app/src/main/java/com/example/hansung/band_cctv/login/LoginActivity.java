package com.example.hansung.band_cctv.login;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private static LoginActivity instance;
   // SharedPreferences login_pre;
   // SharedPreferences.Editor login_editor;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RetroClient retroClient;
    EditText editText_id;
    EditText editText_pw;
    Button login_btn;
    String id, pw;
    Boolean autoLogin = false;
    HashMap<String, Object> tokenmap;


    HashMap<String, Object> parameter;

    public static LoginActivity getInstance() {
        if (instance == null)
            instance = new LoginActivity();
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //login_pre = getSharedPreferences("logininfo", MODE_PRIVATE);
        //login_editor = login_pre.edit();

        sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        parameter = new HashMap<>();
        editText_id = findViewById(R.id.login_id_et);
        editText_pw = findViewById(R.id.login_pw_et);

        Button signUp_btn = findViewById(R.id.signUp_btn);
        login_btn = findViewById(R.id.login_btn);
        retroClient = RetroClient.getInstance().createBaseApi();

        android.widget.CheckBox autoLogin_ck = findViewById(R.id.autoLogin_check);
        // 1.sharedPreference를 통해 사용자의 자동로그인 설정 확인
        if (sharedPreferences.getBoolean("autoLogin", false)) {
            //자동 로그인 사용자라면 바로 로그인 버튼을 눌리게 한다.
          //  id = sharedPreferences.getString("id", null);
          //  login();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        autoLogin_ck.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.e("ssssssss","이프들어옴22");
                    autoLogin = true;
                } else {
                    // if unChecked, removeAll
                    Log.e("ssssssss","이ㅏ프233");

                    autoLogin = false;
                    //editor.clear();
                    //editor.commit();

                }
                editor.putBoolean("autoLogin", autoLogin);
                editor.commit();
            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = editText_id.getText().toString();
                pw = editText_pw.getText().toString();

                login();

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


    public void login() {
        // 서버 안통할때 용 코드 (지우지마세용)
//        if (autoLogin) {
//            login_editor.putString("id", id);
//            login_editor.putString("pw", pw);
//            login_editor.commit();
//        }
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);

        parameter.put("AppUserInfo_id", id);
        parameter.put("AppUserInfo_password", pw);
        Log.e("input id", "id->" + id);
        Log.e("input password", "password->" + pw);


        retroClient.Login(parameter, new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Response_Login data = (Response_Login) receivedData;
                Log.e("login get data", "data->" + data.getSuccess());
                editor.putString("id", id);
                editor.commit();
                if (data.getSuccess().equals("success")) {

                    //editor.putString("id", id);
                    if (autoLogin) {
                     // editor.putString("id", id);
                        editor.putString("pw", pw);
                       editor.commit();
                    }
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    FirebaseInstanceId.getInstance().getToken();

                    if (FirebaseInstanceId.getInstance().getToken() != null) {
                        Log.d("token", "token = " + FirebaseInstanceId.getInstance().getToken());
                    }

                    tokenmap = new HashMap<>();
                    tokenmap.put("user_id", id);
                    tokenmap.put("user_token", FirebaseInstanceId.getInstance().getToken());
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
}

