package com.example.hansung.band_cctv.util;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RtspViewPlayer extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "RtspPlayView";

    private SurfaceHolder mHolder;
    private NDKAdapter mPlayerNDKAdapter;
    private String uri;
    private static int state;

    public RtspViewPlayer(Context context, String uri) {
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
                    NDKAdapter.setDataSource(uri);
                    Log.e("surface run1 : ", mHolder.getSurface()+"");
                    mPlayerNDKAdapter.play(mHolder.getSurface());
                    Log.e("surface run2 : ", mHolder.getSurface()+"");
                    state = 0;
            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.e("surface destory1 : ", mHolder.getSurface()+"");
        if(state == 0){
            mPlayerNDKAdapter.stop(0);
        }else{
            mPlayerNDKAdapter.stop(1);
        }
        Log.e("surface destory2 : ", mHolder.getSurface()+"");
    }
}
