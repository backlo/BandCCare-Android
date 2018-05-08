package com.example.hansung.band_cctv.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.hansung.band_cctv.MyPagerAdapter;
import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.Retrofit.Model.Request_exit_PI;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit.RetroClient;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static MyHandler myHandler;

    RetroClient retroClient;

    public String exit;
    public String noexit;

    HashMap<String, Object> parameter;
    HashMap<String, Object> parameter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parameter = new HashMap<>();
        parameter2 = new HashMap<>();
        exit = "exit";
        noexit = "noexit";
        retroClient = RetroClient.getInstance().createBaseApi();
        parameter.put("exit",exit);
        parameter2.put("exit",noexit);

        retroClient.Exit_PI(parameter2, new RetroCallback() {
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

        myHandler = new MyHandler();
        MyPagerAdapter mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mPagerAdapter);

        TabLayout mTab = (TabLayout) findViewById(R.id.tabs);
        mTab.setupWithViewPager(mViewPager);

        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            mTab.getTabAt(i).setIcon(mPagerAdapter.getIcon(i));
        }
    }

    public class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("main ondestroy","destroy");
        retroClient.Exit_PI(parameter, new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Request_exit_PI data = (Request_exit_PI)receivedData;
                Log.e("exit data@@",data.getExit());
            }

            @Override
            public void onFailure(int code) {

            }
        });

    }
}

