package com.nbhero.mine;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.nbhero.Home.EquipmentList;
import com.nbhero.flower.R;
import com.nbhero.util.ZlzRootActivity;

/**
 * Created by zhenglingzhong on 2016/11/2.
 */

public class MainForMine extends ZlzRootActivity{


    private  String title[] = new String[]{"我的信息","消息","设备列表","设置","意见反馈","关于"};
    //    fragment标示
    private  int showFragment = -1;

    //fragment 实例
    Mine_mine mine = new Mine_mine();
    Mine_news news = new Mine_news();
    EquipmentList equipmentList = new EquipmentList();
    Mine_settting settting = new Mine_settting();
    Mine_advice  advice = new Mine_advice();
    Mine_about about = new Mine_about();
    //fragment列表
    Fragment fragments[] = new Fragment[]{mine,news, equipmentList,settting,advice,about};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainformine);
        aboutView();
    }

    private  void aboutView(){
        back();
        showFragment = getIntent().getIntExtra("id",-1);
        if(showFragment != -1){
            switchContent(fragments[showFragment]);
            settitle(title[showFragment]);


        }


    }



    public void switchContent(Fragment to) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        Log.e("zlz","1");

        if (!to.isAdded()) {    // 先判断是否被add过

            transaction.add(R.id.mainformine , to);
//                transaction.addToBackStack(null);
            transaction.show(to);
            transaction.commit();

        } else {
//                transaction.addToBackStack(null);
            transaction.show(to);
            transaction.commit(); // 隐藏当前的fragment，显示下一个
        }
    }
    private void hideFragments(FragmentTransaction transaction) {

        for (int i = 0 ; i < fragments.length ;i++){

            if(fragments[i].isAdded()){

                transaction.hide( fragments[i]);

            }

        }
    }

}
