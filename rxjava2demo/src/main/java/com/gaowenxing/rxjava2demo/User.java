package com.gaowenxing.rxjava2demo;

import com.google.gson.Gson;

/**
 * Created by lx on 2017/8/14.
 */

public class User {

    private String username;

    private String id;

    private String head;

    public User(String username, String id, String head) {
        this.username = username;
        this.id = id;
        this.head = head;
    }

    public User(String username, String id) {
        this.username = username;
        this.id = id;
    }

    public User() {
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
