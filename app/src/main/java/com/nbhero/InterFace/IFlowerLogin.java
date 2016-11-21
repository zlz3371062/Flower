package com.nbhero.InterFace;

import android.content.Intent;

/**
 * Created by zhenglingzhong on 2016/10/24.
 */

public interface IFlowerLogin extends  IMainManger{

    /**
     * 未输入用户名提示请输入用户名
     */
    void enterUserName();
    /**
     * 未输入密码提示请输入密码
     */
    void enterUserPwd();
    /**
     * 登录成功
     */
    void loginSuccess();
    /**
     * 登录失败
     * @param code 错误码 1该账号被禁用,2用户名或密码错误,3用户名不存在
     */
    void loginFaild(String code);

    /**
     *跳转页面
     */
//   void  goTo(Intent intent);

}
