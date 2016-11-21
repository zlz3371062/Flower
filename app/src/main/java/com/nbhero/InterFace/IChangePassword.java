package com.nbhero.InterFace;

/**
 * Created by zhenglingzhong on 2016/11/16.
 */

public interface IChangePassword  extends  IMainManger{

    //原密码为空
    public  void  passwordNull();
    //新密码为空
    public  void newPasswordNull();
    //请再次输入新密码
    public  void newPasswordAginNull();

    //密码更新成功
    public void changeSuccess();
    //密码更新失败
    public  void changeFail();

}
