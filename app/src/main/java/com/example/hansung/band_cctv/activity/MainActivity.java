package com.example.hansung.band_cctv.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hansung.band_cctv.MyPagerAdapter;
import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Band_Info;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Info;
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
    public static boolean sv_state = true;
    public static boolean videoIsOn = false;
    public static MainActivity instance;

    private DrawerLayout mDrawerLayout;
    RetroClient2 retroClient2;
    RetroClient retroClient;
    Intent intent2;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    SharedPreferences sharedPreferencesForPulse;
    SharedPreferences.Editor editorForPulse;


    int bandUserBirth_forPulse;
    int bandUserSex_forPulse;

    public static int sendindex;

    public static ArrayList<Response_Info> infoArrayList;
    public static ArrayList<Response_Band_Info> bandInfoArrayList;

    public String exit;
    public String noexit;
    public MyPagerAdapter mPagerAdapter;
    public  ViewPager mViewPager;
    String id;

    HashMap<String, Object> parameter;
    HashMap<String, Object> parameter2;
    String noalarm = "noalarm";
    HashMap<String, Object> noalarmmap;
    PulseFragment pulseFragment= PulseFragment.getInstance();
    int MALE = 0, FEMALE = 1;
    int bandUserAge;

    public static MainActivity getInstance() {
        if (instance == null)
            instance = new MainActivity();
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sharedPreferencesForPulse= getSharedPreferences("pulse", MODE_PRIVATE);
        editorForPulse = sharedPreferencesForPulse.edit();

        noalarmmap = new HashMap<>();
        noalarmmap.put("alarm", noalarm);
        checkDalogAlert();
        retroClient2 = RetroClient2.getInstance().createBaseApi2();
        retroClient = RetroClient.getInstance().createBaseApi();
        intent2 = new Intent(getApplicationContext(), SV_Data.class);

        id = sharedPreferences.getString("id", "null");

        retroClient.GetInfo(id, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
            }
            @Override
            public void onSuccess(int code, Object receivedData) {
                infoArrayList = (ArrayList<Response_Info>)receivedData;
                sendindex = infoArrayList.get(0).getAppUserInfo_index();
                retroClient.GetInfo_Band(sendindex, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {
                        bandInfoArrayList = (ArrayList<Response_Band_Info>) receivedData;
                        Log.e("band user birth->", "" + bandInfoArrayList.get(0).getBandUserInfo_birth());
                        Log.e("band user sex->", "" + bandInfoArrayList.get(0).getBandUserInfo_sex());
                        String bandUserBirth = bandInfoArrayList.get(0).getBandUserInfo_birth().substring(0,4);
                        Log.e("bandUserBirth",""+bandUserBirth);
                        bandUserBirth_forPulse = Integer.parseInt(bandUserBirth);
                        Log.e("bandUserBirth_forPulse",""+bandUserBirth_forPulse);
                        String bandUserSex = bandInfoArrayList.get(0).getBandUserInfo_sex();
                        Log.e("bandUserSex",""+bandUserSex);
                        if(bandUserSex.equals("남성")) bandUserSex_forPulse = 0;
                        else bandUserSex_forPulse = 1;
                        Log.e("bandUserSex_forPulse",""+bandUserSex_forPulse);

                        bandUserAge = 2018 - bandUserBirth_forPulse + 1;
                        editorForPulse.putInt("age",bandUserAge);
                        editorForPulse.putInt("sex",bandUserSex_forPulse);
                        editorForPulse.commit();
                    }

                    @Override
                    public void onFailure(int code) {
                    }
                });
            }

            @Override
            public void onFailure(int code) {

            }
        });

        Log.e("main",""+id);
        retroClient.GetToken(id, new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                ArrayList<Response_Token> data = (ArrayList<Response_Token>) receivedData;
                //Log.e("get token", data.get(0).getUser_token());
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
                Log.e("alaram", "noalarm");
            }

            @Override
            public void onFailure(int code) {

            }
        });
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);

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
                    case R.id.navigation_myPage:
                        intent = new Intent(getApplicationContext(), InfoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        break;

                    case R.id.navigation_logout:
                        if(videoIsOn == true){
                            retroClient2.Exit_PI(parameter, new RetroCallback() {
                                @Override
                                public void onError(Throwable t) {

                                }

                                @Override
                                public void onSuccess(int code, Object receivedData) {
                                    Request_exit_PI data = (Request_exit_PI) receivedData;
                                    Log.e("Logout btn Data->", data.getExit());
                                }

                                @Override
                                public void onFailure(int code) {
                                }
                            });
                        }

                        if(!isServiceRunningCheck()) {
                        }
                        else{
                            stopService(intent2);
                        }
                        editor.putBoolean("autoLogin", false);
                        editorForPulse.clear();
                        editorForPulse.commit();

                        editor.clear();
                        editor.commit();
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
 /*
    public int getage(){
        return bandUserAge;
    }

    public int getsex(){
        return bandUserSex_forPulse;
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.e("zxc","start");
        super.onStart();
    }


    @Override
    protected void onRestart() {
        stopService(intent2);
        super.onRestart();
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

    @Override
    public void onBackPressed() {
        Log.e("okokok", "뒤로가기 눌름???");
        AlertDialog.Builder alert_ex = new AlertDialog.Builder(this);
        alert_ex.setMessage("정말로 종료하시겠습니까?");
        alert_ex.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert_ex.setPositiveButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(videoIsOn == true){
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


                if(!isServiceRunningCheck()) {
                    Log.e("okok","중복 아님요~");
                    startService(intent2);
                }
                else{
                    Log.e("okok","중복 임요~");
                }
                finishAffinity();
            }
        });
        alert_ex.setTitle("예제어플 알림!");
        AlertDialog alert = alert_ex.create();
        alert.show();
    }

    @Override
    protected void onUserLeaveHint() {
        /*pulseFragment.stopThread();
        locationFragment.stopThread();*/

        Log.e("okok","홈키 눌렀다!!");
        if(!isServiceRunningCheck()) {
            Log.e("okok","중복 아님요~");
            startService(intent2);
        }
        else{
            Log.e("okok","중복 임요~");
        }
        super.onUserLeaveHint();
    }

    public boolean isServiceRunningCheck() {
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.example.hansung.band_cctv.ServiceThread.SV_Data".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void checkDalogAlert(){

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_DENIED) {

            new android.app.AlertDialog.Builder(this).setTitle("알림").setMessage("권한을 허용해주셔야 앱을 이용할 수 있습니다.")
                    .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).setNegativeButton("권한 설정", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                    getApplicationContext().startActivity(intent);
                }
            }).setCancelable(false).show();

        } else {
        }
    }
}

