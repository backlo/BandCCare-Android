package com.example.hansung.band_cctv.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.Retrofit.Model.Response_MaxIndex;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Sensor;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit.RetroClient;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PulseFragment extends Fragment {
    private static PulseFragment instance;

    public static final long ref = System.currentTimeMillis();
    SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
    long mNow;
    public static Date mDate;
    public Response_MaxIndex data;
    public static int maxIndex;
    public static int startIndex;

    RetroClient retroClient;

    int index =0;
    Double xindex = 1.0 ;
    LineChart lineChart;
    XAxis xAxis;
    List<Entry> entries;

    TextView dataview;
    TextView timeTextView;
    int result;
    String time_result;


    public static PulseFragment getInstance() {
        if (instance == null)
            instance = new PulseFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_pulse, container, false);

        retroClient = RetroClient.getInstance().createBaseApi();

        dataview = (TextView) view.findViewById(R.id.dataview);
        lineChart = (LineChart) view.findViewById(R.id.linechart);


        DataThread thread = new DataThread();
        thread.setDaemon(true);
        thread.start();

        retroClient.GetMaxIndex(new RetroCallback() {
            @Override
            public void onError(Throwable t) {
            }
            @Override
            public void onSuccess(int code, Object receivedData) {
                data = (Response_MaxIndex)receivedData;
                maxIndex = data.getMax();
                startIndex = maxIndex-10;
            }
            @Override
            public void onFailure(int code) {
            }
        });

        entries = new ArrayList<>();
        //entries.add(new Entry(0,0));

        LineDataSet lineDataSet = new LineDataSet(entries, "심박수");
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(4);
        lineDataSet.setCircleColor(Color.parseColor("#FFEC6253"));
        lineDataSet.setCircleColorHole(Color.WHITE);
        lineDataSet.setColor(Color.parseColor("#FFEF450C"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(true);
        lineDataSet.setDrawHighlightIndicators(true);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawFilled(true);

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        IAxisValueFormatter myformat = new HourAxisValueFormatter();

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setAvoidFirstLastClipping(true);
        //xAxis.setAxisMaximum(200);
        xAxis.setDrawAxisLine(true);
        xAxis.setValueFormatter(myformat);
        xAxis.setAxisMinimum(maxIndex);
        xAxis.enableGridDashedLine(15, 100, 5);

        LimitLine min = new LimitLine ( 50 , " 최소심박수" );
        LimitLine max = new LimitLine(170,"최대심박수");
        min.setLineColor(Color.RED);
        min.setTextColor(Color.RED);
        max.setLineColor(Color.RED);
        max.setTextColor(Color.RED);
        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setAxisMaximum(200);
        yLAxis.setTextColor(Color.BLACK);
        yLAxis.addLimitLine(min);
        yLAxis.addLimitLine(max);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("현재시간(분:초)");

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setAutoScaleMinMaxEnabled(true);
        lineChart.notifyDataSetChanged();
        lineChart.setDescription(description);
        lineChart.moveViewTo(index,getData(),YAxis.AxisDependency.LEFT);
        lineChart.setBackgroundColor(Color.parseColor("#FFBCB6B3"));
        lineChart.animateY(2000, Easing.EasingOption.EaseInElastic);
        lineChart.zoom((float) 1.5,1,0,0);


        return view;
    }

    public void StartgetData() {
        retroClient.GetSensor(startIndex, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
            }
            @Override
            public void onSuccess(int code, Object receivedData) {
                Response_Sensor data = (Response_Sensor) receivedData;
                Log.e("datas =", String.valueOf(data.getSensor_data()));
            }
            @Override
            public void onFailure(int code) {
            }
        });
    }
    public int getData() {
        retroClient.GetSensor(startIndex, new RetroCallback() {
            Response_Sensor data;
            @Override
            public void onError(Throwable t) {}
            @Override
            public void onSuccess(int code, Object receivedData) {
                data = (Response_Sensor) receivedData;
                result = data.getSensor_data();
            }
            @Override
            public void onFailure(int code) {}
        });
        return result;
    }


    public void chartUpdate(int x){
        entries.add(new Entry(x,getData()));
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
        xAxis.setAxisMaximum((float) (maxIndex+xindex));
        xAxis.setAxisMinimum(maxIndex-20);
        dataview.setText(String.valueOf(getData()));
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0 ){
                StartgetData();
                chartUpdate(startIndex);

                Log.e("Max INDEX@@@@", String.valueOf(startIndex));
                xindex++;
                index++;
                startIndex++;
            }
        }
    };
    class DataThread extends Thread{
        @Override
        public void run() {
            while(true){
                handler.sendEmptyMessage(0);
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }



    public String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return dateFormat.format(mDate);
    }

    public class HourAxisValueFormatter implements IAxisValueFormatter {

        private DateFormat mDataFormat;
        public Date mDate;

        public HourAxisValueFormatter(){
            this.mDataFormat = new SimpleDateFormat("mm:ss", Locale.KOREAN);
            //this.mDataFormat = DateFormat.getTimeInstance();
            this.mDate = new Date();
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // convertedTimestamp = originalTimestamp - referenceTimestamp
            long originalTimestamp = ref + (long)value * 1000;
            mDate.setTime(originalTimestamp);
            // Retrieve original timestamp
            // Convert timestamp to hour:minute
            return mDataFormat.format(mDate);
        }
    }

}
