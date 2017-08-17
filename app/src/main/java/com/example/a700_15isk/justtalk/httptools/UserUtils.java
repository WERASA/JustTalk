package com.example.a700_15isk.justtalk.httptools;


import android.util.Log;

import com.example.a700_15isk.justtalk.data.UserInfoBean;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 700-15isk on 2017/8/16.
 */

public class UserUtils {
    private static final String TAG = "UserUtils";
    private static final int DEFAULT_TIMEOUT = 5;
    public static String baseUrl = "https://api.netease.im/nimserver/";
    private Retrofit retrofit;
    private DataService dataService;


    public UserUtils() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String nonce = NonceUtils.getNonce(128);
                Request request = chain.request().newBuilder()
                        .addHeader("AppKey", MyAppData.APP_KEY)
                        .addHeader("Nonce", nonce)
                        .addHeader("CurTime", String.valueOf(MyAppData.CurTime))
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("CheckSum", CheckSumBuilder.getCheckSum("593fe115fe85", nonce, String.valueOf(MyAppData.CurTime)))
                        .build();
                Log.d(TAG, request.headers().toString());
                return chain.proceed(request);
            }
        });
        httpClient.addInterceptor(new HttpLoggingInterceptor());
        OkHttpClient okhttp = httpClient.build();
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okhttp)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        dataService = retrofit.create(DataService.class);

    }

    public static UserUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void setNewUser(Subscriber<UserInfoBean> subscriber, String accid) {
        Observable<UserInfoBean> observable = dataService.postLoginMessage(accid)
                .map(new HttpResultFunc());
        initObservable(observable, subscriber);

    }

    private <T> Subscription initObservable(Observable<T> o, Subscriber<T> s) {
        return o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    private static class SingletonHolder {
        private static final UserUtils INSTANCE = new UserUtils();
    }

    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getCode() != 200) {
                throw new ApiException(httpResult.getCode());

            }
            return httpResult.getName();
        }
    }
}
