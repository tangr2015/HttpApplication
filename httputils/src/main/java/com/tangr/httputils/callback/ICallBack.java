package com.tangr.httputils.callback;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by tangr on 2016/5/31.
 */
public interface ICallBack<T> {
    void onSuccess(T result);
    void onFailure(Exception e);
    T parse(HttpURLConnection con) throws Exception;
}
