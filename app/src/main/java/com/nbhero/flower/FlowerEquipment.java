package com.nbhero.flower;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.nbhero.Home.EquipmentList;
import com.nbhero.util.ZlzRootActivity;

/**
 * Created by zhenglingzhong on 2016/11/2.
 */


/**
 * @deprecated
 * 描述 已经用fragment替换 取消了本avtivity
 */
public class FlowerEquipment extends ZlzRootActivity{

    //   id
    private  int id = R.id.flowerequipment_fl;
    //view
    EquipmentList equipmentList = new EquipmentList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flowerequipment);
        aboutView();
    }
    private void aboutView(){
        change(equipmentList);
    }

    //改变选项卡
    private void change(Fragment pfragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(id,pfragment);
        transaction.commit();
    }
}
