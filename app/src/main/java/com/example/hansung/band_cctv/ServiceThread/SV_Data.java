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

    public SV_Data() {
        myHandler = new MyHandler();
        SV_Thread sv_thread = new SV_Thread(myHandler);
        sv_thread.setDaemon(true);
        sv_thread.start();
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
                Log.e("okokok","adfiuewhfkjasnc");
            }

        }
    }

}
