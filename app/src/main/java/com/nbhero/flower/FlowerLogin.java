package com.nbhero.flower;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nbhero.DIYview.FlowerLoading;
import com.nbhero.InterFace.IFlowerLogin;
import com.nbhero.model.MFlowerLogin;
import com.nbhero.presenter.PFlowerLogin;
import com.nbhero.util.ZlzRootActivity;

import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.v3.Bmob;

/**
 * Created by zhenglingzhong on 2016/10/24.
 */

public class FlowerLogin extends ZlzRootActivity implements IFlowerLogin, View.OnClickListener{

    private PFlowerLogin pFlowerLogin;
    private MFlowerLogin mFlowerLogin;

    private EditText etuserName;
    private EditText etpassWord;
    private TextView btnLogin;
    private TextView btnForget;
    private TextView btnRegister;
    private Intent go;
    private ImageView showPassword;
    private int seestype = 0;
    private Toast mytoast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setBar(1);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.flowerlogin);
        BmobSMS.initialize(this,"ea757297eaec7024867c88a79b8dd636");
//        Bmob.initialize(this, "ea757297eaec7024867c88a79b8dd636");
        init();
        aboutView();
    }


    //view初始化之前的绑定工作 和 获取数据工作

    private  void init(){

        pFlowerLogin = new PFlowerLogin();
        mFlowerLogin = new MFlowerLogin();
        bind(pFlowerLogin,mFlowerLogin,this);

    }
    private void getData(){

        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        mFlowerLogin.setUserName(sharedPreferences.getString("name",""));
        mFlowerLogin.setUserPassWord(sharedPreferences.getString("password",""));

    }

    //view相关
    private void aboutView(){

        getData();
        this.etuserName = (EditText) findViewById(R.id.login_et_username);
        this.etpassWord = (EditText) findViewById(R.id.login_et_password);
        this.btnForget = (TextView) findViewById(R.id.login_btn_forget);
        this.btnLogin = (TextView) findViewById(R.id.login_btn_login);
        this.btnRegister = (TextView) findViewById(R.id.login_btn_reginster);
        this.showPassword = (ImageView) findViewById(R.id.login_img_see);
        showPassword.setOnClickListener(this);
        this.btnForget.setOnClickListener(this);
        this.btnLogin.setOnClickListener(this);
        this.btnRegister.setOnClickListener(this);
        if (!(mFlowerLogin.getUserName().equals(""))){
            etuserName.setText(mFlowerLogin.getUserName());
            etpassWord.setText(mFlowerLogin.getUserPassWord());
        }


    }

    //login
    private void login(){

        this.mFlowerLogin.setUserName(etuserName.getText().toString().trim());
        this.mFlowerLogin.setUserPassWord(etpassWord.getText().toString().trim());
        this.pFlowerLogin.login();



    }



    //重写接口方法
    @Override
    public void enterUserName() {

        mytoast = Toast.makeText(this,"请输入用户名",Toast.LENGTH_SHORT);
        mytoast.show();
    }

    @Override
    public void enterUserPwd() {

        mytoast = Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT);
        mytoast.show();

    }

    @Override
    public void loginSuccess() {

        save();
        go = new Intent(this,MainActivity.class);
        this.startActivity(go);
        this.finish();
    }

    @Override
    public void loginFaild(String code) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case  R.id.login_btn_forget:
                go = new Intent(this,FlowerForgetPassword.class);
                this.startActivity(go);

                break;
            case   R.id.login_btn_login:

                login();

                break;
            case   R.id.login_btn_reginster:

                go = new Intent(this,FlowerRegister.class);
                this.startActivity(go);

                break;

            case  R.id.login_img_see:
                if(seestype == 0){
                    Log.e("zlz",seestype + "");
                    seestype = 1;
                    etpassWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                }else{
                    Log.e("zlz",seestype + "");
                    seestype = 0;
                    etpassWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }


                break;
        }

    }
    @Override
    public void zlzG() {

    }


    private void save (){
        String name = mFlowerLogin.getUserName();
        String password = mFlowerLogin.getUserPassWord();
        SharedPreferences.Editor editor = getSharedPreferences("user",MODE_PRIVATE).edit();
        editor.putString("name",mFlowerLogin.getUserName());
        editor.putString("password",mFlowerLogin.getUserPassWord());
        editor.commit();
    }



}
