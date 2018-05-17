package com.example.hansung.band_cctv.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit.RetroClient;
import com.example.hansung.band_cctv.activity.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {
    RetroClient retroClient;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        retroClient = RetroClient.getInstance().createBaseApi();
        Map<String, String> pushDataMap = remoteMessage.getData();
        sendalaramdata();
        sendNotification(pushDataMap);
    }

    public void sendalaramdata(){
        HashMap<String, Object> alarmmap = new HashMap<>();
        alarmmap.put("alarm","alarm");
        retroClient.Send_Alarm(alarmmap, new RetroCallback() {
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

    }

    private void sendNotification(Map<String, String> dataMap) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this,"0")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(dataMap.get("title"))
                .setContentText(dataMap.get("msg"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(new long[]{1000, 1000})
                .setLights(Color.WHITE, 1500, 1500)
                .setContentIntent(contentIntent);

        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(0 /* ID of notification */, nBuilder.build());
    }
}
