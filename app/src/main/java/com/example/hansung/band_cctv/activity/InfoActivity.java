package com.example.hansung.band_cctv.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.login.LoginActivity;

import org.w3c.dom.Text;

public class InfoActivity extends AppCompatActivity {

    int APPUSER = 1, BANDUSER = 2, DEVICE = 3;
    private DrawerLayout mDrawerLayout;

    LinearLayout id_layout;
    LinearLayout name_layout;
    LinearLayout phone_layout;
    LinearLayout birth_layout;
    LinearLayout device_layout;
    LinearLayout address_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        int intentValue = getIntent().getIntExtra("info", 0);

        TextView info_tv = (TextView) findViewById(R.id.info_tv);

        id_layout = (LinearLayout) findViewById(R.id.id_layout);
        name_layout = (LinearLayout) findViewById(R.id.name_layout);
        phone_layout = (LinearLayout) findViewById(R.id.phone_layout);
        birth_layout = (LinearLayout) findViewById(R.id.birth_layout);
        address_layout = (LinearLayout) findViewById(R.id.address_layout);
        device_layout = (LinearLayout) findViewById(R.id.device_layout);

        TextView id_tv = (TextView) findViewById(R.id.id_tv);
        TextView name_tv = (TextView) findViewById(R.id.name_tv);
        TextView phone_tv = (TextView) findViewById(R.id.phone_tv);
        TextView birth_tv = (TextView) findViewById(R.id.birth_tv);
        TextView band_tv = (TextView) findViewById(R.id.band_tv);
        TextView camera_tv = (TextView) findViewById(R.id.camera_tv);


        if (intentValue == APPUSER) {
            info_tv.setText("보호자 정보입니다.");
            setLayout(1, 0, 1, 1, 0, 0);
            id_tv.setText("db값");
            phone_tv.setText("db값");
            birth_tv.setText("db값");
        } else if (intentValue == BANDUSER) {
            info_tv.setText("사용자 정보입니다.");
            setLayout(1, 1, 1, 1,1, 0);
            id_tv.setText("db값");
            name_tv.setText("db값");
            phone_tv.setText("db값");
            birth_tv.setText("db값");
        } else if (intentValue == DEVICE) {
            info_tv.setText("기기 정보입니다.");
            setLayout(0, 0, 0, 0, 0, 1);
            band_tv.setText("db값");
            camera_tv.setText("db값");
        } else {
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
                        Toast.makeText(InfoActivity.this, "App user info", Toast.LENGTH_LONG).show();
                        intent = new Intent(getApplicationContext(), InfoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("info", 1);
                        startActivity(intent);
                        break;

                    case R.id.navigation_item2:
                        Toast.makeText(InfoActivity.this, "band user info", Toast.LENGTH_LONG).show();
                        intent = new Intent(getApplicationContext(), InfoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("info", 2);
                        startActivity(intent);
                        break;

                    case R.id.navigation_item3:
                        Toast.makeText(InfoActivity.this, "device info", Toast.LENGTH_LONG).show();
                        intent = new Intent(getApplicationContext(), InfoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("info", 3);
                        startActivity(intent);
                        break;

                    case R.id.navigation_logout:
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                }

                return true;
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

    public void setLayout(int id, int name, int phone, int birth, int address, int device) {
        if (id == 0) id_layout.setVisibility(View.GONE);
        else id_layout.setVisibility(View.VISIBLE);

        if (name == 0) name_layout.setVisibility(View.GONE);
        else name_layout.setVisibility(View.VISIBLE);

        if (phone == 0) phone_layout.setVisibility(View.GONE);
        else phone_layout.setVisibility(View.VISIBLE);

        if (birth == 0) birth_layout.setVisibility(View.GONE);
        else birth_layout.setVisibility(View.VISIBLE);
        if (address == 0) address_layout.setVisibility(View.GONE);
        else address_layout.setVisibility(View.VISIBLE);

        if (device == 0) device_layout.setVisibility(View.GONE);
        else device_layout.setVisibility(View.VISIBLE);

    }


}
