package com.nbhero.presenter;

import android.util.Log;

import com.nbhero.DIYview.FlowerLoading;
import com.nbhero.InterFace.IFlowerLogin;
import com.nbhero.model.MFlowerLogin;

/**
 * Created by zhenglingzhong on 2016/10/24.
 */

public class PFlowerLogin  extends PMainManger {

    private IFlowerLogin iFlowerLogin;
    private  MFlowerLogin mFlowerLogin;


    public  void init(){

        iFlowerLogin = (IFlowerLogin)listerner;
        mFlowerLogin = (MFlowerLogin)model;

    }




    public void login(){
        init();
        Log.e("zlz","login");
        String username = mFlowerLogin.getUserName();
        String password = mFlowerLogin.getUserPassWord();
        if(username.equals("")){
            iFlowerLogin.enterUserName();
            return;
        }
        if (password.equals("")){

            iFlowerLogin.enterUserPwd();
            return;
        }

        iFlowerLogin.loginSuccess();





    }




}
