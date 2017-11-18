package com.example.aidldemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.aidldemo.module.Book;
import com.example.aidldemo.module.BookManager;

import java.util.ArrayList;
import java.util.List;


/**
 * 可以直接跨进程调用服务端的方法
 * 可以很好地处理并发的问题，尤其是在涉及多进程并发情况下的进程间通信。
 *
 * 注意：
 * 1.文件类型：用AIDL书写的文件的后缀是 .aidl，而不是 .java。
 * 2.数据类型：AIDL默认支持一些数据类型，在使用这些数据类型的时候是不需要导包的，但是除了这些类型之外的数据类型，
 * 在使用之前必须导包，就算目标文件与当前正在编写的 .aidl 文件在同一个包下
 * 3.定向tag
 * AIDL中的定向 tag 表示了在跨进程通信中数据的流向，其中 in 表示数据只能由客户端流向服务端， out 表示数据只能由
 * 服务端流向客户端，而 inout 则表示数据可在服务端与客户端之间双向流通。其中，数据流向是针对在客户端中的那个传入
 * 方法的对象而言的。in 为定向 tag 的话表现为服务端将会接收到一个那个对象的完整数据，但是客户端的那个对象不会因为
 * 服务端对传参的修改而发生变动；out 的话表现为服务端将会接收到那个对象的的空对象，但是在服务端对接收到的空对象有
 * 任何修改之后客户端将会同步变动；inout 为定向 tag 的情况下，服务端将会接收到客户端传来对象的完整信息，并且客户
 * 端将会同步服务端对该对象的任何变动.
 * 4.两种AIDL文件：一类是用来定义parcelable对象;一类是用来定义方法接口;
 */
public class AIDLService extends Service {
    private static final String TAG = "AIDLService";
    
    public AIDLService() {
    }

    //包含Book对象的list
    private List<Book> mBooks = new ArrayList<>();

    //由AIDL文件生成的BookManager(并作为后期的IBinder，在onBind方法中进行返回)
    private final BookManager.Stub mBookManager = new BookManager.Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            if(mBooks!=null) {
                return mBooks;
            }
            return new ArrayList<>();
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if (mBooks == null) {
                mBooks = new ArrayList<>();
            }

            if (book == null) {
                Log.e(TAG, "Book is null in In");
                book = new Book();
            }

            //尝试修改book的参数，主要是为了观察其到客户端的反馈
            book.setPrice(2333);
            if (!mBooks.contains(book)) {
                mBooks.add(book);
            }
            //打印mBooks列表，观察客户端传过来的值
            Log.e(TAG, "invoking addBooks() method , now the list is : " + mBooks.toString());
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Book book = new Book();
        book.setName("传感器");
        book.setPrice(25);
        mBooks.add(book);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(getClass().getSimpleName(), String.format("on bind,intent = %s", intent.toString()));
        return mBookManager;
    }
}
