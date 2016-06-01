package com.tangr.httputils.core;

import android.util.Log;
import android.webkit.URLUtil;

import com.tangr.httputils.AppException;
import com.tangr.httputils.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by tangr on 2016/5/30.
 */
public class HttpConnection {
    public static HttpURLConnection excute(Request request) throws AppException {
        if(!URLUtil.isNetworkUrl(request.url)){
            throw new AppException(AppException.ErrorType.INVALID,request.url+" is invalid");
        }
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

    private static HttpURLConnection get(Request request) throws AppException {
        try {
            request.checkIfCancelled();

            HttpURLConnection httpURLConnection =  (HttpURLConnection) new URL(request.url).openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestMethod("GET");
            addHeader(httpURLConnection, request.headers);

            request.checkIfCancelled();

            return httpURLConnection;
        }catch (InterruptedIOException e){
            throw new AppException(AppException.ErrorType.TIMEOUT,e.getMessage());
        }catch (IOException e) {
            throw new AppException(AppException.ErrorType.IO,e.getMessage());
        }
    }

    private static HttpURLConnection post(Request request) throws AppException {
        try {
            request.checkIfCancelled();

            HttpURLConnection httpURLConnection =  (HttpURLConnection) new URL(request.url).openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestMethod("POST");
            addHeader(httpURLConnection, request.headers);

            request.checkIfCancelled();

            OutputStream os = httpURLConnection.getOutputStream();
            os.write(request.content.getBytes());

            request.checkIfCancelled();

            return httpURLConnection;
        }catch (InterruptedIOException e){
            throw new AppException(AppException.ErrorType.TIMEOUT,e.getMessage());
        }catch (IOException e) {
            throw new AppException(AppException.ErrorType.IO,e.getMessage());
        }
    }

    private static void addHeader(HttpURLConnection con,Map<String, String> headers){
        if (headers == null || headers.size() == 0)
            return;

        for(Map.Entry<String,String> entry : headers.entrySet()){
            con.addRequestProperty(entry.getKey(),entry.getValue());
        }
    }
}
