package com.gaowenxing.retrofitdemo;

/**
 * Created by lx on 2017/8/14.
 */

public class BaseResult {

    private String username;

    private int id;

    private int user_id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
