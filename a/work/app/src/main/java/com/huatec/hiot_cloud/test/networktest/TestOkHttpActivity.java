package com.huatec.hiot_cloud.test.networktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.huatec.hiot_cloud.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp框架测试类
 */
public class TestOkHttpActivity extends AppCompatActivity {

//    private static final String basUrl = "http://14.215.177.39";
    private static final String basUrl = "http://114.67.88.191:8080";
    private static final String TAG = "TestOkHttpActivity";
    private String authorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ok_http);

        //execute方法测试
        Button btnExecute = findViewById(R.id.btn_okhttp_execute);
        btnExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testExecute();
            }
        });

        //enqueue方法测试
        Button btnEnqueue = findViewById(R.id.btn_okhttp_enqueue);
        btnEnqueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testEnqueue();
            }
        });

        //登录测试
        Button btnLogin = findViewById(R.id.btn_okhttp_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login("lct","abc123","app");
            }
        });


    //获取用户信息
        final Button btnGetUserInfo = findViewById(R.id.btn_okhttp_userinfo);
        btnGetUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInfo("4663591d0a154a6bbbbd9a7179096414_899153e7b8274ed29d1f92377d1903c9_use");
            }
        });

        //修改邮箱
        final Button btnUpdateEmail = findViewById(R.id.btn_okhttp_update_email);
        btnUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmail("4663591d0a154a6bbbbd9a7179096414_899153e7b8274ed29d1f92377d1903c9_use",
                "liuct@qq.com");
            }
        });
    }

    /**
     * 修改邮箱
     * @param authorization
     * @param email
     */
    private void updateEmail(String authorization, String email) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder().build();
        String url = basUrl + "/user/email?email=" + email;
        Request request = new Request.Builder().put(body).url(url)
                .header("Authorization", authorization).build();
        callEnqueue(okHttpClient, request);
    }

    /**
     *获取用户信息
     * @param authorization
     */
    private void getUserInfo(String authorization) {
        this.authorization = authorization;

        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder().build();
        String url = basUrl + "/user/one";
        Request request = new Request.Builder().get().url(url)
                .header("Authorization", authorization).build();
        callEnqueue(okHttpClient, request);
    }

    private void callEnqueue(OkHttpClient okHttpClient, Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "onFailure: " + e.getMessage(), e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    /**
     * 登录
     * @param userName
     * @param password
     * @param loginCode
     */
    private void login(String userName, String password, String loginCode) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder().build();
        String url = basUrl + String.format("/auth/login?username=%s&password=%s&loginCode=%s",
                userName, password, loginCode);
        Request request = new Request.Builder().post(body).url(url).build();
        callEnqueue(okHttpClient, request);
    }


    /**
     * 测试execute方式
     */
    private void testExecute() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(basUrl).build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    Log.d(TAG, "run: " + response.body().string());
                } catch (IOException e) {
                    Log.e(TAG, "testExecute: " + e.getMessage(), e);
                }
            }
        }.start();
    }

    /**
     * 测试enqueue方式
     */
    private void testEnqueue() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(basUrl).build();
        callEnqueue(okHttpClient, request);
    }

}
