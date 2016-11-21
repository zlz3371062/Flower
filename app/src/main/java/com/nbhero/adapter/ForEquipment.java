package com.nbhero.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nbhero.equipment.MainForEquipment;
import com.nbhero.flower.R;

/**
 * Created by zhenglingzhong on 2016/10/27.
 */

public class ForEquipment extends BaseAdapter{


    private  Context con;
    private Intent go;
    //id
    private  int logoid = R.id.equipment_list_img_logo;
    private  int nameid = R.id.equipment_list_txt_name;
    private  int halllogoid = R.id.equipment_list_img_hall;
    private  int restaurantlogoid = R.id.equipment_list_img_restaurant;
    private  int hallstypeid= R.id.equipment_list_hall_txt_stype;
    private  int restaurantstypeid = R.id.equipment_list_restaurant_txt_stype;

    //view
    private ImageView logo,halllogo,restaurantlogo;
    private TextView txtName,txtHallStype,txtrestaurantStype;

    //stype
    private  String stype[] = new String[]{"自运营","租用"};

    public  ForEquipment(Context pcon){

        this.con = pcon;

    }


    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return 10;
    }

    @Override
    public long getItemId(int i) {
        return 10;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){

            view = LayoutInflater.from(con).inflate(R.layout.item_forequipment,null);

        }

        LinearLayout lldating = (LinearLayout) view.findViewById(R.id.equipmentlist_ll_dating);
        LinearLayout llcanting = (LinearLayout) view.findViewById(R.id.equipmentlist_ll_canting);
        lldating.setOnClickListener(myclick);
        llcanting.setOnClickListener(myclick);
        logo = (ImageView) view.findViewById(logoid);
        halllogo = (ImageView) view.findViewById(halllogoid);
        restaurantlogo = (ImageView) view.findViewById(restaurantlogoid);
        halllogo.setImageResource(R.drawable.equipmenthall);
        restaurantlogo.setImageResource(R.drawable.lessgas);
        txtHallStype = (TextView) view.findViewById(hallstypeid);
        txtHallStype.setText(stype[1]);
        txtrestaurantStype = (TextView) view.findViewById(restaurantstypeid);
        txtrestaurantStype.setText(stype[0]);

        return view;
    }


    private View.OnClickListener myclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            go = new Intent(con, MainForEquipment.class);
            if(view.getId() == R.id.equipmentlist_ll_dating){

                go.putExtra("where",0);
                go.putExtra("stype",0);

            }else if( view.getId() == R.id.equipmentlist_ll_canting){

                go.putExtra("where",1);
                go.putExtra("stype",1);
            }
            con.startActivity(go);


        }

    };

}
