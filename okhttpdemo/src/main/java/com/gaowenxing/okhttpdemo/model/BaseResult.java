package com.gaowenxing.okhttpdemo.model;

/**
 * Created by lx on 2017/8/13.
 */

public class BaseResult {

    private int success;

    private String message;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
