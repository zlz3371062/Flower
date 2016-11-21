package com.nbhero.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nbhero.InterFace.IChangePassword;
import com.nbhero.flower.R;
import com.nbhero.model.MChangePassword;
import com.nbhero.presenter.PChangePassword;
import com.nbhero.util.ZlzRootActivity;

/**
 * Created by zhenglingzhong on 2016/11/2.
 */

public class ChangePassword extends ZlzRootActivity implements IChangePassword,View.OnClickListener{


    private PChangePassword pChangePassword;
    private MChangePassword mChangePassword;

    //id

    private final int id_et_password = R.id.changepassword_et_password;
    private final int id_et_newPassword = R.id.changepassword_et_newpassword;
    private final int id_et_newPasswordAgin = R.id.changepassword_et_newpasswordagin;
    private final  int id_txt_sure = R.id.changepassword_txt_sure;
    //view
    private EditText view_et_password;
    private EditText view_et_newPassword;
    private EditText view_et_newPasswordAgin;
    private TextView view_txt_sure;
    //
//    private  int etids[] = new int[]{id_et_password,id_et_newPassword,id_et_newPasswordAgin};
//    private  EditText etviews[] = new EditText[]{view_et_password,view_et_newPassword,view_et_newPasswordAgin};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flower_changepassword);
        aboutView();
    }


    private  void aboutView(){

        pChangePassword = new PChangePassword();
        mChangePassword = new MChangePassword();
        bind(pChangePassword,mChangePassword,this);
        bind(pChangePassword,mChangePassword,this);
        settitle("修改密码");
        back();
//        for (int i =0;i<etids.length;i++){
//
//            etviews[i] = (EditText) findViewById(etids[i]);
//        }
        view_et_password = (EditText) findViewById(id_et_password);
        view_et_newPassword = (EditText) findViewById(id_et_newPassword);
        view_et_newPasswordAgin = (EditText) findViewById(id_et_newPasswordAgin);
        view_txt_sure = (TextView) findViewById(id_txt_sure);
        view_txt_sure.setOnClickListener(this);

    }

    private  void sure(){

        mChangePassword.setPassword(view_et_password.getText().toString().trim());
        mChangePassword.setNewPassword(view_et_newPassword.getText().toString().trim());
        mChangePassword.setNewPasswordAgin(view_et_newPasswordAgin.getText().toString().trim());
        pChangePassword.sure();

    }


    @Override
    public void passwordNull() {

        Toast.makeText(this,"请输入原密码",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void newPasswordNull() {
        Toast.makeText(this,"请输入新密码",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void newPasswordAginNull() {
        Toast.makeText(this,"请再次输入新密码",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void changeSuccess() {
        Toast.makeText(this,"密码更新成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeFail() {

        Toast.makeText(this,"密码更新失败",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void zlzG() {

    }

    @Override
    public void onClick(View view) {
        sure();
    }
}
