package com.example.hansung.band_cctv.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hansung.band_cctv.R;

public class AddBandUserFragment extends Fragment {

    private static AddAppUserFragment instance;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_band_user, container, false);
    }

}
