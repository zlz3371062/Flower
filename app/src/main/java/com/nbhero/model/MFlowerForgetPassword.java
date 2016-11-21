package com.nbhero.model;

/**
 * Created by zhenglingzhong on 2016/10/25.
 */

public class MFlowerForgetPassword extends MMainModel{


    private String  phoneNum;
    private String  code;
    private String  newPassword;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
