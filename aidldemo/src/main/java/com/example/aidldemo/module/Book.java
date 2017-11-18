package com.example.aidldemo.module;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vincent on 2017/11/18.
 */

public class Book implements Parcelable{
    protected Book(Parcel in) {
        name = in.readString();
        price = in.readInt();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private String name;

    private int price;

    public Book() {}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(price);
    }

    public void readFromParcel(Parcel dest){

        this.name = dest.readString();
        this.price = dest.readInt();

    }

    @Override
    public String toString() {
        return "{ name:"+name+",price:"+price+"}";
    }
}
