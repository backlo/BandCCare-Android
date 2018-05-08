package com.example.hansung.band_cctv.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit.RetroClient;
import com.example.hansung.band_cctv.util.RtspViewPlayer;

import java.util.HashMap;

public class VideoFragment extends Fragment {
    private static VideoFragment instance;
    private RtspViewPlayer playView_first;
    private RtspViewPlayer playView_second;
    private RelativeLayout surfaceView_first;
    private RelativeLayout surfaceView_second;

    public String right;
    public String left;
    public String stop;
    HashMap<String, Object> parameter_right;
    HashMap<String, Object> parameter_left;
    HashMap<String, Object> parameter_stop;

    RetroClient retroClient;

    public static VideoFragment getInstance() {
        if (instance == null)
            instance = new VideoFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = (View) inflater.inflate(R.layout.fragment_video, container, false);
        Button left_btn = (Button) view.findViewById(R.id.left_btn);
        Button right_btn = (Button) view.findViewById(R.id.right_btn);

        right = "right";
        left = "left";
        stop = "stop";

        parameter_left = new HashMap<>();
        parameter_right = new HashMap<>();
        parameter_stop = new HashMap<>();
        retroClient = RetroClient.getInstance().createBaseApi();

        parameter_right.put("rsl",right);
        parameter_left.put("rsl",left);
        parameter_stop.put("rsl",stop);

        left_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        retroClient.Motor_Controller(parameter_left, new RetroCallback() {
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
                    case MotionEvent.ACTION_UP:
                        retroClient.Motor_Controller(parameter_stop, new RetroCallback() {
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


                }
                return false;
            }
        });

        right_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        retroClient.Motor_Controller(parameter_right, new RetroCallback() {
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
                    case MotionEvent.ACTION_UP:
                        retroClient.Motor_Controller(parameter_stop, new RetroCallback() {
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

                }
                return false;
            }
        });

//        playView_first = new RtspViewPlayer(getContext(),"rtsp://192.168.0.2:8091/rtsp");
//        surfaceView_first = (RelativeLayout)view.findViewById(R.id.surface_video1);
//        surfaceView_first.addView(playView_first);
//
//        playView_second = new RtspViewPlayer(getContext(),"rtsp://192.168.0.2:8091/rtsp1");
//        surfaceView_second = (RelativeLayout)view.findViewById(R.id.surface_video2);
//        surfaceView_second.addView(playView_second);
//
        return view;
    }
}

