package com.example.a700_15isk.justtalk.data;

import com.google.gson.Gson;

/**
 * Created by 700-15isk on 2017/8/16.
 */

public class UserInfoBean {

    /**
     * token : db62968de4c995ed426595b907371039
     * accid : 56544654
     * name :
     */

    private String token;
    private String accid;
    private String name;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
