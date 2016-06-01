package com.tangr.httpapplication;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tangr.httputils.AppException;
import com.tangr.httputils.callback.impl.FileCallback;
import com.tangr.httputils.callback.impl.GsonCallback;
import com.tangr.httputils.callback.impl.StringCallback;
import com.tangr.httputils.Request;
import com.tangr.httputils.core.RequestTask;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.bt_string).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://api.stay4it.com/v1/public/core/?service=user.login";
                String content = "account=stay4it&password=123456";
                Request request = new Request(url, Request.RequestMethod.POST);
                request.content = content;
                request.setOnResponseListener(new StringCallback() {
                    @Override
                    public void onSuccess(String result) {
                        tv.setText(result);
                        Log.i("sss", result);
                    }

                    @Override
                    public void onFailure(AppException e) {

                    }
                });
                new RequestTask(request).execute();
            }
        });
        findViewById(R.id.bt_gson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://api.stay4it.com/v1/public/core/?service=user.login";
                String content = "account=stay4it&password=123456";
                Request request = new Request(url, Request.RequestMethod.POST);
                request.content = content;
                request.setOnResponseListener(new GsonCallback<User>() {
                    @Override
                    public void onSuccess(User result) {
                        tv.setText(result.toString());
                        Log.i("sss", result.toString());
                    }

                    @Override
                    public void onFailure(AppException e) {

                    }
                });
                new RequestTask(request).execute();
            }
        });
        findViewById(R.id.bt_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://api.stay4it.com/v1/public/core/?service=user.getAll";
                url += "&timestamp=" + System.currentTimeMillis() + "&count=10";
                Request request = new Request(url, Request.RequestMethod.GET);
                request.setOnResponseListener(new GsonCallback<ArrayList<Bean>>() {
                    @Override
                    public void onSuccess(ArrayList<Bean> result) {
                        for (Bean b:result){
                            Log.i("sss",b.toString());
                        }
                    }

                    @Override
                    public void onFailure(AppException e) {

                    }
                });
                new RequestTask(request).execute();
            }
        });
        findViewById(R.id.bt_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = "http://api.stay4it.com/uploads/test.jpg";
                String url = "http://ws.stream.qqmusic.qq.com/1530858.m4a?fromtag=46";
                Request request = new Request(url);
                final RequestTask task = new RequestTask(request);
                String path = Environment.getExternalStorageDirectory() + File.separator + "pm.m4a";
                request.setOnResponseListener(new FileCallback() {
                    @Override
                    public void onSuccess(String result) {
                        tv.setText(result);
                    }

                    @Override
                    public void onFailure(AppException e) {
                        Log.i("sss",e.toString());
                        tv.setText(e.toString());
                    }

                    @Override
                    public void onProgressUpdate(int current, int total) {
                        Log.i("sss","cur:"+current+",total:"+total);
                        tv.setText("cur:"+current+",total:"+total);
                        if(current * 100l / total > 50){
                            task.cancel();
                        }
                    }
                }.setCachePath(path));
                task.execute();
            }
        });
    }
}
