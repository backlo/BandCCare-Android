package com.example.hansung.band_cctv.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Band_Info;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Info;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit.RetroClient;
import com.example.hansung.band_cctv.login.LoginActivity;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    SharedPreferences sharedPreferences;
    RetroClient retroClient;
    SharedPreferences login_pre;
    SharedPreferences.Editor login_editor;
    public static int sendindex;
    public static ArrayList<Response_Info> infoArrayList;
    public static ArrayList<Response_Band_Info> bandInfoArrayList;
    public String id;
    public String app_info_id;

    TextView app_id_tv;
    TextView app_phone_tv;
    TextView app_birth_tv;

    TextView band_name_tv;
    TextView band_phone_tv;
    TextView band_sex_tv;
    TextView band_birth_tv;
    TextView band_address_tv;

    TextView camera_tv;
    TextView band_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        login_pre = getSharedPreferences("logininfo", MODE_PRIVATE);
        login_editor = login_pre.edit();
        app_id_tv = (TextView) findViewById(R.id.app_id_tv);
        app_phone_tv = (TextView) findViewById(R.id.app_phone_tv);
        app_birth_tv = (TextView) findViewById(R.id.app_birth_tv);

        band_name_tv = (TextView) findViewById(R.id.band_name_tv);
        band_phone_tv = (TextView) findViewById(R.id.band_phone_tv);
        band_sex_tv = (TextView) findViewById(R.id.band_sex_tv);
        band_birth_tv = (TextView) findViewById(R.id.band_birth_tv);
        band_address_tv = (TextView) findViewById(R.id.band_address_tv);

        camera_tv = (TextView) findViewById(R.id.camera_tv);
        band_tv = (TextView) findViewById(R.id.band_tv);

        retroClient = RetroClient.getInstance().createBaseApi();

        sharedPreferences = getSharedPreferences("logininfo", MODE_PRIVATE);
        id = sharedPreferences.getString("id", "null");
        String pw = sharedPreferences.getString("pw", "null");
        Log.e("shared info!!!!", "id->" + id + "," + "pw->" + pw);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                Intent intent;
                switch (id) {
                    case R.id.navigation_myPage:
                        intent = new Intent(getApplicationContext(), InfoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        break;

                    case R.id.navigation_logout:
                        login_editor.putBoolean("autoLogin", false);
                        login_editor.clear();
                        login_editor.commit();
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                }
                return true;
            }
        });
        retroClient.GetInfo(id, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
            }
            @Override
            public void onSuccess(int code, Object receivedData) {
                infoArrayList = (ArrayList<Response_Info>)receivedData;
                Log.e("user info index->",""+infoArrayList.get(0).getAppUserInfo_index());
                Log.e("user info band_id->",""+infoArrayList.get(0).getAppUserInfo_band_id());
                Log.e("user info birthday->",""+infoArrayList.get(0).getAppUserInfo_birthday());
                Log.e("user info camera_id->",""+infoArrayList.get(0).getAppUserInfo_camera_id());
                Log.e("user info id->",""+infoArrayList.get(0).getAppUserInfo_id());
                Log.e("user info pw->",""+infoArrayList.get(0).getAppUserInfo_password());
                Log.e("user info phone->",""+infoArrayList.get(0).getAppUserInfo_phone());
                sendindex = infoArrayList.get(0).getAppUserInfo_index();

                retroClient.GetInfo_Band(sendindex, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {
                        bandInfoArrayList = (ArrayList<Response_Band_Info>) receivedData;
                        Log.e("band user address->", "" + bandInfoArrayList.get(0).getBandUserInfo_address());
                        Log.e("band user birth->", "" + bandInfoArrayList.get(0).getBandUserInfo_birth());
                        Log.e("band user index->", "" + bandInfoArrayList.get(0).getBandUserInfo_index());
                        Log.e("band user name->", "" + bandInfoArrayList.get(0).getBandUserInfo_name());
                        Log.e("band user phone->", "" + bandInfoArrayList.get(0).getBandUserInfo_phone());
                        Log.e("band user sex->", "" + bandInfoArrayList.get(0).getBandUserInfo_sex());

                        app_info_id = infoArrayList.get(0).getAppUserInfo_id();

                        app_id_tv.setText(infoArrayList.get(0).AppUserInfo_id);
                        app_phone_tv.setText(infoArrayList.get(0).AppUserInfo_phone);
                        app_birth_tv.setText(infoArrayList.get(0).AppUserInfo_birthday);
                        band_name_tv.setText(bandInfoArrayList.get(0).BandUserInfo_name);
                        band_phone_tv.setText(bandInfoArrayList.get(0).BandUserInfo_phone);
                        band_sex_tv.setText(bandInfoArrayList.get(0).BandUserInfo_sex);
                        band_birth_tv.setText(bandInfoArrayList.get(0).BandUserInfo_birth);
                        band_address_tv.setText(bandInfoArrayList.get(0).BandUserInfo_address);

                    }

                    @Override
                    public void onFailure(int code) {
                    }
                });

                camera_tv = (TextView) findViewById(R.id.camera_tv);
                band_tv = (TextView) findViewById(R.id.band_tv);
            }

            @Override
            public void onFailure(int code) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}
