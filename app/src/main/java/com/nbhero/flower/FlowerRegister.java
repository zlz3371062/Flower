package com.nbhero.flower;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nbhero.InterFace.IFlowerRegister;
import com.nbhero.bean.Person;
import com.nbhero.model.MFlowerRegister;
import com.nbhero.presenter.PFlowerRegister;
import com.nbhero.util.Code;
import com.nbhero.util.ZlzRootActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.newsmssdk.exception.BmobException;
import cn.bmob.newsmssdk.listener.RequestSMSCodeListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by zhenglingzhong on 2016/10/25.
 */

public class FlowerRegister extends ZlzRootActivity implements IFlowerRegister, View.OnClickListener {

    MFlowerRegister mFlowerRegister;
    PFlowerRegister pFlowerRegister;
    //控件
    private EditText etphoneNum , etpassWord,etcode;
    private TextView btngetCode,btnregister,txttip,txtpasswordtip;
    //控件id
    private   final int intetphoneNum = R.id.reginster_et_phonenumber;
    private   final int intetpassWord = R.id.reginster_et_password;
    private   final int intetcode = R.id.reginster_et_code;
    private   final int intbtngetCode = R.id.reginster_btn_getcode;
    private   final int intbtnregister = R.id.reginster_btn_reginster;
    private    final  int inttxttip = R.id.reginster_txt_tip;
    private  final  int  passwordtipid = R.id.register_txt_passwordtip;
    //提示控件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setBarColor(R.color.headcolor);
        setContentView(R.layout.flowerregister);
        init();
        aboutView();

    }


    private void init(){
        mFlowerRegister = new MFlowerRegister();
        pFlowerRegister = new PFlowerRegister();
        bind(pFlowerRegister,mFlowerRegister,this);

    }

    private  void aboutView(){
        this.settitle("注册帕乔");
        this.back();
        etphoneNum = (EditText) findViewById(intetphoneNum);
        etpassWord = (EditText) findViewById(intetpassWord);
        etcode = (EditText) findViewById(intetcode);
        txtpasswordtip = (TextView) findViewById(passwordtipid);
        txtpasswordtip.setText("8-16位的大小写字母、数字、符号的组合，请至少选择两种类型");
        btngetCode = (TextView) findViewById(intbtngetCode);
        btnregister= (TextView) findViewById(intbtnregister);
        txttip = (TextView) findViewById(inttxttip);
        txttip.setText(Html.fromHtml("清触上面的\"注册\"按钮，即表示您同意<font color=blue >《隐私条例和服务条款》</font>"));
        btngetCode.setOnClickListener(this);
        btnregister.setOnClickListener(this);
    }
    //获取验证码
    private void getCode(){

        mFlowerRegister.setPhoneNumber(etphoneNum.getText().toString().trim());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sendTime = format.format(new Date());
        BmobSMS.requestSMS(this, etphoneNum.getText().toString().trim(), "注册验证码",sendTime,new RequestSMSCodeListener() {

            @Override
            public void done(Integer smsId,BmobException ex) {
                // TODO Auto-generated method stub
                if(ex==null){//
                    Log.i("bmob","短信发送成功，短信id："+smsId);//用于查询本次短信发送详情
                }else{
                    Log.i("bmob","errorCode = "+ex.getErrorCode()+",errorMsg = "+ex.getLocalizedMessage());
                }
            }
        });

    }
    //注册
    private  void  reginster(){
        mFlowerRegister.setPhoneNumber(etphoneNum.getText().toString().trim());
        mFlowerRegister.setPassWord(etpassWord.getText().toString().trim());
        mFlowerRegister.setCode(etcode.getText().toString().trim());
        pFlowerRegister.reginster();

    }

    @Override
    public void emptyPhoneNum() {

        Toast.makeText(this,"手机号码为空",Toast.LENGTH_LONG).show();

    }

    @Override
    public void errorPhone() {

        Toast.makeText(this,"请输入正确的手机号码",Toast.LENGTH_LONG).show();
    }

    @Override
    public void emptyPassword() {
        Toast.makeText(this,"密码为空",Toast.LENGTH_LONG).show();

    }

    @Override
    public void emptyCode() {

        Toast.makeText(this,"验证码为空",Toast.LENGTH_LONG).show();

    }

    @Override
    public void getCodeSuccess() {

        Code code = new Code(myhandler,btngetCode);
        myhandler.postDelayed(code,1000);

    }

    @Override
    public void getCodeFail() {

        Toast.makeText(this,"获取验证码失败，请重新获取",Toast.LENGTH_LONG).show();
    }

    @Override
    public void registerSuccess() {


        Person p2 = new Person();
        p2.setName("lucky");
        p2.setAddress("北京海淀");
        p2.save(new SaveListener<String>() {


            @Override
            public void done(String s, cn.bmob.v3.exception.BmobException e) {

            }
        });
        Toast.makeText(this,"注册成功",Toast.LENGTH_LONG).show();

    }

    @Override
    public void registerFail() {
        Toast.makeText(this,"注册失败",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case intbtngetCode:
                getCode();
                break;
            case intbtnregister:
                reginster();
                break;

        }
    }

    @Override
    public void zlzG() {

    }


    public Handler myhandler = new Handler();
}
