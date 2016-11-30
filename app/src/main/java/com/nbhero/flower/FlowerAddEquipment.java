package com.nbhero.flower;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nbhero.adapter.ForaddEquipment;
import com.nbhero.bean.BWifi;
import com.nbhero.bean.BWifiNow;
import com.nbhero.connScoket.ConnetScoket;
import com.nbhero.util.ZaboutWIFI;
import com.nbhero.util.ZlzRootActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenglingzhong on 2016/10/27.
 * 发现直接赋值不可以 猜测是在adapter中绑定的数据源是一个地址 是一个具体的对象所以在刷新中往往给数据源赋值是起不了作用的
 */


public class FlowerAddEquipment extends ZlzRootActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    //wifi管理
    private WifiManager wifiManager;    //网络管理
    private ConnectivityManager connectManager; //管理网络连接
    private NetworkInfo netInfo;                 //网络连接
    private WifiInfo wifiInfo;                   //wifi
    private DhcpInfo dhcpInfo;                   //动态主机配置协议信息对象获取ip等网关信息
    private ArrayList<ScanResult> list;
    private List<WifiConfiguration> wifiConfigList;
    private  boolean isWift = true;

    private ZaboutWIFI wi;
    private List<BWifi> bWifis = new ArrayList<>();
    private ListView ls;
    private  BWifiNow bWifiNow;
    private ForaddEquipment ad;
    private  int seestype = 0;
    private ImageView seeimg;
    private EditText wifipassword;
    int  code = -1; //设置wifi结果

    private  int txtTipid = R.id.addrquipment_txt_tip;

    private TextView txtwifiNow,txtTip,next;

    //广播
    NetworkConnectChangedReceiver networkConnectChangedReceiver;
    IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBarColor(R.color.headcolor);
        setContentView(R.layout.floweraddequipment);
        wifiInit();
        aboutView();

    }


    private void wifiInit(){
        settitle("添加设备");
        back();
        wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        connectManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        dhcpInfo = wifiManager.getDhcpInfo();
        wifiInfo = wifiManager.getConnectionInfo();
        filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        networkConnectChangedReceiver = new NetworkConnectChangedReceiver();
        registerReceiver(networkConnectChangedReceiver, filter);
        ls = (ListView) findViewById(R.id.addequipment_list);
        ls.setOnItemClickListener(this);
        ad = new ForaddEquipment(this, bWifis);
        seeimg= (ImageView) findViewById(R.id.addequipment_img_see);
        wifipassword = (EditText) findViewById(R.id.addequipment_et_wifipassword);
        seeimg.setOnClickListener(this);
        txtTip = (TextView) findViewById(txtTipid);
        txtwifiNow = (TextView) findViewById(R.id.addrquipment_txt_wifinow);
        next = (TextView) findViewById(R.id.addequipment_txt_next);



    }

    private void aboutView() {

        txtTip.setText(Html.fromHtml("设备暂时无法使用5GWI-FI配置<br/>如果使用的是5G WLAN，请切换为他WLAN"));
        ls.setAdapter(ad);
        next.setOnClickListener(this);
        if(!wifiManager.isWifiEnabled()){
            isWift = false;
            Dialog dialog = new AlertDialog.Builder(this).setTitle("提示").setMessage("需wifi支持需要打开wifi吗？").setPositiveButton("打开", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    wifiManager.setWifiEnabled(true);
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).create();

            dialog.show();

        }
        if(isWift){

            aboutWifi();
            txtwifiNow.setText(bWifiNow.getSSID());
            ls.setAdapter(ad);


        }else {
            txtwifiNow.setText("无网络");
            ls.setAdapter(null);
        }

    }

    private  void aboutWifi(){
        wi = new ZaboutWIFI(wifiManager,connectManager);
        bWifiNow = wi.wifiNow();
        wifiConfigList = wi.getConfiguration();
        List<BWifi> temp = wi.nearWifi();
        Log.e("zlz",temp.size()+"tiao");
        for ( int i = 0 ;i<temp.size();i++){
            Log.e("zlz",temp.get(i).getSSID());
        }
        bWifis.clear();
        for ( int i = 0 ;i<temp.size();i++){

            String ssid = temp.get(i).getSSID();
            int code = wi.IsConfiguration(wifiConfigList,ssid);
            if(code == -1){
                temp.get(i).setIsConfig("未配置");
                Log.e("zlz","运行次数1");
            }else{
                temp.get(i).setIsConfig("已保存");
                temp.get(i).setNetworkId(code);
            }

            bWifis.add(temp.get(i));
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.addequipment_img_see:
                if(seestype == 0){
                    seestype = 1;
                    wifipassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                }else{
                    seestype = 0;
                    wifipassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                break;
            case R.id.addequipment_txt_next:
                Intent go = new Intent(this,FlowerEquipmentSetting.class);
                startActivity(go);

                break;

        }

    }

    public void zlzG() {
    }


    //点击
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        final String ssid =  bWifis.get(i).getSSID();
        String config = bWifis.get(i).getIsConfig();

        if(config.equals("未配置")){

            configWifi(ssid,i);



            return;

        }else {


            Log.e("zlz",bWifis.get(i).getSSID());
            Log.e("zlz",bWifis.get(i).getNetworkId()+"连接");
            boolean issuccess = wi.ConnectWifi(bWifis.get(i).getNetworkId());
            if(issuccess){
                Toast.makeText(this,"连接wifi成功",Toast.LENGTH_SHORT).show();
                Intent go = new Intent(this, ConnetScoket.class);
                startActivity(go);

            }


        }
    }

    public class NetworkConnectChangedReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {


            String action = intent.getAction();
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                NetworkInfo ni = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (ni.getState() == NetworkInfo.State.CONNECTED && ni.getType() == ConnectivityManager.TYPE_WIFI) {
                    //wifi打开
                    isWift = true;
                    aboutView();
                } else if(ni.getState() == NetworkInfo.State.DISCONNECTED && ni.getType() == ConnectivityManager.TYPE_WIFI){
                    isWift = false;
                    aboutView();

                }
            }
        }
    }

    //配制wifi
    private  void configWifi(final String pssid,final int pi){


        code = -1 ;

        View v  =   LayoutInflater.from(this).inflate(R.layout.configwifi,null);
        final Dialog dialog = new AlertDialog.Builder(this).setView(v).create();
        dialog.show();

        final EditText et = (EditText) v.findViewById(R.id.wifipassword);

        dialog.show();
        v.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View view) {

                ArrayList<ScanResult>  list = (ArrayList<ScanResult>) wifiManager.getScanResults();
                String  password = et.getText().toString().trim();

                code = wi.AddWifiConfig(list,pssid,password);

                if(code == -1){

                    Toast.makeText(FlowerAddEquipment.this,"配制wifi失败",Toast.LENGTH_SHORT).show();
                }else {

                    bWifis.get(pi).setIsConfig("已保存");
                    bWifis.get(pi).setSSID(pssid);
                    bWifis.get(pi).setNetworkId(code);
                    ad.notifyDataSetChanged();
                    Toast.makeText(FlowerAddEquipment.this,"配制wifi成功",Toast.LENGTH_SHORT).show();
                }


            }
        });

        v.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });

    }
    @Override
    protected void onStop() {
        super.unregisterReceiver(networkConnectChangedReceiver);
        super.onStop();

    }

    @Override
    protected void onResume() {
//        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        networkConnectChangedReceiver = new NetworkConnectChangedReceiver();
        registerReceiver(networkConnectChangedReceiver, filter);
        super.onResume();
    }
}

