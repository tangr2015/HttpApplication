package com.tangr.httputils.core;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;

import com.tangr.httputils.AppException;
import com.tangr.httputils.Request;
import com.tangr.httputils.callback.impl.FileCallback;
import com.tangr.httputils.callback.impl.UploadFileCallback;
import com.tangr.httputils.itf.onProgressUpdateListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by tangr on 2016/6/12.
 */
public class RequestExecutor {
    private static final int CPU_COUNT = Runtime.getRuntime()
            .availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;
    private static RequestExecutor requestExecutor = null;

    private static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
            KEEP_ALIVE, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                MessageObj mo = (MessageObj) msg.obj;
                mo.request.iCallBack.onFailure((AppException) mo.obj);
            }
            else if(msg.what==1){
                MessageObj mo = (MessageObj) msg.obj;
                mo.request.iCallBack.onSuccess(mo.obj);
            }
            else if(msg.what==2){
                ((Request)msg.obj).iCallBack.onProgressUpdate(msg.arg1,msg.arg2);
            }
        }
    };

    private RequestExecutor(){
    }

    public static RequestExecutor getInstance(){
        if(requestExecutor==null){
            return new RequestExecutor();
        }
        return requestExecutor;
    }

    public void execute(final Request request){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Object o = request(0,request);
                Message ms = new Message();
                if (o instanceof AppException){
                    ms.what = 0;
                }else {
                    ms.what = 1;
                }
                MessageObj mo = new MessageObj();
                mo.request = request;
                mo.obj = o;
                ms.obj = mo;
                mMainHandler.sendMessage(ms);
            }
        };
        THREAD_POOL_EXECUTOR.execute(runnable);
    }

    private Object request(int retry, final Request request) {
        try {
            if(request.iCallBack instanceof FileCallback){
                return request.iCallBack.parse(HttpConnection.excute(request), new onProgressUpdateListener() {
                    @Override
                    public void onProgressUpdate(int current, int total) {
                        Message ms = new Message();
                        ms.what = 2;
                        ms.obj = request;
                        ms.arg1 = current;
                        ms.arg2 = total;
                        mMainHandler.sendMessage(ms);
                    }
                });
            }else if(request.iCallBack instanceof UploadFileCallback){
                UploadUtil.setOnUpdateListener(new onProgressUpdateListener() {
                    @Override
                    public void onProgressUpdate(int current, int total) {
                        Message ms = new Message();
                        ms.what = 2;
                        ms.obj = request;
                        ms.arg1 = current;
                        ms.arg2 = total;
                        mMainHandler.sendMessage(ms);
                    }
                });
                return request.iCallBack.parse(HttpConnection.excute(request));
            }else {
                return request.iCallBack.parse(HttpConnection.excute(request));
            }
        } catch (AppException e) {
            if (e.type == AppException.ErrorType.TIMEOUT) {
                if (retry < request.maxRetryCount) {
                    retry++;
                    return request(retry,request);
                }
            }
            return e;
        }
    }
}
