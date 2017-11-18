package com.example.dagger2demo;

import dagger.Component;

/**
 * Created by Vincent on 2017/11/17.
 */

@Component(modules = {UserModule.class})
public interface UserComponent {

    void inject(MainActivity activity);
}
