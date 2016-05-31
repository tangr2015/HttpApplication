package com.tangr.httputils.callback.impl;

import com.google.gson.Gson;
import com.tangr.httputils.callback.AbstractCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by tangr on 16/5/31.
 */
public abstract class GsonCallback<T> extends AbstractCallback<T>{
    @Override
    protected T bindData(String s) throws Exception {
        try {
            JSONObject json = new JSONObject(s);
            Object data = json.opt("data");
            Gson gson = new Gson();
            Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            return gson.fromJson(data.toString(), type);
        } catch (JSONException e) {
            throw new JSONException("JSONException");
        }
    }
}
