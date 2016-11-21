package com.nbhero.Home;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nbhero.DIYview.CircleImageView;
import com.nbhero.flower.FlowerEquipment;
import com.nbhero.flower.FlowerMine;
import com.nbhero.flower.FlowerNews;
import com.nbhero.flower.R;
import com.nbhero.mine.MainForMine;

import java.io.File;

/**
 * Created by zhenglingzhong on 2016/10/26.
 */

public class Mine extends Fragment implements View.OnClickListener {
    // id
    private int llIds[] = new int[]{R.id.fragment_mine_btn_about,R.id.fragment_mine_ll_news,R.id.fragment_mine_ll_equipment,R.id.fragment_mine_ll_setting,R.id.fragment_mine_ll_advice,R.id.fragment_mine_ll_about};
    private int txtIds[] = new int[]{R.id.hometitel};

    //VIEW
    CircleImageView circleImageView;
    private  TextView txtV[] = new TextView[8];
    private  LinearLayout llBtns[] = new LinearLayout[7];
    //class
    private   Class<?> airs[] = new Class<?>[]{FlowerMine.class, FlowerNews.class, FlowerEquipment.class};

    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View  view = inflater.inflate(R.layout.mine_mine,container,false);

        circleImageView = (CircleImageView) view.findViewById(R.id.circleImageView);
        abooutView(view);

        IntentFilter intentFilter = new IntentFilter("COM.ZLZ");
        s a = new s();
        getActivity().registerReceiver(a,intentFilter);


        String dir2 = getActivity().getExternalCacheDir().toString() + "/head/tempmin.jpg";
        File file = new File(dir2);


        if(file.exists()){
            Log.e("zlz","true");
            Bitmap bit = BitmapFactory.decodeFile(dir2);
            circleImageView.setImageBitmap(bit);
        }else {
            Log.e("zlz","false");
        }

        return view;
    }

    private void abooutView(View v){
        for (int i = 0; i<llIds.length;i++ ){
            llBtns[i] = (LinearLayout) v.findViewById(llIds[i]);
            llBtns[i].setOnClickListener(this);

        }

    }


    @Override
    public void onClick(View view) {

        for (int i = 0 ; i<llIds.length;i++){

            if(view.getId() == llIds[i]){
//
//                Intent go = new Intent(getActivity(), airs[i]);
//                getActivity().startActivity(go);
                Intent go = new Intent(getActivity(), MainForMine.class);
                go.putExtra("id",i);
                getActivity().startActivity(go);

                break;
            }

        }


    }


   private class s extends BroadcastReceiver{


        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals("COM.ZLZ")){

                String dir = getActivity().getExternalCacheDir().toString() + "/head";
                String dir2 = getActivity().getExternalCacheDir().toString() + "/head/tempmin.jpg";
                File file = new File(dir);

                if(file.exists()){

                    Bitmap bit = BitmapFactory.decodeFile(dir2);
                    circleImageView.setImageBitmap(bit);
                }

            }
        }
    }

}
