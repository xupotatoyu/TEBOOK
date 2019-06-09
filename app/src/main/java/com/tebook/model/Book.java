package com.tebook.model;

import com.tebook.R;

import java.util.Random;

public class Book {
    private int _id; //书籍编号，数据库自增字段
    private String name; //书籍名称，必填
    private String address; //书籍地址，必填
    private int cover;//书籍封面，必填,这里的int是指R文件中的图片的对应id

    public Book() {

    }
    public Book(String name, String address, int cover) {
        this.name = name;
        this.address = address;
        this.cover = cover;
    }

    /**获取书籍编号*/
    public int getId() {
        return _id;
    }
    /**获取书籍名称*/
    public String getName() {
        return name;
    }
    /**获取书籍地址*/
    public String getAddress() {
        return address;
    }
    /**获取封面*/
    public int getCover() {
        return cover;
    }

    /**设置书籍编号*/
    public void set_id(int _id) {
        this._id = _id;
    }
    /**设置书籍名称*/
    public void setName(String name) {
        this.name = name;
    }
    /**设置书籍地址*/
    public void setAddress(String address) {
        this.address = address;
    }
    /**设置书籍封面*/
    public void setCover(int cover) {
        this.cover = cover;
    }

    /**获取随机封面*/
    public static int getRandomCover() {
        int[] coverId = new int[5];
        coverId[0] = R.drawable.bookcover;
        coverId[1] = R.drawable.bookcover;
        coverId[2] = R.drawable.bookcover;
        coverId[3] = R.drawable.bookcover;
        coverId[4] = R.drawable.bookcover;

        int ran = new Random().nextInt(5);
        return coverId[ran];
    }
}
