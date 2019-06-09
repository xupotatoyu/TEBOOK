package com.tebook.model;

public class Member {

    private String username;
    private String realname;
    private Integer money;

    public Member() {}
    public Member(String username, String realname, Integer money) {
        this.username = username;
        this.realname = realname;
        this.money = money;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
