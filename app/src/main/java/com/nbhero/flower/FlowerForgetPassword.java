package com.nbhero.flower;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nbhero.InterFace.IFlowerForgetPassword;
import com.nbhero.model.MFlowerForgetPassword;
import com.nbhero.model.MFlowerRegister;
import com.nbhero.presenter.PFlowerForgetPassword;
import com.nbhero.presenter.PFlowerRegister;
import com.nbhero.util.Code;
import com.nbhero.util.ZlzRootActivity;

/**
 * Created by zhenglingzhong on 2016/10/25.
 */

public class FlowerForgetPassword extends ZlzRootActivity implements IFlowerForgetPassword, View.OnClickListener{

    //控件
    private EditText etphoneNum,etcode,etnewPassword;
    private TextView title,btngetCode,btnsure;


    PFlowerForgetPassword pFlowerForgetPassword;
    MFlowerForgetPassword mFlowerForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setBarColor(R.color.headcolor);
        setContentView(R.layout.flowerforgetpassword);
        init();
        aboutView();

    }
    private void init(){
        pFlowerForgetPassword = new PFlowerForgetPassword();
        mFlowerForgetPassword = new MFlowerForgetPassword();
        bind(pFlowerForgetPassword,mFlowerForgetPassword,this);

    }


    private void aboutView(){
        this.back();
        this.settitle("忘记密码");
        this.etphoneNum = (EditText) findViewById(R.id.forgetpassword_et_phonenum);
        this.etcode = (EditText) findViewById(R.id.forgetpassword_et_code);
        this.etnewPassword = (EditText) findViewById(R.id.forgetpassword_et_newpassword);
        this.btnsure = (TextView) findViewById(R.id.forgetpassword_btn_sure);
        this.btngetCode= (TextView) findViewById(R.id.forgetpassword_btn_getcode);
        this.btngetCode.setOnClickListener(this);
        this.btnsure.setOnClickListener(this);


    }

    private  void getCode(){

        mFlowerForgetPassword.setPhoneNum(etphoneNum.getText().toString().trim());
        pFlowerForgetPassword.getCode();

    }
    public void sure(){
        mFlowerForgetPassword.setPhoneNum(etphoneNum.getText().toString().trim());
        mFlowerForgetPassword.setCode(etcode.getText().toString().trim());
        mFlowerForgetPassword.setNewPassword(etnewPassword.getText().toString().trim());
        pFlowerForgetPassword.sure();

    }

    //重写接口方法
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forgetpassword_btn_getcode :
                getCode();
                break;

            case  R.id.forgetpassword_btn_sure:
                sure();
                break;


        }



    }

    @Override
    public void emptyPhone() {

        Toast.makeText(this,"手机号码为空",Toast.LENGTH_LONG).show();

    }

    @Override
    public void errorPhone() {
        Toast.makeText(this,"请输入正确的手机号码",Toast.LENGTH_LONG).show();
    }

    @Override
    public void emptyCode() {
        Toast.makeText(this,"验证码为空",Toast.LENGTH_LONG).show();
    }

    @Override
    public void emptyNewPassWord() {
        Toast.makeText(this,"请输入新密码",Toast.LENGTH_LONG).show();
    }

    @Override
    public void getCodeSuccess() {

        Code code = new Code(myHandle,btngetCode);
        myHandle.postDelayed(code,1000);
    }

    @Override
    public void getCodeFail() {

        Toast.makeText(this,"获取验证码失败，请重新获取",Toast.LENGTH_LONG).show();

    }

    @Override
    public void changeSuccess() {
        Toast.makeText(this,"修改密码成功",Toast.LENGTH_LONG).show();

    }

    @Override
    public void changeFail() {

        Toast.makeText(this,"修改密码失败，请重新获取",Toast.LENGTH_LONG).show();
    }

    @Override
    public void zlzG() {

    }


    public Handler myHandle = new Handler();
}
