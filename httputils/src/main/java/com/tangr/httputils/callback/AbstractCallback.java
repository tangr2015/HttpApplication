package com.tangr.httputils.callback;

import android.text.TextUtils;

import com.tangr.httputils.AppException;
import com.tangr.httputils.itf.onProgressUpdateListener;

import org.json.JSONException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by tangr on 2016/5/31.
 */
public abstract class AbstractCallback<T> implements ICallBack<T>{
    private String path;
    private volatile boolean isCancelled;

    public T parse(HttpURLConnection con) throws AppException {
        return parse(con,null);
    }

    @Override
    public T parse(HttpURLConnection con, onProgressUpdateListener listener) throws AppException {
        try {
            checkIfCancelled();

            int responseCode = con.getResponseCode();
            if(responseCode==HttpURLConnection.HTTP_OK){
                InputStream is = con.getInputStream();
                if(TextUtils.isEmpty(path)){
                    checkIfCancelled();
                    byte[] b = new byte[2048];
                    StringBuffer sb = new StringBuffer();
                    int len = 0;
                    while ((len = is.read(b))!=-1){
                        sb.append(new String(b,0,len));
                    }
                    is.close();
                    return bindData(sb.toString().trim());
                }else {
                    FileOutputStream out = new FileOutputStream(path);
                    byte[] buffer = new byte[2048];
                    int totalLen = con.getContentLength();
                    int curLen = 0;
                    int len;
                    int percent = 0;
                    while ((len = is.read(buffer)) != -1) {
                        checkIfCancelled();
                        out.write(buffer, 0, len);
                        curLen += len;
                        if (listener != null) {
                            if (curLen * 100l / totalLen > percent) {
                                listener.onProgressUpdate(curLen, totalLen);
                            }
                        }
                    }
                    is.close();
                    out.flush();
                    out.close();
                    return bindData(path);
                }
            }else {
                throw new AppException(responseCode,con.getResponseMessage());
            }
        } catch (IOException e) {
            throw new AppException(AppException.ErrorType.IO,e.getMessage());
        }
    }

    protected abstract T bindData(String s) throws AppException;

    public ICallBack setCachePath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public void onProgressUpdate(int current, int total) {

    }

    @Override
    public void cancel() {
        isCancelled = true;
    }

    protected void checkIfCancelled() throws AppException {
        if (isCancelled){
            throw new AppException(AppException.ErrorType.CANCEL,"the request has been cancelled");
        }
    }
}
