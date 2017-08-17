package com.example.a700_15isk.justtalk.httptools;

import android.util.Log;

/**
 * Created by 700-15isk on 2017/8/16.
 */

public class ApiException extends RuntimeException {
    public static final int NO_FOUND =404;


    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }


    private static String getApiExceptionMessage(int code){
        String message = "";
        switch (code) {
            case 301:
                message = "用户名或密码错误";
                break;
            case 503:
                message="服务器繁忙";
                break;
            case 414:
                message="参数错误";
                break;
            default:
                message = "未知错误";
        }
        Log.d( "getApiExceptionMessage: ",code+"");
        return message;
    }
}
