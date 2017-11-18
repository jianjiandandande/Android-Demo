package com.example.vincent.ndkdemo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Date;
import java.util.IllegalFormatException;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
        init();
    }

    private String name="test";
    private int[] source={1,0,5,2,4,3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(getMessage());
//数组访问
        getArray(source);

        for (int i : source) {
            Log.d("TAG", "onCreate: source"+i);
        }


/*异常处理  c++的异常，java无法try-catch,如果jni层面出现了异常，那么java的代码也终止
        try{
            exception();
        }catch (Exception e){
            Log.d("TAG", "onCreate: "+e.getMessage());
        }
        Log.d("TAG", "onCreate: -0-----------------");

*/
    //缓存策略
        for(int i=0;i<10;i++){
            cachede();
        }

    }

    public String getName(){
        return "1234";
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native String updateNameFromc();//属性访问
    public native String getMessage();//根据此方法，返回getName中返回的内容(方法访问)
    public native void getArray(int[] arrays);//获取数组
    //引用-->作用：通知JVM GC来回收JNI中的对象
    public native void getLocalReference();
    public native void exception();

    //缓存策略 --对象的生命周期
    public native void cachede();
    public static native void init();

}
