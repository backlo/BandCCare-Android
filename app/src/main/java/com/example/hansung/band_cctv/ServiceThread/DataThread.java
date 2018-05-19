/*
package com.example.hansung.band_cctv.ServiceThread;


import android.os.Handler;

public class DataThread extends Thread{

    public boolean state = true;
    public Handler handler;

    public DataThread(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run() {
        while(state == true){
            try{
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            handler.sendEmptyMessage(3);
        }
    }
}
*/
