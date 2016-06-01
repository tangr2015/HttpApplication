package com.tangr.httputils.callback;

import com.tangr.httputils.AppException;
import com.tangr.httputils.itf.onProgressUpdateListener;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by tangr on 2016/5/31.
 */
public interface ICallBack<T> {
    void onSuccess(T result);
    void onFailure(AppException e);
    T parse(HttpURLConnection con) throws AppException;
    T parse(HttpURLConnection con,onProgressUpdateListener listener) throws AppException;
    void onProgressUpdate(int current,int total);

    void cancel();
}
