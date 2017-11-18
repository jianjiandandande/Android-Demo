package com.gaowenxing.retrofitdemo;

/**
 * Created by lx on 2017/8/14.
 */

public class UserParam {

    private String username;

    private String password;

    public UserParam(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
