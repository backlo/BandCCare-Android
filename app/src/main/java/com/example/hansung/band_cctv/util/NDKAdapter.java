package com.example.hansung.band_cctv.util;

public class NDKAdapter {

    static {
        System.loadLibrary("VideoPlayer");
    }

    public static native void setDataSource(String uri);
    public static native int play(Object surface);
    public static native int stop(int k);

    public static native void setDataSource1(String uri);
    public static native int play1(Object surface);
    public static native int stop1(int k);

    public NDKAdapter(){

    }

}
