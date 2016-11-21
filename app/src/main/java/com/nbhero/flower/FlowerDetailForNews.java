package com.nbhero.flower;

import android.os.Bundle;

import com.nbhero.util.ZlzRootActivity;

/**
 * Created by zhenglingzhong on 2016/11/2.
 */


/**
 * @deprecated
 * 描述 已经用fragment替换 取消了本avtivity
 */

public class FlowerDetailForNews extends ZlzRootActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flowerdetailfornews);
        aboutView();
    }



    private void aboutView(){
        settitle("详情");
        back();

    }




}
