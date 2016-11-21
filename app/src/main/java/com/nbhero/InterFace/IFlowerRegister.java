package com.nbhero.InterFace;

/**
 * Created by zhenglingzhong on 2016/10/25.
 */

public interface IFlowerRegister extends  IMainManger{
    //手机号码为空
    public void  emptyPhoneNum();

    //错误的手机号码
    public  void errorPhone();
    //密码未空
    public void emptyPassword();

    //验证码为空
    public void emptyCode();

    //获取验证码成功
    public void getCodeSuccess();

    //获取验证码失败
    public void getCodeFail();

    //注册成功
    public void registerSuccess();
    //注册失败
    public  void registerFail();



}
