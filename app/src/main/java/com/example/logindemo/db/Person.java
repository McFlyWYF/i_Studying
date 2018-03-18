package com.example.logindemo.db;

import cn.bmob.v3.BmobObject;

/**
 * Created by 王宇飞 on 2018/3/18/018.
 */

public class Person extends BmobObject {

    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
