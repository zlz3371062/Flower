package com.nbhero.presenter;

import android.app.Activity;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.nbhero.InterFace.IFlowerRegister;
import com.nbhero.InterFace.IMainManger;
import com.nbhero.model.MFlowerRegister;
import com.nbhero.util.tool;

import java.util.Date;

import cn.bmob.newsmssdk.BmobSMS;

/**
 * Created by zhenglingzhong on 2016/10/25.
 */

public class PFlowerRegister extends PMainManger {



    MFlowerRegister    mFlowerRegister;
    IFlowerRegister iFlowerRegister;



    private  void init(){

        this.mFlowerRegister = (MFlowerRegister) model;
        this.iFlowerRegister  = (IFlowerRegister) listerner;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public   void  getCode(){
        init();
        String phone = mFlowerRegister.getPhoneNumber();
        Log.e("zlz","phone" + phone);

        if (phone.equals("")){

            iFlowerRegister.emptyPhoneNum();
            return;
        }

        if(!tool.isMobileNO(phone)){
            iFlowerRegister.errorPhone();
            return;
        }



    }

    public  void reginster(){
        init();
        String phone = mFlowerRegister.getPhoneNumber();
        String password = mFlowerRegister.getPassWord();
        String code = mFlowerRegister.getCode();
        if(phone.equals("")){
            iFlowerRegister.emptyPhoneNum();
            return;
        }
        if(!tool.isMobileNO(phone)){
            iFlowerRegister.errorPhone();
            return;
        }
        if(password.equals("")){
            iFlowerRegister.emptyPassword();
            return;
        }
        if(code.equals("")){
            iFlowerRegister.emptyCode();
            return;
        }


        iFlowerRegister.registerSuccess();
    }

}
