package com.example.hansung.band_cctv.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.util.RtspViewPlayer;

public class VideoFragment extends Fragment {
    private static VideoFragment instance; 
    private RelativeLayout surfaceView;

    public static VideoFragment getInstance() {
        if (instance == null)
            instance = new VideoFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = (View) inflater.inflate(R.layout.fragment_video, container, false);

    /*    RtspViewPlayer playView = new RtspViewPlayer(getContext(),"rtsp://192.168.0.2:8091/rtsp");
        surfaceView = (RelativeLayout)view.findViewById(R.id.surface_video);
        surfaceView.addView(playView);*/

        return view;
    }

}
