package com.nbhero.model;

/**
 * Created by zhenglingzhong on 2016/10/25.
 */

public class MFlowerRegister extends  MMainModel{

    private String phoneNumber;
    private String passWord;
    private String code;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
