package com.nbhero.mine;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nbhero.InterFace.IChangePhone;
import com.nbhero.flower.R;
import com.nbhero.model.MChangePhone;
import com.nbhero.presenter.PChangePhone;
import com.nbhero.util.Code;
import com.nbhero.util.ZlzRootActivity;

/**
 * Created by zhenglingzhong on 2016/11/2.
 */

public class ChangePhone extends ZlzRootActivity implements IChangePhone,View.OnClickListener{


    private MChangePhone mChangePhone;
    private PChangePhone pChangePhone;

    //id
    private  final int id_et_phone = R.id.forgetpassword_et_phonenum;
    private  final  int id_et_code = R.id.forgetpassword_et_code;
    private  final  int id_txt_getCode = R.id.forgetpassword_btn_getcode;
    private  final int id_txt_sure = R.id.forgetpassword_btn_sure;
    //view
    private EditText view_et_phone;
    private EditText view_et_code;
    private TextView view_txt_getCode;
    private TextView view_txt_sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flower_changephone);
        aboutView();
    }

    private  void aboutView(){
        settitle("更换手机");
        init();
        back();
        view_et_code = (EditText) findViewById(id_et_code);
        view_et_phone = (EditText) findViewById(id_et_phone);
        view_txt_getCode= (TextView) findViewById(id_txt_getCode);
        view_txt_sure = (TextView) findViewById(id_txt_sure);
        view_txt_getCode.setOnClickListener(this);
        view_txt_sure.setOnClickListener(this);


    }
    private void getCode(){

        mChangePhone.setPhone(view_et_phone.getText().toString().trim());
        pChangePhone.getCode();

    }
    private void sure(){

        mChangePhone.setPhone(view_et_phone.getText().toString().trim());
        mChangePhone.setCode(view_et_code.getText().toString().trim());
        pChangePhone.sure();

    }


    //绑定
    private  void  init(){
        pChangePhone = new PChangePhone();
        mChangePhone = new MChangePhone();
        bind(pChangePhone,mChangePhone,this);
    }

    @Override
    public void codeNull() {
        Toast.makeText(this,"验证码为空",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void errorPhone() {
        Toast.makeText(this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void phoneNull() {
        Toast.makeText(this,"手机号码为空",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getCodeFail() {
        Toast.makeText(this,"获取验证码失败",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void getCodeSuccess() {
        Toast.makeText(this,"获取验证码成功",Toast.LENGTH_SHORT).show();

        Code code  = new Code(myhandler,view_txt_getCode);
        myhandler.postDelayed(code,1000);

    }

    @Override
    public void changePhoneFail() {
        Toast.makeText(this,"修改手机失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changePhoneSuccess() {
        Toast.makeText(this,"修改手机成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void zlzG() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case id_txt_getCode:
                getCode();
                break;
            case id_txt_sure:
                sure();
                break;

        }
    }

    private Handler myhandler = new Handler();
}
