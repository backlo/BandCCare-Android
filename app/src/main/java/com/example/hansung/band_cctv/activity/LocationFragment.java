package com.example.hansung.band_cctv.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hansung.band_cctv.R;

public class LocationFragment extends Fragment {
    private static LocationFragment instance;

    public static LocationFragment getInstance() {
        if (instance == null)
            instance = new LocationFragment();
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        return rootView;
    }

}
