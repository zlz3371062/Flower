package com.nbhero.equipment;

import android.app.Fragment;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nbhero.flower.R;

/**
 * Created by zhenglingzhong on 2016/11/3.
 */

public class EquipmentTime extends Fragment implements View.OnClickListener{



    private int llweekid[] = new int[]{R.id.equipment_time_ll_monday,R.id.equipment_time_ll_tuesday,
            R.id.equipment_time_ll_wednesday,R.id.equipment_time_ll_thursday,
            R.id.equipment_time_ll_friday,R.id.equipment_time_ll_saturday,
            R.id.equipment_time_ll_sunday,R.id.equipment_time_ll_sure};

    private int imgweekid[] = new int[]{R.id.equipment_time_img_monday,R.id.equipment_time_img_tuesday,
            R.id.equipment_time_img_wednesday,R.id.equipment_time_img_thursday,
            R.id.equipment_time_img_friday,R.id.equipment_time_img_saturday,
            R.id.equipment_time_img_sunday};
    private LinearLayout week[] = new LinearLayout[8];
    private ImageView weekimg[] = new ImageView[7];
    private int state[] = new int[]{0,0,0,0,0,0,0};
    private  int imgresource[] = new int[]{R.drawable.addno,R.drawable.addyes};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.equipment_time,null);
        aboutView(v);
        return  v;
    }

    private  void aboutView(View v){

        for (int i=0 ; i<llweekid.length;i++){

            week[i]= (LinearLayout) v.findViewById(llweekid[i]);
            week[i].setOnClickListener(this);

        }
        for (int i=0 ; i<imgweekid.length;i++){

            weekimg[i]= (ImageView) v.findViewById(imgweekid[i]);

        }


    }

    @Override
    public void onClick(View view) {

        Log.e("zlz",view.getId()+ "");
        for (int i = 0 ; i<llweekid.length;i++){

            if(view.getId() == llweekid[i]){
                Log.e("zlz",i+"");
                if(i < 7){
                    Log.e("zlz",i+"");
                    if( state[i] == 0){
                        state[i] = 1;
                    }else{
                        state[i] = 0;
                    }
                    weekimg[i].setImageResource(imgresource[state[i]]);
                }


                break;
            }


        }


    }


}

