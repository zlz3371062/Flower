package com.nbhero.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nbhero.flower.R;
import com.nbhero.model.Module;

import java.util.List;

/**
 * Created by zhenglingzhong on 2016/12/15.
 */

public class Forequ extends BaseAdapter {

    private List<Module> module;
    private  Context con;

    public Forequ(List<Module> module, Context con) {

        this.module = module;
        this.con = con;
    }

    @Override
    public int getCount() {
        return module.size();
    }

    @Override
    public Object getItem(int i) {
        return module.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = LayoutInflater.from(con).inflate(R.layout.item_equ,null);
        TextView txt = (TextView) v.findViewById(R.id.txt);
        txt.setText(module.toString());

        return v;
    }
}
