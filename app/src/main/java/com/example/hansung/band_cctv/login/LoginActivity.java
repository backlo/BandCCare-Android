package com.example.hansung.band_cctv.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.activity.MainActivity;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText id_et = (EditText) findViewById(R.id.id_et);
        final EditText pw_et = (EditText) findViewById(R.id.pw_et);
        Button login_btn = (Button) findViewById(R.id.login_btn);
        Button signUp_btn = (Button) findViewById(R.id.signUp_btn);

        String id = id_et.getText().toString();
        String pw = pw_et.getText().toString();
        Intent intent;

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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
