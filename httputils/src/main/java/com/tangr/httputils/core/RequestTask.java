package com.tangr.httputils.core;

import android.os.AsyncTask;

import com.tangr.httputils.AppException;
import com.tangr.httputils.Request;
import com.tangr.httputils.itf.onProgressUpdateListener;

import java.io.IOException;

/**
 * Created by tangr on 2016/5/31.
 */
public class RequestTask extends AsyncTask<Void,Integer,Object>{
    private Request request;

    public RequestTask(Request request) {
        this.request = request;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (o instanceof AppException){
            request.iCallBack.onFailure((AppException) o);
        }else {
            request.iCallBack.onSuccess(o);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        request.iCallBack.onProgressUpdate(values[0], values[1]);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Object doInBackground(Void... params) {
        return request(0);
    }

    private Object request(int retry) {
        try {
            if(request.enableProgress){
                return request.iCallBack.parse(HttpConnection.excute(request), new onProgressUpdateListener() {
                    @Override
                    public void onProgressUpdate(int current, int total) {
                        publishProgress(current,total);
                    }
                });
            }else {
                return request.iCallBack.parse(HttpConnection.excute(request));
            }
        } catch (AppException e) {
            if (e.type == AppException.ErrorType.TIMEOUT) {
                if (retry < request.maxRetryCount) {
                    retry++;
                    return request(retry);
                }
            }
            return e;
        }
    }

    public void cancel() {
        request.cancel();
    }
}
