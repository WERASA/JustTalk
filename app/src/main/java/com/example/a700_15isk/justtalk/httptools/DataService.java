package com.example.a700_15isk.justtalk.httptools;

import com.example.a700_15isk.justtalk.data.UserInfoBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 700-15isk on 2017/8/16.
 */

public interface DataService<T> {
    @FormUrlEncoded
    @POST("user/create.action")
    Observable<HttpResult<UserInfoBean>> postLoginMessage(@Field("accid") String dataName);

}
