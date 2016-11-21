package com.nbhero.presenter;

import com.nbhero.InterFace.IChangePassword;
import com.nbhero.InterFace.IChangePhone;
import com.nbhero.model.MChangePassword;

/**
 * Created by zhenglingzhong on 2016/11/16.
 */

public class PChangePassword extends PMainManger {
    private IChangePassword iChangePassword;
    private MChangePassword mChangePassword;


    private  void init(){
        iChangePassword = (IChangePassword) listerner;
        mChangePassword = (MChangePassword) model;

    }

    public  void sure(){
        init();
        String password = mChangePassword.getPassword();
        String newPassword = mChangePassword.getNewPassword();
        String newPasswordAgin = mChangePassword.getNewPasswordAgin();
        if(password.equals("")){

            iChangePassword.passwordNull();
            return;

        }
        if(newPassword.equals("")){

            iChangePassword.newPasswordNull();
            return;
        }
        if(newPasswordAgin.equals("")){


            iChangePassword.newPasswordAginNull();
            return;
        }

        iChangePassword.changeSuccess();
    }

}
