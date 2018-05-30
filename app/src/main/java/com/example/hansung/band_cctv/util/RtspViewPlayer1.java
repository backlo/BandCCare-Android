package com.example.hansung.band_cctv.util;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RtspViewPlayer1 extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "RtspPlayView";

    private SurfaceHolder mHolder;
    private NDKAdapter mPlayerNDKAdapter;
    private String uri;
    private static boolean state1 = true;

    public RtspViewPlayer1(Context context, String uri) {
        super(context);

        this.uri = uri;
        mHolder = getHolder();
        mHolder.addCallback(this);

        mPlayerNDKAdapter = new NDKAdapter();
        Log.d("datasource",uri+"");

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(state1 == true){
                    NDKAdapter.setDataSource1(uri);
                    Log.e("surface run1 : ", mHolder.getSurface()+"");
                    mPlayerNDKAdapter.play1(mHolder.getSurface());
                    Log.e("surface run2 : ", mHolder.getSurface()+"");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(state1 == false){
                    state1 = true;
                }
            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.e("surface destory1 : ", mHolder.getSurface()+"");
        mPlayerNDKAdapter.stop1(1);
        state1 = false;
        Log.e("surface destory2 : ", mHolder.getSurface()+"");
    }
}
