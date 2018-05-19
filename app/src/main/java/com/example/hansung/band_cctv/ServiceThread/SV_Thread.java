package com.example.hansung.band_cctv.ServiceThread;

import android.os.Handler;
import android.util.Log;

import com.example.hansung.band_cctv.Retrofit.Model.Response_MaxIndex;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Sensor;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit.RetroClient;

public class SV_Thread extends Thread{

    Handler handler;

    RetroClient retroClient;
    int maxindex;
    int startindex;
    int sensordata;
    int result;

    public SV_Thread(Handler handler) {
        retroClient = RetroClient.getInstance().createBaseApi();


        retroClient.GetMaxIndex(new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Response_MaxIndex data = (Response_MaxIndex) receivedData;
                maxindex = data.getMax();
                startindex = maxindex - 30;
            }

            @Override
            public void onFailure(int code) {

            }
        });

        retroClient.GetSensor(startindex, new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Response_Sensor data = (Response_Sensor) receivedData;
                sensordata = data.getSensor_data();
            }

            @Override
            public void onFailure(int code) {

            }
        });
        this.handler = handler;
    }
    public int getData() {
        retroClient.GetSensor(startindex, new RetroCallback() {
            Response_Sensor data;

            @Override
            public void onError(Throwable t) {}

            @Override
            public void onSuccess(int code, Object receivedData) {
                data = (Response_Sensor) receivedData;
                result = data.getSensor_data();
            }
            @Override
            public void onFailure(int code) {}
        });
        return result;
    }

    @Override
    public void run() {
        while(true){
            Log.e("okokok", String.valueOf(getData()));
            handler.sendEmptyMessage(5);
            try {
                startindex++;
                Thread.sleep(2000);
            }catch (Exception e){
            }
        }
    }
}
