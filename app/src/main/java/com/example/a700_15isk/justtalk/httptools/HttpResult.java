package com.example.a700_15isk.justtalk.httptools;

/**
 * Created by 700-15isk on 2017/8/16.
 */

public class HttpResult<T> {
    /**
     * code : 200
     * info : {"to  ken":"12e01e181e093920b5f55a9cd914c6f3","accid":"17723557342","name":""}
     */

    private int code;

    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }

    private InfoBean info;
    private T name;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
    }
}
