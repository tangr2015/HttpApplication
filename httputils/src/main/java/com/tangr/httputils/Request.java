package com.tangr.httputils;

import com.tangr.httputils.callback.ICallBack;

import java.util.Map;

/**
 * Created by tangr on 2016/5/31.
 */
public class Request {
    public enum RequestMethod {POST,GET,PUT,DELETE}

    public String url;
    public String content;
    public Map<String,String> headers;
    public RequestMethod method;
    public ICallBack iCallBack;

    public Request(String url){
        this.url = url;
        this.method = RequestMethod.GET;
    }

    public Request(String url,RequestMethod method){
        this.url = url;
        this.method = method;
    }

    public void setOnResponseListener(ICallBack iCallBack){
        this.iCallBack = iCallBack;
    }
}
