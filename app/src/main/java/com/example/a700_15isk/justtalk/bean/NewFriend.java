package com.example.a700_15isk.justtalk.bean;

import java.io.Serializable;

/**
 * Created by 700-15isk on 2017/8/21.
 */

public class NewFriend implements Serializable {
    private Long id;
    private String msg;
    private String name;
    private Integer status;
    private Long time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
