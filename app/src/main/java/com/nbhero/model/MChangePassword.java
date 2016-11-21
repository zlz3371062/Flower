package com.nbhero.model;

/**
 * Created by zhenglingzhong on 2016/11/16.
 */

public class MChangePassword extends  MMainModel {

    private String password;
    private  String newPassword;
    private  String newPasswordAgin;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordAgin() {
        return newPasswordAgin;
    }

    public void setNewPasswordAgin(String newPasswordAgin) {
        this.newPasswordAgin = newPasswordAgin;
    }
}
