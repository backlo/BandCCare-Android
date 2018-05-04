package com.example.hansung.band_cctv.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.util.RtspViewPlayer;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout surfaceView;
    private RelativeLayout surfaceView1;
    private RtspViewPlayer playView;
    private RtspViewPlayer playView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playView = new RtspViewPlayer(getApplication(),"rtsp://192.168.0.2:8091/rtsp");

        surfaceView = (RelativeLayout) findViewById(R.id.surface_video);
        surfaceView.addView(playView);

        playView1 = new RtspViewPlayer(getApplication(),"rtsp://192.168.0.2:8091/rtsp1");

        surfaceView1 = (RelativeLayout) findViewById(R.id.surface_video1);
        surfaceView1.addView(playView1);

    }
}
