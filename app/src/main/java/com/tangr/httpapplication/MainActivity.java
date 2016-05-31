package com.tangr.httpapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tangr.httputils.callback.impl.StringCallback;
import com.tangr.httputils.Request;
import com.tangr.httputils.core.RequestTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
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
                    public void onFailure(Exception e) {

                    }
                });
                new RequestTask(request).execute();
//                        final String s = HttpConnection.excute(request);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                tv.setText(s);
//                            }
//                        });
            }
        });
    }
}
