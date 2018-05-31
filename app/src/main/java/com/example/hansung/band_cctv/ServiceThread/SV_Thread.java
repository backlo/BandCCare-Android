package com.example.hansung.band_cctv.ServiceThread;

import android.os.Handler;

import com.example.hansung.band_cctv.Retrofit.Model.Response_lastPulse;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit.RetroClient;

import java.util.ArrayList;

public class SV_Thread extends Thread {

    Handler handler;

    RetroClient retroClient;
    int result;
    public boolean state1;


    public SV_Thread(Handler handler) {
        retroClient = RetroClient.getInstance().createBaseApi();

        state1 = true;

        this.handler = handler;
    }


    public int getData2() {
        retroClient.LastPulse(new RetroCallback() {
            ArrayList<Response_lastPulse> lastPulseArrayList;

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                lastPulseArrayList = (ArrayList<Response_lastPulse>) receivedData;
                result = lastPulseArrayList.get(0).getSensor_data();
            }

            @Override
            public void onFailure(int code) {

            }
        });
        return result;
    }


    @Override
    public void run() {
        while (state1 == true) {
            getData2();
            handler.sendEmptyMessage(5);
            try {
                Thread.sleep(4000);
            } catch (Exception e) {
            }
        }
    }
}
