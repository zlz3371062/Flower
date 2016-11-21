package com.nbhero.presenter;

import com.nbhero.InterFace.IChangePhone;
import com.nbhero.model.MChangePhone;
import com.nbhero.util.tool;

/**
 * Created by zhenglingzhong on 2016/11/15.
 */

public class PChangePhone extends PMainManger {

    private MChangePhone mChangePhone;
    private IChangePhone iChangePhone;

    private void init(){
        mChangePhone = (MChangePhone) model;
        iChangePhone = (IChangePhone) listerner;

    }

    public  void getCode(){
        init();
        String phone =  mChangePhone.getPhone();
        if(phone.equals("")){
            iChangePhone.phoneNull();

            return;
        }
        if(!tool.isMobileNO(phone)){
            iChangePhone.errorPhone();
            return;

        }
        iChangePhone.getCodeSuccess();

    }
    public  void sure(){
        init();
        String phone =  mChangePhone.getPhone();
        String code =  mChangePhone.getCode();
        if(phone.equals("")){
            iChangePhone.phoneNull();

            return;
        }
        if(!tool.isMobileNO(phone)){
            iChangePhone.errorPhone();
            return;

        }
        if(code.equals("")){
            iChangePhone.codeNull();

            return;
        }
        iChangePhone.changePhoneSuccess();

    }

}
