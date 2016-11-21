package com.nbhero.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nbhero.flower.R;
import com.nbhero.bean.BWifi;

import java.util.List;

/**
 * Created by zhenglingzhong on 2016/10/27.
 */

public class ForaddEquipment extends BaseAdapter {

    List<BWifi> wifis;
    Activity ac;


    public ForaddEquipment(Activity pac, List<BWifi> pname){

        this.ac = pac;
        wifis = pname;
        Log.e("zlz",wifis.size() + "适配器中的数据条数");

    }

    @Override
    public int getCount() {
        return wifis.size();
    }

    @Override
    public Object getItem(int i) {
        return wifis.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        if (view == null){

            view = LayoutInflater.from(this.ac).inflate(R.layout.item_foraddequipment,null);

        }
        ((TextView)view.findViewById(R.id.wifi)).setText(wifis.get(i).getSSID());
        ((TextView)view.findViewById(R.id.isconfig)).setText(wifis.get(i).getIsConfig());

        return view;
    }

}
