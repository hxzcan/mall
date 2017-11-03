package com.ican.vo;

import com.ican.pojo.User;

/**
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/11/3 0003
 * Time: 11:29
 */
public class UserVo extends User {
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "token='" + token + '\'' +
                '}';
    }
}
