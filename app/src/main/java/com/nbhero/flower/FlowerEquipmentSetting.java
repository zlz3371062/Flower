package com.nbhero.flower;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.nbhero.util.ZlzRootActivity;

/**
 * Created by zhenglingzhong on 2016/11/3.
 */

public class FlowerEquipmentSetting extends ZlzRootActivity implements View.OnClickListener{

    private  int  llid[] = new int[]{R.id.equipment_ll_addgroup};
    private LinearLayout llv[] =  new LinearLayout[4];

    //log'、
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flowerequipmentsetting);
        aboutView();
    }

    private  void aboutView(){
        settitle("信息设定");
        back();
        for (int i = 0 ;i<llid.length;i++){

            llv[i] = (LinearLayout) findViewById(llid[i]);

            llv[i].setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View view) {

        for (int i = 0;i<llid.length;i++){

            if(view.getId() == llid[i]){
                click(i);
                break;
            }
        }
    }

    private  void click(int i){

        if (i == 0){

            showLog();

        }


    }
    //新建分组框
    private void  showLog(){

        View v = LayoutInflater.from(this).inflate(R.layout.log_foraddlist,null);
        dialog = new Dialog(this);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setBackgroundDrawableResource(R.drawable.back_dialog);
        dialogWindow.requestFeature(Window.FEATURE_NO_TITLE);
        dialogWindow.setContentView(v);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        dialogWindow.setAttributes(lp);

        dialog.show();


    }

}
