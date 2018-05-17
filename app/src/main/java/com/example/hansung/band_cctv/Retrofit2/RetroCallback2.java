package com.example.hansung.band_cctv.Retrofit2;

public interface RetroCallback2<T> {
    void onError(Throwable t);

    void onSuccess(int code, T receivedData);

    void onFailure(int code);
}
