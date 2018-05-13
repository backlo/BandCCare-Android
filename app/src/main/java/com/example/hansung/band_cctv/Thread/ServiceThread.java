package com.example.hansung.band_cctv.Thread;

import android.os.Handler;
import android.util.Log;

import com.example.hansung.band_cctv.Retrofit.Model.Response_MaxIndex;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Sensor;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit.RetroClient;

import java.util.HashMap;


public class ServiceThread extends Thread {
    Handler handler;
    boolean isRun = true;
    RetroClient retroClient;
    int index = 7;
    int result;
    int sum = 0;
    int count = 0;
    int startindex;
    String alarm = "alarm";
    HashMap<String, Object> alarmmap;

    public ServiceThread(Handler handler) {
        alarmmap = new HashMap<>();
        alarmmap.put("alarm",alarm);
        retroClient = RetroClient.getInstance().createBaseApi();
        retroClient.GetMaxIndex(new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Response_MaxIndex data = (Response_MaxIndex)receivedData;
                startindex = data.getMax();
            }

            @Override
            public void onFailure(int code) {

            }
        });
        this.handler = handler;
    }

    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
            notify();
        }
    }

    public int getData() {
        retroClient.GetSensor(startindex, new RetroCallback() {
            Response_Sensor data;

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                data = (Response_Sensor) receivedData;
                result = data.getSensor_data();
            }

            @Override
            public void onFailure(int code) {
            }
        });
        return result;
    }

    public void run() {
        //반복적으로 수행할 작업을 한다.
        while (isRun) {
            Log.e("okokok", String.valueOf(getData()));
            sum += getData();
            count++;
            if (count == 5) {
                int a = sum / count;
                Log.e("okokok", String.valueOf(a));
                if (a > 150 || a < 70) {
                    Log.e("okokok", "나여기들어옴 ㅋ");
                    handler.sendEmptyMessage(0);
                    retroClient.Send_Alarm(alarmmap, new RetroCallback() {
                        @Override
                        public void onError(Throwable t) {

                        }

                        @Override
                        public void onSuccess(int code, Object receivedData) {

                        }

                        @Override
                        public void onFailure(int code) {

                        }
                    });
                } else {
                    handler.sendEmptyMessage(1);
                }
                count = 0;
                sum = 0;
            } else {
                handler.sendEmptyMessage(1);
            }
            startindex++;
            try {
                Thread.sleep(3000); //10초씩 쉰다.
            } catch (Exception e) {
            }
        }
    }
}
