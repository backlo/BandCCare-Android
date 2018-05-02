package com.example.hansung.band_cctv.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.util.RtspViewPlayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class VideoFragment extends Fragment {
    private static VideoFragment instance;
    private RelativeLayout surfaceView;
    private static String position;

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

        left_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        position = "left";
                        new JSONTask().execute("http://192.168.0.6:3000/post");
                        break;
                    case MotionEvent.ACTION_UP:
                        position = "stop";
                        new JSONTask().execute("http://192.168.0.6:3000/post");
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
                        position = "right";
                        new JSONTask().execute("http://192.168.0.6:3000/post");
                        break;
                    case MotionEvent.ACTION_UP:
                        position = "stop";
                        new JSONTask().execute("http://192.168.0.6:3000/post");
                        break;

                }
                return false;
            }
        });
        RtspViewPlayer playView = new RtspViewPlayer(getContext(),"rtsp://192.168.0.2:8091/rtsp");
        surfaceView = (RelativeLayout)view.findViewById(R.id.surface_video);
        surfaceView.addView(playView);

        return view;
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                JSONObject jsonObject = new JSONObject();
                Log.e("rsl", position);
                jsonObject.accumulate("rsl", position);

                HttpURLConnection conn = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(urls[0]);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Cache-Control", "no-cache");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "text/html");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();

                    OutputStream outputStream = conn.getOutputStream();

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();

                    InputStream stream = conn.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    return buffer.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


    }
}
