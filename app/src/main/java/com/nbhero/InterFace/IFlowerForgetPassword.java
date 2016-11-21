package com.nbhero.InterFace;

/**
 * Created by zhenglingzhong on 2016/10/25.
 */

public interface IFlowerForgetPassword extends  IMainManger {
    /**
     *
     * 手机为空
     */
    public void emptyPhone();
    //错误的手机号码
    public  void errorPhone();

    /**
     *
     * 验证码为空
     *
     */
    public  void  emptyCode();
    /**
     * 新密码为空
     */
    public void emptyNewPassWord();

    /**
     *
     * 获取验证码成功
     */
    public void getCodeSuccess();
    /**
     *
     * 获取验证码失败
     */
    public void getCodeFail();

    /**
     *
     * 修改成功
     *
     */
    public  void changeSuccess();

    /**
     *
     * 修改失败
     *
     */
    public  void changeFail();
}
