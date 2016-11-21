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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nbhero.adapter.ForaddEquipment;
import com.nbhero.bean.BWifi;
import com.nbhero.bean.BWifiNow;
import com.nbhero.util.ZaboutWIFI;
import com.nbhero.util.ZlzRootActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenglingzhong on 2016/10/27.
 * 发现直接赋值不可以 猜测是在adapter中绑定的数据源是一个地址 是一个具体的对象所以在刷新中往往给数据源赋值是起不了作用的
 */


public class FlowerAddEquipment extends ZlzRootActivity implements View.OnClickListener {

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
        bWifis.clear();
        for ( int i = 0 ;i<temp.size();i++){

            String ssid = temp.get(i).getSSID();
            if(wi.IsConfiguration(wifiConfigList,ssid) == -1){
                temp.get(i).setIsConfig("未配置");

            }else {

                temp.get(i).setIsConfig("已保存");
            }

            bWifis.add(temp.get(i));
        }

       Log.e("zlz",temp.size()+"temp");

        Log.e("zlz",bWifis.size()+"bWifis");
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

    public class NetworkConnectChangedReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {


            String action = intent.getAction();
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                NetworkInfo ni = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (ni.getState() == NetworkInfo.State.CONNECTED && ni.getType() == ConnectivityManager.TYPE_WIFI) {
                    //wifi打开
                    Log.e("zlz","开");
                    isWift = true;
                    aboutView();
                } else if(ni.getState() == NetworkInfo.State.DISCONNECTED && ni.getType() == ConnectivityManager.TYPE_WIFI){
                    isWift = false;
                    aboutView();

                }
            }
        }
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

