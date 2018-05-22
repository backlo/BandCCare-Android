package com.example.hansung.band_cctv.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hansung.band_cctv.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class AddDeviceFragment extends Fragment {


    private static AddDeviceFragment instance;
    public String qr_result_band;
    public String qr_result_camera;
    IntentResult result_band;
    public EditText editText_band;
    public EditText editText_camera;
    public String bandstring;
    public String camerastring;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_device, container, false);

        Button band_btn = view.findViewById(R.id.band_btn);
        Button camera_btn = view.findViewById(R.id.camera_btn);

        editText_band = (EditText)view.findViewById(R.id.bandId_et);
        editText_camera = (EditText)view.findViewById(R.id.cameraId_et);

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




        band_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(getActivity()).initiateScan();
            }
        });

        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(getActivity()).initiateScan();
            }
        });
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE
        //  = 0x0000c0de; // Only use bottom 16 bits
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (intentResult == null) {
                // 취소됨
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                // 스캔된 QRCode --> result.getContents()
                Toast.makeText(getActivity(), "Scanned: " + intentResult.getContents(), Toast.LENGTH_LONG).show();

                if(intentResult.getContents().equals("band_1")){
                    Toast.makeText(getActivity(), ""+intentResult.getContents(), Toast.LENGTH_SHORT).show();
                    qr_result_band = intentResult.getContents();
                }else{
                    Toast.makeText(getActivity(), ""+intentResult.getContents(), Toast.LENGTH_SHORT).show();
                    qr_result_camera = intentResult.getContents();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
