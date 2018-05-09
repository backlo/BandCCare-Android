package com.example.hansung.band_cctv.activity;


import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit.RetroClient;
import com.example.hansung.band_cctv.util.RtspViewPlayer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

public class VideoFragment extends Fragment {
    private static VideoFragment instance;
    /* video */
    RetroClient retroClient;
    private RtspViewPlayer playView_first;
    private RtspViewPlayer playView_second;
    private RelativeLayout surfaceView_first;
    private RelativeLayout surfaceView_second;
    HashMap<String, Object> parameter_right;
    HashMap<String, Object> parameter_left;
    HashMap<String, Object> parameter_stop;
    public String right;
    public String left;
    public String stop;

    /* UI design */
    public ArrayList<String> parentList;
    public HashMap<String, String> childList;
    ExpandableListView expListView;
    com.example.hansung.band_cctv.ExpandableListAdapter listAdapter;

    public String uri1 = "rtsp://192.168.0.2:8091/rtsp";
    public String uri2 = "rtsp://192.168.0.2:8091/rtsp1";

    /* constructor(Singleton) */
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

        retroClient = RetroClient.getInstance().createBaseApi();
        right = "right";
        left = "left";
        stop = "stop";

        parameter_left = new HashMap<>();
        parameter_right = new HashMap<>();
        parameter_stop = new HashMap<>();

        parameter_right.put("rsl", right);
        parameter_left.put("rsl", left);
        parameter_stop.put("rsl", stop);

        /*left_btn.setOnTouchListener(new View.OnTouchListener() {
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
                        break;
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
                        break;

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

                        break;
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
                        break;

                }
                return false;
            }
        });

        playView_first = new RtspViewPlayer(getContext(), "rtsp://192.168.0.2:8091/rtsp");
        surfaceView_first = (RelativeLayout) view.findViewById(R.id.surface_video1);
        surfaceView_first.addView(playView_first);

        playView_second = new RtspViewPlayer(getContext(), "rtsp://192.168.0.2:8091/rtsp1");
        surfaceView_second = (RelativeLayout) view.findViewById(R.id.surface_video2);
        surfaceView_second.addView(playView_second);
*/
        /* UI design */

        expListView = (ExpandableListView) view.findViewById(R.id.elv);
        setListData();

        listAdapter = new com.example.hansung.band_cctv.ExpandableListAdapter(getContext(), parentList, childList);
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                 Toast.makeText(getContext(),
                 "Group Clicked " + parentList.get(groupPosition),
                 Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group 열렸을 때
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getContext(),
                        parentList.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group 닫혔을 때
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getContext(),
                        parentList.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getContext(),
                        parentList.get(groupPosition)
                                + " : "
                                + childList.get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

        return view;
    }


    private void setListData() {
        parentList = new ArrayList<String>();
        childList = new HashMap<String, String>();

        parentList.add("camera1");
        parentList.add("camera2");

        childList.put(parentList.get(0),uri1);
        childList.put(parentList.get(1),uri2);


    }

}