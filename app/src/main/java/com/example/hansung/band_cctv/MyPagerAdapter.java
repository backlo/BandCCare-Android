package com.example.hansung.band_cctv;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hansung.band_cctv.activity.InfoFragment;
import com.example.hansung.band_cctv.activity.PulseFragment;
import com.example.hansung.band_cctv.activity.VideoFragment;

public class MyPagerAdapter extends FragmentPagerAdapter{

    private static int PAGE_NUM = 3;

    public MyPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return VideoFragment.getInstance();
            case 1:
                return PulseFragment.getInstance();
            case 2:
                return InfoFragment.getInstance();
            default:
                return null;
        }
    }

    public String getPageTitle(int position){
        switch (position) {
            case 0:
                return "Video";
            case 1:
                return "Pulse";
            case 2:
                return "Info";
            default:
                return null;
        }
    }

    public int getIcon(int position){
        switch (position) {
            case 0:
                return R.drawable.tab_video;
            case 1:
                return R.drawable.tab_pulse;
            case 2:
                return R.drawable.tab_info;
            default:
                return 0;
        }
    }
    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
