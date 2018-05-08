package com.example.hansung.band_cctv.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit.RetroClient;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    int APP_USER_FRAG = 0;
    int BAND_USER_FRAG = 1;
    int DEVICE_FRAG = 2;
    int FINISH = 3;
    Fragment fragment;
    Button next_btn, pre_btn;
    int count = 0;

    public String user_password;
    public String user_birthday;
    public String user_id;
    public String user_phone;

    RetroClient retroClient;
    HashMap<String,Object> appuser_parameter;
    HashMap<String,Object> banduser_parameter;

    AddAppUserFragment addAppUserFragment = new AddAppUserFragment();
    AddBandUserFragment addBandUserFragment = new AddBandUserFragment();
    AddDeviceFragment addDeviceFragment = new AddDeviceFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        appuser_parameter = new HashMap<>();
        banduser_parameter = new HashMap<>();

        retroClient = RetroClient.getInstance().createBaseApi();

        next_btn = (Button) findViewById(R.id.next_btn);
        pre_btn = (Button) findViewById(R.id.pre_btn);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, addAppUserFragment).commit();
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, addAppUserFragment).commit();
        } else if (state == BAND_USER_FRAG) {
            pre_btn.setVisibility(View.VISIBLE);
            next_btn.setText("Next");
            appuser_parameter.put("AppUserInfo_id",addAppUserFragment.getuserId());

            appuser_parameter.put("AppUserInfo_password",addAppUserFragment.getuserPassword());

            appuser_parameter.put("AppUserInfo_phone",addAppUserFragment.getuserPhone());

            appuser_parameter.put("AppUserInfo_birthday",addAppUserFragment.getuserBirthday());

            //appuser_parameter.put("AppUserInfo_camera_id");
            //appuser_parameter.put("AppUserInfo_band_id");

            user_password = addAppUserFragment.getuserPassword();
            user_id = addAppUserFragment.getuserId();
            user_birthday = addAppUserFragment.getuserBirthday();
            user_phone = addAppUserFragment.getuserPhone();
            retroClient.Insert_App_Member(appuser_parameter, new RetroCallback() {
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

            Log.e("userinfo","result->"+user_phone+","+user_id+","+user_birthday+","+user_password);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, addBandUserFragment).commit();
        } else if (state == DEVICE_FRAG) {
            next_btn.setText("완료");
            banduser_parameter.put("BandUserInfo_name",addBandUserFragment.getBand_user_name());
            banduser_parameter.put("BandUserInfo_sex",1);
            banduser_parameter.put("BandUserInfo_phone",addBandUserFragment.getBand_user_phone());
            banduser_parameter.put("BandUserInfo_birth",addBandUserFragment.getBand_user_birthday());
            banduser_parameter.put("BandUserInfo_address",addBandUserFragment.getBand_user_address());

            retroClient.Insert_Band_Member(banduser_parameter, new RetroCallback() {
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


            Log.e("banduserinfo",""+addBandUserFragment.getBand_user_address()+","+addBandUserFragment.getBand_user_birthday()+","+addBandUserFragment.getBand_user_name()+","+addBandUserFragment.getBand_user_phone());

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, addDeviceFragment).commit();
        }else if (state == FINISH){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
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
