package com.gaowenxing.okhttpdemo.okhttp;

/**
 * Created by lx on 2017/8/13.
 */


import java.util.ArrayList;
import java.util.List;

/**
 * 封装okhttp
 */
public class SimpleHttpClient {

    private SimpleHttpClient(){

    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public void equeue(BaseCallback callback){

    }

    public static class Builder{

        private String url;

        private String method;

        private List<RequestParam> mParams;

        private boolean isJson;

        private Builder(){
            method = "GET";
        }

        public SimpleHttpClient build(){
            return new SimpleHttpClient();
        }

        /**
         * get请求
         * @return
         */
        public Builder get(){

            method = "GET";
            return this;
        }

        /**
         * post请求
         * From 表单
         * @return
         */
        public Builder post(){

            method = "POST";
            return this;
        }

        /**
         * post请求
         * json参数
         * @return
         */
        public Builder json(){

            method = "POST";
            isJson = true;
            return post();
        }

        public Builder url(String url){

            this.url = url;
            return this;
        }

        public Builder addParam(String key,Object value){

            if (mParams==null){
                mParams = new ArrayList<>();
            }


            mParams.add(new RequestParam(key,value));
            return this;
        }

    }
}





























