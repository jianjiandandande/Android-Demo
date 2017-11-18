package com.example.dagger2demo;

/**
 * Created by Vincent on 2017/11/17.
 */

public class UserManager {

    private ApiService mApiService;

    private UserStore mUserStore;

    public UserManager(ApiService apiService, UserStore userStore) {
        this.mApiService = apiService;
        this.mUserStore = userStore;
    }

    public void register(){

        mApiService.register();
        mUserStore.register();
    }

}
