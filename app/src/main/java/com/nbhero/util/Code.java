package com.nbhero.util;

import android.os.Handler;
import android.widget.TextView;


/**
 * Created by zhenglingzhong on 2016/11/14.
 */

public class Code implements  Runnable{



    private  int time = 60;
    private Handler handler;
    private  TextView txt;


    public Code(Handler phandler, TextView ptxt){

        this.handler = phandler;
        this.txt  = ptxt;

    }

    @Override
    public void run() {

        if(time != 0){
            txt.setClickable(false);
            time -- ;
            handler.postDelayed(this,1000);
            txt.setText(time + "");
        } else {
            txt.setText("获取验证码");
            txt.setClickable(true);
        }

    }
}
