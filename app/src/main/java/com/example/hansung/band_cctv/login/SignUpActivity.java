package com.example.hansung.band_cctv.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hansung.band_cctv.R;

public class SignUpActivity extends AppCompatActivity {
    int APP_USER_FRAG = 0;
    int BAND_USER_FRAG = 1;
    int DEVICE_FRAG = 2;
    Fragment fragment;
    Button next_btn, pre_btn;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        next_btn = (Button) findViewById(R.id.next_btn);
        pre_btn = (Button) findViewById(R.id.pre_btn);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new AddAppUserFragment()).commit();
        pre_btn.setVisibility(View.INVISIBLE);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                myBtnListener(count);

            }
        });

        pre_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;
                myBtnListener(count);
            }
        });
    }

    private void myBtnListener(int state) {
        if (state == APP_USER_FRAG) {
            pre_btn.setVisibility(View.INVISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new AddAppUserFragment()).commit();
        } else if (state == BAND_USER_FRAG) {
            pre_btn.setVisibility(View.VISIBLE);
            next_btn.setText("Next");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new AddBandUserFragment()).commit();
        } else if (state == DEVICE_FRAG) {
            next_btn.setText("완료");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new AddDeviceFragment()).commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int request = requestCode & 0xffff;

        // 프래그먼트에서 결과값을 받아야 한다면 아래와 같이...
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment.onActivityResult(request, resultCode, data);
    }


}
