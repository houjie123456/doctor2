package com.company.linquan.app.http;

import android.content.Context;
import android.util.Log;


import com.company.linquan.app.base.MyBaseApplication;
import com.company.linquan.app.config.C;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YC on 2017/7/11.
 */

public abstract class BaseApi {
    public static final String API_SERVER = C.Url_IP;
    private static final OkHttpClient.Builder mOkHttpClientBuild = new OkHttpClient().newBuilder();
    private static Retrofit mRetrofit;

    protected static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            Context context = MyBaseApplication.getContext();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    //打印retrofit日志
                    Log.i("payBack","payBack = "+message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置log
            mOkHttpClientBuild.addInterceptor(loggingInterceptor);
            //设定30秒超时
            mOkHttpClientBuild.connectTimeout(30, TimeUnit.SECONDS);
            //设置缓存目录
            File cacheDirectory = new File(context.getCacheDir().getAbsolutePath(), "HttpCache");
            Cache cache = new Cache(cacheDirectory, 20 * 1024 * 1024);
            mOkHttpClientBuild.cache(cache);
            //构建Retrofit
            mRetrofit = new Retrofit.Builder()
                    //配置服务器路径
                    .baseUrl(API_SERVER)
                    //配置转化库，默认是Gson
                    .addConverterFactory(GsonConverterFactory.create())
                    //配置回调库，采用RxJava
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    //设置OKHttpClient为网络客户端
                    .client(mOkHttpClientBuild.build())
                    .build();
        }
        return mRetrofit;
    }
}
