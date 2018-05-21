package com.example.hansung.band_cctv;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.Toast;

import com.example.hansung.band_cctv.activity.BlankFragment;
import com.example.hansung.band_cctv.activity.LocationFragment;
import com.example.hansung.band_cctv.activity.MainActivity;
import com.example.hansung.band_cctv.activity.PulseFragment;
import com.example.hansung.band_cctv.activity.VideoFragment;
import com.example.hansung.band_cctv.login.LoginActivity;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private static int PAGE_NUM = 3;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return VideoFragment.getInstance();
                //return BlankFragment.getInstance();
            case 1:
                return PulseFragment.getInstance();
            case 2:
                return LocationFragment.getInstance();
            default:
                return null;
        }
    }


    public String getPageTitle(int position) {
        switch (position) {
            case 0:
                return "VIDEO";
            case 1:
                return "PULSE";
            case 2:
                return "LOCATION";
            default:
                return null;
        }
    }

    public int getIcon(int position) {
        switch (position) {
            case 0:
                return R.drawable.tab_video;
            case 1:
                return R.drawable.tab_pulse;
            case 2:
                return R.drawable.location;
            default:
                return 0;
        }
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
