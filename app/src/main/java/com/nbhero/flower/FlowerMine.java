package com.nbhero.flower;

import android.os.Bundle;

import com.nbhero.util.ZlzRootActivity;

/**
 * Created by zhenglingzhong on 2016/11/1.
 */


/**
 * @deprecated
 * 描述 已经用fragment替换 取消了本avtivity
 */
public class FlowerMine extends ZlzRootActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBarColor(R.color.headcolor);
        setContentView(R.layout.flower_mine);
        aboutView();
    }

    private void aboutView(){

        settitle("我的资料");
        back();

    }
}
