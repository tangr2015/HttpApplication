package com.tangr.httputils.core;

import android.os.AsyncTask;

import com.tangr.httputils.Request;

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
        if (o instanceof Exception){
            request.iCallBack.onFailure((Exception) o);
        }else {
            request.iCallBack.onSuccess(o);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Object doInBackground(Void... params) {
        try {
            return request.iCallBack.parse(HttpConnection.excute(request));
        } catch (Exception e) {
            return e;
        }
    }
}
