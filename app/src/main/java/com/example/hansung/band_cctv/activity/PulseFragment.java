package com.example.hansung.band_cctv.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hansung.band_cctv.R;

public class PulseFragment extends Fragment {
    private static PulseFragment instance;

    public static PulseFragment getInstance() {
        if (instance == null)
            instance = new PulseFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = (View) inflater.inflate(R.layout.fragment_pulse, container, false);
        return rootView;
    }  

}
