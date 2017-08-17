package com.example.a700_15isk.justtalk.httptools;

/**
 * Created by 700-15isk on 2017/8/16.
 */

public class MyAppData {
    public static String APP_KEY = "14847fd2c007b1b3ce873d64d905c453";
    public static int Nonce = (int) Math.random() * (128 - 1 + 1);
    public static long CurTime = System.currentTimeMillis() / 1000;

}
