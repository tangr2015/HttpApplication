package com.tangr.httputils.callback.impl;

import com.tangr.httputils.callback.AbstractCallback;

import java.net.HttpURLConnection;

/**
 * Created by tangr on 2016/5/31.
 */
public abstract class StringCallback extends AbstractCallback<String>{
    @Override
    protected String bindData(String s) {
        return s;
    }
}
