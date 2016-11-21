package com.nbhero.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nbhero.flower.R;


/**
 * Created by zhenglingzhong on 2016/11/2.
 */

public class ForNews  extends BaseAdapter {

    //id
    private  int llredId = R.id.inews_ll_red;
    //view
    private LinearLayout llredV;
    private Context con;


    public ForNews(Context pcon){

        this.con = pcon;

    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 10;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){

            view = LayoutInflater.from(con).inflate(R.layout.item_fornews,null);

        }
        if(i > 1){

            llredV  = (LinearLayout) view.findViewById(llredId);
            llredV.setVisibility(View.INVISIBLE);
        }

        return view;
    }
}
