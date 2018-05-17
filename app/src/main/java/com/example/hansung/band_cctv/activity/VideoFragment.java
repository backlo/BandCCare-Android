package com.example.hansung.band_cctv.activity;

import android.annotation.SuppressLint;
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
import com.example.hansung.band_cctv.Retrofit2.RetroClient2;
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

    Button left_btn1;
    Button right_btn1;

    Button left_btn2;
    Button right_btn2;

    RetroClient2 retroClient2;

    public static VideoFragment getInstance() {
        if (instance == null)
            instance = new VideoFragment();
        return instance;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video, container, false);

        left_btn1 = view.findViewById(R.id.left_btn1);
        right_btn1 = view.findViewById(R.id.right_btn1);

        left_btn2 = view.findViewById(R.id.left_btn2);

        retroClient2 = RetroClient2.getInstance().createBaseApi2();

        right = "right";
        left = "left";
        stop = "stop";

        parameter_left = new HashMap<>();
        parameter_right = new HashMap<>();
        parameter_stop = new HashMap<>();


        parameter_right.put("rsl",right);
        parameter_left.put("rsl",left);
        parameter_stop.put("rsl",stop);

        left_btn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        retroClient2.Motor_Controller(parameter_left, new RetroCallback() {
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
                        retroClient2.Motor_Controller(parameter_stop, new RetroCallback() {
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

        right_btn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        retroClient2.Motor_Controller(parameter_right, new RetroCallback() {
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
                        retroClient2.Motor_Controller(parameter_stop, new RetroCallback() {
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

        playView_first = new RtspViewPlayer(getContext(),"rtsp://192.168.0.2:8091/rtsp");
        surfaceView_first = (RelativeLayout)view.findViewById(R.id.surface_video1);
        surfaceView_first.addView(playView_first);

        playView_second = new RtspViewPlayer(getContext(),"rtsp://192.168.0.2:8091/rtsp1");
        surfaceView_second = (RelativeLayout)view.findViewById(R.id.surface_video2);
        surfaceView_second.addView(playView_second);

        return view;
    }
}

