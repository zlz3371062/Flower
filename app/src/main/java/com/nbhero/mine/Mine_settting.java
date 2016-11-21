package com.nbhero.mine;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nbhero.flower.R;

/**
 * Created by zhenglingzhong on 2016/11/2.
 */

public class Mine_settting extends Fragment implements View.OnClickListener{
    //view
    private LinearLayout llv[]=new LinearLayout[3];
    //id
    private  int llid[] = new int[]{R.id.setting_ll_changepassword,R.id.setting_ll_bangding,R.id.setting_ll_wraing};
    //class
    private   Class<?> airs[] = new Class<?>[]{ChangePassword.class,ChangePhone.class};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.mine_setting,null);
        aboutView(v);
        return v;
    }


    private  void aboutView(View v){

        for (int i = 0 ;i<llid.length;i++ ){

            llv[i] = (LinearLayout) v.findViewById(llid[i]);

            llv[i].setOnClickListener(this);

        }


    }

    @Override
    public void onClick(View view) {
        Log.e("zlz",view.getId() + "'");
        Log.e("zlz",llid[0]+ "");
        for (int i = 0 ; i<airs.length;i++){
            Log.e("zlz",view.getId() + "我飞");
            if(view.getId() == llid[i] ){
                Intent go = new Intent(getActivity(),airs[i]);
                getActivity().startActivity(go);

                break;

            }

        }


    }

}
