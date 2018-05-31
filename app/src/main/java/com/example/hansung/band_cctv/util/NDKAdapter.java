package com.example.hansung.band_cctv.util;

public class NDKAdapter {

    static {
        System.loadLibrary("VideoPlayer");
    }

    public static native void setDataSource(String uri);
    public static native int play(Object surface);
    public static native int stop(int k);

    public NDKAdapter(){

    }

}
