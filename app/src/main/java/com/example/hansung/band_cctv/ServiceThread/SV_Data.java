package com.example.hansung.band_cctv.ServiceThread;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

public class SV_Data extends Service{

        MyHandler myHandler;
        SV_Thread sv_thread;


    public SV_Data() {
        Log.e("SV_Data","생성자 생성!!");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myHandler = new MyHandler();
        sv_thread = new SV_Thread(myHandler);
        sv_thread.setDaemon(true);
        sv_thread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {

            if(msg.what == 5){
                Log.e("SV_Data MyHandler","ok");
            }

        }
    }

    @Override
    public void onDestroy() {
        sv_thread.state1 = false;
        super.onDestroy();
    }
}
