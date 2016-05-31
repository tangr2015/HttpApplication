package com.tangr.httputils.callback;

import android.text.TextUtils;

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
    public T parse(HttpURLConnection con) throws Exception {
        int responseCode = con.getResponseCode();
        if(responseCode==HttpURLConnection.HTTP_OK){
            InputStream is = con.getInputStream();
            if(TextUtils.isEmpty(path)){
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
                int len;
                while ((len = is.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                is.close();
                out.flush();
                out.close();
                return bindData(path);
            }
        }
        return null;
    }

    protected abstract T bindData(String s) throws Exception;

    public ICallBack setCachePath(String path) {
        this.path = path;
        return this;
    }
}
