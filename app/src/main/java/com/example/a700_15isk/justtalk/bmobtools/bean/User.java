package com.example.a700_15isk.justtalk.bmobtools.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by 700-15isk on 2017/8/18.
 */

public class User extends BmobUser {
    private Boolean sex;
    private String nick;
    private Integer age;


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private String avatar;

    public void setSex(Boolean sex) {
        this.sex = sex;
    }


    public boolean getSex() {
        return this.sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
