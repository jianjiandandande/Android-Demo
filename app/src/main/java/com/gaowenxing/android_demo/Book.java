package com.gaowenxing.android_demo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vincent on 2017/10/27.
 */

public class Book implements Parcelable{

    private String name;

    private int id;

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book(String name, int id) {
        this.name = name;
        this.id = id;
    }

    protected Book(Parcel in) {
        name = in.readString();
        id = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
    }

    /**
     * 参数是一个Parcel,用它来存储与传输数据
     * @param dest
     */
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        name = dest.readString();
        id = dest.readInt();
    }

    @Override
    public String toString() {
        return name+","+id;
    }
}
