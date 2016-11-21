package com.nbhero.model;

/**
 * Created by zhenglingzhong on 2016/10/24.
 */

public class MFlowerLogin extends  MMainModel{

    private String userName;
    private String userPassWord  ;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }

}
