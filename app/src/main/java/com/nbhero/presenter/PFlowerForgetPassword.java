package com.nbhero.presenter;

import android.app.Activity;

import com.nbhero.InterFace.IFlowerForgetPassword;
import com.nbhero.InterFace.IMainManger;
import com.nbhero.model.MFlowerForgetPassword;
import com.nbhero.util.tool;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by zhenglingzhong on 2016/10/25.
 */

public class PFlowerForgetPassword extends PMainManger {

    private  MFlowerForgetPassword mFlowerForgetPassword;
    private IFlowerForgetPassword iFlowerForgetPassword;


    private   void init(){

        this.mFlowerForgetPassword = (MFlowerForgetPassword)model;
        this.iFlowerForgetPassword = (IFlowerForgetPassword)listerner;


    }
    public void getCode(){

        init();

        String phone = mFlowerForgetPassword.getPhoneNum();
        if (phone.equals("")){
            iFlowerForgetPassword.emptyPhone();
            return;

        }

        if(!tool.isMobileNO(phone)){

            iFlowerForgetPassword.errorPhone();
            return;
        }

        iFlowerForgetPassword.getCodeSuccess();


    }

    public void sure(){
        init();
        String phone = mFlowerForgetPassword.getPhoneNum();
        String code = mFlowerForgetPassword.getCode();
        String newpassword = mFlowerForgetPassword.getNewPassword();
        if (phone.equals("")){
            iFlowerForgetPassword.emptyPhone();
            return;

        }
        if(!tool.isMobileNO(phone)){

            iFlowerForgetPassword.errorPhone();
            return;
        }
        if (code.equals("")){
            iFlowerForgetPassword.emptyCode();
            return;
        }

        if(newpassword.equals("")){

            iFlowerForgetPassword.emptyNewPassWord();
            return;
        }
        iFlowerForgetPassword.changeSuccess();
    }


}
