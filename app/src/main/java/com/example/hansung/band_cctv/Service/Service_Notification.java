package com.example.hansung.band_cctv.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.Thread.ServiceThread;
import com.example.hansung.band_cctv.activity.MainActivity;


public class Service_Notification extends Service {

    NotificationManager notificationManager;
    ServiceThread thread;
    myServiceHandler myServiceHandler;
    Notification notification;
    Intent intent;
    PendingIntent pendingIntent;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler = new myServiceHandler();
        thread = new ServiceThread(myServiceHandler);
        thread.start();
        return START_STICKY;
    }

    public void onDestroy() {
        thread.stopForever();
        thread = null;
    }

    class myServiceHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) { //불안정한 수치!!!
                Log.e("okokok", "위험해요 ㅠㅠ");
                intent = new Intent(getApplicationContext(), MainActivity.class);
                pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                notification = new Notification.Builder(getApplicationContext())
                        .setContentTitle("Content Title")
                        .setContentText("Content Text")
                        .setSmallIcon(R.drawable.noti_icon)
                        .setTicker("알림!!!")
                        .setContentIntent(pendingIntent)
                        .build();
                //소리추가
                notification.defaults = Notification.DEFAULT_SOUND;
                //알림 소리를 한번만 내도록

                notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
                //확인하면 자동으로 알림이 제거 되도록
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(777, notification);
                //토스트 띄우기
                Toast.makeText(getApplicationContext(), "뜸?", Toast.LENGTH_LONG).show();
            } else if (msg.what == 1) { //안전한 수치!!!
                Log.e("okokok", "안전해요");
            }
        }
    }
}