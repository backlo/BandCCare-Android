package com.example.hansung.band_cctv.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.hansung.band_cctv.R;


public class AddDeviceFragment extends Fragment {


    private static AddDeviceFragment instance;
    public String qr_result_band;
    public String qr_result_camera;
    public EditText editText_band;
    public EditText editText_camera;
    public String bandstring;
    public String camerastring;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_device, container, false);


        editText_band = view.findViewById(R.id.bandId_et);
        editText_camera = view.findViewById(R.id.cameraId_et);

        editText_band.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bandstring = editText_band.getText().toString();
            }
        });

        editText_camera.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                camerastring = editText_camera.getText().toString();
            }
        });


        return view;
    }


    public String getQr_result_band(){
        return qr_result_band;
    }

    public String getQr_result_camera(){
        return qr_result_camera;
    }

    public String getBandstring(){return bandstring;}
    public String getCamerastring(){return camerastring;}


}
