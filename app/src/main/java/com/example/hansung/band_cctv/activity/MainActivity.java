package com.example.hansung.band_cctv.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.util.RtspViewPlayer;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        RtspViewPlayer playView = new RtspViewPlayer(getApplication(),"rtsp://192.168.0.2:8091/rtsp");

        surfaceView = (RelativeLayout) findViewById(R.id.surface_video);
        surfaceView.addView(playView);

    }
}
