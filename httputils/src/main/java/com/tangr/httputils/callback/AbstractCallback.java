package com.tangr.httputils.callback;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by tangr on 2016/5/31.
 */
public abstract class AbstractCallback<T> implements ICallBack<T>{
    public T parse(HttpURLConnection con) throws Exception {
        int responseCode = con.getResponseCode();
        if(responseCode==HttpURLConnection.HTTP_OK){
            InputStream is = con.getInputStream();
            byte[] b = new byte[2048];
            StringBuffer sb = new StringBuffer();
            int len = 0;
            while ((len = is.read(b))!=-1){
                sb.append(new String(b,0,len));
            }
            is.close();
            return bindData(sb.toString().trim());
        }
        return null;
    }

    protected abstract T bindData(String s);
}
