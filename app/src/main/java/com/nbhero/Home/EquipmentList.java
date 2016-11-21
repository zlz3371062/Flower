package com.nbhero.Home;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nbhero.adapter.ForEquipment;
import com.nbhero.flower.FlowerAddEquipment;
import com.nbhero.flower.R;

/**
 * Created by zhenglingzhong on 2016/10/26.
 */

public class EquipmentList extends Fragment implements View.OnClickListener ,SwipeRefreshLayout.OnRefreshListener {

    private   ListView ls;
    private  ForEquipment ad;
    private  SwipeRefreshLayout  mSwipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View  view = inflater.inflate(R.layout.fragment_equipment,container,false);

        about(view);

        return view;
    }

    private void about(View v){

        mSwipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.equipment_sw_pull);
        mSwipeLayout.setOnRefreshListener(this);
        ad = new ForEquipment(getActivity());
        ls = (ListView) v.findViewById(R.id.equipment_list);
        ls.setAdapter(ad);
//        btnhomeadd.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {

        Intent go = new Intent(getActivity(), FlowerAddEquipment.class);
        getActivity().startActivity(go);

    }

    @Override
    public void onRefresh() {

        for (int i=5;i<10000;i++){


        }
        mSwipeLayout.setRefreshing(false);

    }
}
