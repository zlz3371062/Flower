package com.nbhero.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nbhero.InterFace.IMainManger;
import com.nbhero.flower.R;
import com.nbhero.model.MFlowerLogin;
import com.nbhero.model.MMainModel;
import com.nbhero.presenter.PFlowerLogin;
import com.nbhero.presenter.PMainManger;

/**
 * Created by mac on 16/4/8.
 */
public class ZlzRootActivity extends Activity{

    private   SystemBarTintManager ZLZtintManager;

    private   boolean  isTranslucen  = true;


    private  TextView title;


    private  int stype = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(stype == 0){
            makeBar();
        }


    }

    protected  void setBar(int pstype){

        this.stype = pstype;

    }
    private  void makeBar(){

        WindowManager wm = this.getWindowManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true); //false 为沉浸效果
            ZLZtintManager = new SystemBarTintManager(this);
            ZLZtintManager.setStatusBarTintEnabled(true);
            ZLZtintManager.setStatusBarTintResource(R.color.headcolor);

        }
    }

    protected void bind(PMainManger pMainManger, MMainModel mMainModel, IMainManger listener){
        pMainManger.setModel(mMainModel);
        pMainManger.setSelfListerner(listener);
    }


    protected  void setBarColor(int pcolor){

        ZLZtintManager.setStatusBarTintResource(pcolor);

    }

    protected void back(){

        LinearLayout img = (LinearLayout) findViewById(R.id.flowerback);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ZlzRootActivity.this.finish();

            }
        });


    }

    public  void settitle(String ptitle){

        title = (TextView) findViewById(R.id.hometitel);
        title.setText(ptitle);

    }


    //转像素
    public   int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    //转dp
    private   int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //状态栏相关
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    //获取状态栏高度
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}