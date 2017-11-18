package com.gaowenxing.rxjavaoperate;

/**
 * Created by lx on 2017/8/14.
 */

public class BaseResult {

    private String message;

    private int success;

    private int user_id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
