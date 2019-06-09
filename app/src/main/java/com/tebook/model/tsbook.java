package com.tebook.model;

import java.util.Date;

public class tsbook {
    private int id;							//书本编号
    private String bookname = null;				//书本名称
    private String author = null;			//作者
    private String content = null;
    private String EnglishName = null;//简介


    public tsbook(int id, String bookname, String author, String content) {
        super();
        this.id = id;
        this.bookname = bookname;
        this.author = author;
        this.content = content;
        this.EnglishName = EnglishName;
    }

    public tsbook() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getbookname() {
        return bookname;
    }

    public void setbookname(String bookname) {

        this.bookname = bookname;
    }

    public String getauthor() {
        return author;
    }

    public void setauthor(String author) {
        this.author = author;
    }

    public String getcontent() {
        return content;
    }

    public void setcontent(String content) {
        this.content = content;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String EnglishName) {
        this.EnglishName = EnglishName;
    }

    @Override
    public String toString() {
        return "Game [id=" + id + ", bookname=" + bookname + ", \n"+
        ", EnglishName=" + EnglishName + ", \n" + "author=" + author + ", "
                + "content=" + content + ", \n" +"]";
    }


}
