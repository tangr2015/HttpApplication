package com.tangr.httputils.callback.impl;

import com.tangr.httputils.AppException;
import com.tangr.httputils.callback.AbstractCallback;

/**
 * Created by tangr on 16/5/31.
 */
public abstract class UploadFileCallback extends AbstractCallback<String>{
    @Override
    protected String bindData(String s) throws AppException {
        return s;
    }
}
