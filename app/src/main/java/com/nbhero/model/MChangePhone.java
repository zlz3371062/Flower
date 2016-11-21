package com.nbhero.model;

import android.widget.EditText;
import android.widget.TextView;

import com.nbhero.flower.R;

/**
 * Created by zhenglingzhong on 2016/11/15.
 */

public class MChangePhone extends MMainModel {

    private  String phone;
    private  String code;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
