package com.tangr.httputils.core;

import android.util.Log;

import com.tangr.httputils.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by tangr on 2016/5/30.
 */
public class HttpConnection {
    public static HttpURLConnection excute(Request request) throws IOException {
        switch (request.method){
            case POST:
                return post(request);
            case PUT:
            case DELETE:
            case GET:
                return get(request);
        }
        return null;
    }

    private static HttpURLConnection get(Request request) throws IOException {
        HttpURLConnection httpURLConnection =  (HttpURLConnection) new URL(request.url).openConnection();
        httpURLConnection.setConnectTimeout(3000);
        httpURLConnection.setReadTimeout(3000);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestProperty("Charset", "UTF-8");
        httpURLConnection.setRequestMethod("GET");
        addHeader(httpURLConnection,request.headers);

        return httpURLConnection;
    }

    private static HttpURLConnection post(Request request) throws IOException {
        HttpURLConnection httpURLConnection =  (HttpURLConnection) new URL(request.url).openConnection();
        httpURLConnection.setConnectTimeout(3000);
        httpURLConnection.setReadTimeout(3000);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestProperty("Charset", "UTF-8");
        httpURLConnection.setRequestMethod("POST");
        addHeader(httpURLConnection, request.headers);

        OutputStream os = httpURLConnection.getOutputStream();
        os.write(request.content.getBytes());

        return httpURLConnection;
    }

    private static String convertStreamToString(InputStream is) throws IOException {
        byte[] b = new byte[2048];
        StringBuffer sb = new StringBuffer();
        int len = 0;
        while ((len = is.read(b))!=-1){
            sb.append(new String(b,0,len));
        }
        return sb.toString();
    }

    private static void addHeader(HttpURLConnection con,Map<String, String> headers){
        if (headers == null || headers.size() == 0)
            return;

        for(Map.Entry<String,String> entry : headers.entrySet()){
            con.addRequestProperty(entry.getKey(),entry.getValue());
        }
    }
}
