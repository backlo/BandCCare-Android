package com.example.hansung.band_cctv.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hansung.band_cctv.MyPagerAdapter;
import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Token;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit.RetroClient;
import com.example.hansung.band_cctv.Retrofit2.Model2.Request_exit_PI;
import com.example.hansung.band_cctv.Retrofit2.RetroClient2;
import com.example.hansung.band_cctv.ServiceThread.SV_Data;
import com.example.hansung.band_cctv.login.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static Boolean isAppUser;
    public static MainActivity instance;
    // public static MyHandler myHandler;
    private DrawerLayout mDrawerLayout;
    RetroClient2 retroClient2;
    RetroClient retroClient;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public String exit;
    public String noexit;
    String id;

    HashMap<String, Object> parameter;
    HashMap<String, Object> parameter2;
    String noalarm = "noalarm";
    HashMap<String, Object> noalarmmap;

    public static MainActivity getInstance() {
        if (instance == null)
            instance = new MainActivity();
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noalarmmap = new HashMap<>();
        noalarmmap.put("alarm",noalarm);
        retroClient2 = RetroClient2.getInstance().createBaseApi2();
        retroClient = RetroClient.getInstance().createBaseApi();

        sharedPreferences = getSharedPreferences("logininfo", MODE_PRIVATE);
        id = sharedPreferences.getString("id", "null");

        retroClient.GetToken(id, new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }
            @Override
            public void onSuccess(int code, Object receivedData) {
                ArrayList<Response_Token> data = (ArrayList<Response_Token>)receivedData;
                Log.e("get token",data.get(0).getUser_token());
            }

            @Override
            public void onFailure(int code) {

            }
        });

        isAppUser = getIntent().getBooleanExtra("isAppUser", false);

        parameter = new HashMap<>();
        parameter2 = new HashMap<>();
        exit = "exit";
        noexit = "noexit";
        retroClient = RetroClient.getInstance().createBaseApi();
        parameter.put("exit", exit);
        parameter2.put("exit", noexit);

        retroClient2.Exit_PI(parameter2, new RetroCallback() {
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

        retroClient2.Send_Alarm(noalarmmap, new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.e("alaram","noalarm");
            }

            @Override
            public void onFailure(int code) {

            }
        });

        //myHandler = new MyHandler();
        MyPagerAdapter mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(mPagerAdapter);

        TabLayout mTab = findViewById(R.id.tabs);
        mTab.setupWithViewPager(mViewPager);

        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            mTab.getTabAt(i).setIcon(mPagerAdapter.getIcon(i));
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                Intent intent;
                switch (id) {
                    case R.id.navigation_item1:
                        Toast.makeText(MainActivity.this, "App user info", Toast.LENGTH_LONG).show();
                        intent = new Intent(getApplicationContext(), InfoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("info",1);
                        startActivity(intent);
                        break;

                    case R.id.navigation_item2:
                        Toast.makeText(MainActivity.this, "band user info", Toast.LENGTH_LONG).show();
                        intent = new Intent(getApplicationContext(), InfoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("info",2);
                        startActivity(intent);
                        break;

                    case R.id.navigation_item3:
                        Toast.makeText(MainActivity.this, "device info", Toast.LENGTH_LONG).show();
                        intent = new Intent(getApplicationContext(), InfoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("info",3);
                        startActivity(intent);
                        break;

                    case R.id.navigation_logout:
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP

                        );
                        startActivity(intent);
                        break;

                }

                return true;
            }
        });
    }

 /*   public class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }*/


    @Override
    protected void onDestroy() {
        Intent intent2 = new Intent(getApplicationContext(),SV_Data.class);
        startService(intent2);
        super.onDestroy();
        Log.e("main ondestroy", "destroy");
        retroClient2.Exit_PI(parameter, new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Request_exit_PI data = (Request_exit_PI) receivedData;
                Log.e("exit data@@", data.getExit());
            }

            @Override
            public void onFailure(int code) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_119:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:119")));
                break;
            case R.id.action_call:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-1234-1234")));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public Boolean getIsAppUser() {
        return isAppUser;
    }
/*
    @Override
    public void onBackPressed() {
        PulseFragment.DataThread.interrupted();
        super.onBackPressed();
    }*/
}

