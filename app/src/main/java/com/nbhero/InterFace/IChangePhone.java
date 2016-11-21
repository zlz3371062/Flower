package com.nbhero.InterFace;

/**
 * Created by zhenglingzhong on 2016/11/15.
 */

public interface IChangePhone extends IMainManger{

    //手机号码为空
    public  void codeNull();
    //错误的手机号码
    public  void errorPhone();
    //验证码为空
    public  void phoneNull();
    //    获取验证码失败
    public  void getCodeFail();
    //    获取验证码成功
    public  void getCodeSuccess();
    //    更换手机失败
    public  void changePhoneFail();

    //    更换手机成功
    public void changePhoneSuccess();

}
