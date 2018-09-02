package com.zhenjie.smartidiot.entity;

import cn.bmob.v3.BmobUser;

/**
 * 文件名: MyUser
 * 创建者: Jack Yan
 * 创建日期: 2018/9/2 12:31
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：Bmob用户类的扩展类
 */
public class MyUser extends BmobUser{

    private int age;
    private boolean gender;
    private String description;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}