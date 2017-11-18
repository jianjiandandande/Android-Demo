package com.example.dagger2demo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vincent on 2017/11/17.
 */


@Module
public class UserModule {

    /**
     * 如果provided方法中需要进行传递参数，则有两种办法可以解决
     * 1.在此Module中再定义相应的provided方法，给它提供相应的参数
     * 2.在所需的参数的相应类中定义一个具有@Inject注解的构造方法。
     */
    @Provides
    public ApiService providApiService(){
        return new ApiService();
    }


}
