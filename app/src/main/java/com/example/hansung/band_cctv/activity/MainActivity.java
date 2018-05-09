package com.example.hansung.band_cctv.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.example.hansung.band_cctv.Retrofit.Model.Request_exit_PI;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit.RetroClient;
import com.example.hansung.band_cctv.login.LoginActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static MyHandler myHandler;
    private DrawerLayout mDrawerLayout;

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
        parameter.put("exit", exit);
        parameter2.put("exit", noexit);

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
                    case R.id.navigation_item1:
                        Toast.makeText(MainActivity.this, "App user info", Toast.LENGTH_LONG).show();
                        intent = new Intent(getApplicationContext(), InfoActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_item2:
                        Toast.makeText(MainActivity.this, "band user info", Toast.LENGTH_LONG).show();
                        intent = new Intent(getApplicationContext(), InfoActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_item3:
                        Toast.makeText(MainActivity.this, "device info", Toast.LENGTH_LONG).show();
                        intent = new Intent(getApplicationContext(), InfoActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_logout:
                       intent = new Intent(getApplicationContext(), LoginActivity.class);
                       startActivity(intent);
                        break;

                }

                return true;
            }
        });
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
        Log.e("main ondestroy", "destroy");
        retroClient.Exit_PI(parameter, new RetroCallback() {
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_119:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:119")));
                break;
            case R.id.action_call:
                String tel = "01074771488";
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-1234-1234")));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

