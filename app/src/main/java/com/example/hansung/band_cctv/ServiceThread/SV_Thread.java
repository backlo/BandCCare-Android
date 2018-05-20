package com.example.hansung.band_cctv.ServiceThread;

import android.os.Handler;
import android.util.Log;

import com.example.hansung.band_cctv.Retrofit.Model.Response_MaxIndex;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Sensor;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit.RetroClient;
import com.example.hansung.band_cctv.Retrofit2.RetroClient2;

import java.util.HashMap;
import java.util.Map;

public class SV_Thread extends Thread{

    Handler handler;

    RetroClient retroClient;
    int maxindex;
    int startindex;
    int sensordata;
    int result;
    public boolean state1;

    RetroClient2 retroClient2;

    public SV_Thread(Handler handler) {
        retroClient = RetroClient.getInstance().createBaseApi();

        state1  = true;
        retroClient.GetMaxIndex(new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Response_MaxIndex data = (Response_MaxIndex) receivedData;
                maxindex = data.getMax();
                startindex = maxindex - 3;
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
        int count =0;
        while(state1 == true){
            Log.e("okokok", String.valueOf(getData()));
            retroClient2 = RetroClient2.getInstance().createBaseApi2();
            if (getData() > 150 || getData() < 40){
                count++;
                if(count == 5){
                    HashMap<String, Object> alarmmap = new HashMap<>();
                    alarmmap.put("alarm","alarm");
                    retroClient2.Send_Alarm(alarmmap, new RetroCallback() {
                        @Override
                        public void onError(Throwable t) {

                        }
                        @Override
                        public void onSuccess(int code, Object receivedData) {
                            Log.e("alarm","알람");
                        }

                        @Override
                        public void onFailure(int code) {
                        }
                    });
                    count++;
                }
            }

            handler.sendEmptyMessage(5);
            try {
                startindex+=2;
                Thread.sleep(4000);
            }catch (Exception e){
            }
        }
    }
}
