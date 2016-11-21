package com.nbhero.equipment;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.nbhero.flower.R;
import com.nbhero.util.ZlzRootActivity;

/**
 * Created by zhenglingzhong on 2016/11/3.
 */

public class AboutEquipment extends ZlzRootActivity {

    //fragment
    EquipmentTime equipmentTime = new EquipmentTime();
    EquipmentHold equipmentHold = new EquipmentHold();
    EquipmentAbout equipmentAbout = new EquipmentAbout();
    Fragment  views[] = new Fragment[]{equipmentTime,equipmentHold,equipmentAbout};
    Dialog dialog;

    private int where = -1;
    private String title[] = new String[]{"运行时长","工作时段","设备信息"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.euqipment_abouteuqipment);
        back();
        this.where = getIntent().getIntExtra("where",-1);
        if(where != -1){

            aboutView();
        }

    }


    private void aboutView(){
        settitle(title[where]);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.aboutequipment_fl,views[where]);
        ft.commit();

    }


}
