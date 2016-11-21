package com.nbhero.equipment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nbhero.DIYview.AboutGas;
import com.nbhero.DIYview.ProView;
import com.nbhero.DIYview.SwitchOpen;
import com.nbhero.flower.R;
import com.nbhero.util.ZlzRootActivity;

/**
 * Created by zhenglingzhong on 2016/11/3.
 */

public class MainForEquipment extends ZlzRootActivity implements View.OnClickListener ,SwitchOpen.SwitchListen{

    //id
    private  int txtid[] = new int[]{R.id.equipment_txt_time,R.id.equipment_txt_hold,R.id.equipment_txt_equipment};
    private  int llmyproid = R.id.equipment_main_ll_pro;
    private  int txtswitchstateid = R.id.equipment_txt_state;

    //v
    private TextView txtv[] = new TextView[3];
    private  TextView switchstate;
    private  int where = -1;
    private  int stype = -1;
    private ImageView switchimg;
    private  String title[] = new String[]{"大厅","餐厅"};
    private LinearLayout mypro;

    private AboutGas aboutGas;
    private ProView proView;

    Dialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment_main);
        aboutView();
    }

    private  void aboutView(){
        SwitchOpen switchOpen = (SwitchOpen) findViewById(R.id.switchzlz);
        switchOpen.setColor(getResources().getColor(R.color.SwitchColor));
        switchOpen.setSwitchListen(this);
        switchstate = (TextView) findViewById(R.id.equipment_txt_state);
        aboutGas = (AboutGas) findViewById(R.id.equipment_main_ll_pro);
        switchstate = (TextView) findViewById(txtswitchstateid);
        aboutGas.setGas(0.4f);
        proView= (ProView) findViewById(R.id.zlz);
        proView.set(150);
        proView.setSpeed(2);
        back();
        where = getIntent().getIntExtra("where",-1);
        stype = getIntent().getIntExtra("stype",-1);
        settitle(title[where]);

        for (int i = 0 ;i <txtid.length;i++){

            txtv[i] = (TextView) findViewById(txtid[i]);
            txtv[i].setOnClickListener(this);

        }

        showLog();

    }

    @Override
    public void onClick(View view) {


        for (int i = 0; i < txtid.length; i++) {

            if (view.getId() == txtid[i]) {

                Intent go = new Intent(this, AboutEquipment.class);
                go.putExtra("where", i);
                startActivity(go);

                break;
            }

        }
    }


    private void  showLog(){

        View v = LayoutInflater.from(this).inflate(R.layout.dialog_forrent,null);
        TextView TXT = (TextView) v.findViewById(R.id.tip);
        TXT.setText(Html.fromHtml("租约即将到期<font color=red>是否续约</font>？"));
        dialog = new Dialog(this);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setBackgroundDrawableResource(R.drawable.back_dialog);
        dialogWindow.requestFeature(Window.FEATURE_NO_TITLE);
        dialogWindow.setContentView(v);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getResources().getDisplayMetrics(); // 获取屏幕宽、高度
        dialog.show();

    }

    @Override
    public void open() {
//        Toast.makeText(this,"开启",Toast.LENGTH_LONG).show();
        switchstate.setText("关闭喷香机");
        Log.e("zlz","开启");
    }


    @Override
    public void close() {
//        Toast.makeText(this,"关闭",Toast.LENGTH_LONG).show();
        switchstate.setText("打开喷香机");
        Log.e("zlz","关闭");
    }

}
