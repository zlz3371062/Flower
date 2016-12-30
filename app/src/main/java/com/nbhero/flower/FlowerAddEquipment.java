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
import com.nbhero.equimentSet.GetEquiment;
import com.nbhero.util.ZlzRootActivity;

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
    private ArrayList<ScanResult> nearlist;
    private List<WifiConfiguration> wifiConfigList;
    private  boolean isWift = true;

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
        ls = (ListView) findViewById(R.id.addequipment_list);
        ls.setOnItemClickListener(this);
        ad = new ForaddEquipment(this, bWifis);
        txtTip = (TextView) findViewById(txtTipid);
        txtwifiNow = (TextView) findViewById(R.id.addrquipment_txt_wifinow);
        next = (TextView) findViewById(R.id.addequipment_txt_next);


    }

    private void aboutView() {

        txtTip.setText(Html.fromHtml("请先测试希望设备连接的可用网络<br/>然后，点击右上角 + 选择设备网络进行查找设备。"));
        ls.setAdapter(ad);
        next.setOnClickListener(this);

    }

    private  void aboutWifi(){
        //获取wifi中已经配置的wifi列表
        wifiConfigList = getConfiguration();
        //获取当前连接wifi
        bWifiNow = wifiNow();
        //获取附近wifi列表
        nearlist = nearWifi();
        //整合数据获取lisview数据源

        makeData();


    }
    //整合listview需要的数据
    private void makeData() {
        bWifis.clear();
        for (int i = 0 ; i < nearlist.size(); i++){
            String ssid = nearlist.get(i).SSID;
            String bssid = nearlist.get(i).BSSID;
            BWifi wifi =  new BWifi();
            int  code  = IsConfiguration(wifiConfigList,ssid,bssid);
            wifi.setSSID(ssid);
            wifi.setBssid(bssid);
            Log.e("zlz",code + "");
            if (code == -1  || code == 0){

                wifi.setIsConfig("未配置");
                wifi.setNetworkId(code);

            }else {
                wifi.setIsConfig("已保存");
                wifi.setNetworkId(code);
            }

            bWifis.add(wifi);
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){


            case R.id.addequipment_txt_next:

                Intent go = new Intent(this,FlowerEquipmentSetting.class);
                startActivity(go);

                break;

        }

    }

    public void zlzG() {


    }


    //wifi点击
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        final String ssid =  bWifis.get(i).getSSID();
        String config = bWifis.get(i).getIsConfig();

        if(config.equals("未配置")){

//            configWifi(ssid,i);


            return;

        }else {

            if(ConnectWifi(bWifis.get(i).getNetworkId())){

                Toast.makeText(this,"连接wifi成功",Toast.LENGTH_SHORT).show();
                Intent go = new Intent(this,GetEquiment.class);
                go.putExtra("SSID",ssid);
                startActivity(go);

            }

        }
    }




    //查找附近的wifi
    public  ArrayList<ScanResult> nearWifi(){

        ArrayList<ScanResult>  bWifis = new ArrayList<ScanResult>();

        bWifis = (ArrayList<ScanResult>) wifiManager.getScanResults();

        return   bWifis;
    }


    //正在连接 的 wifi
    public  BWifiNow wifiNow(){

        BWifiNow bWifiNow = new BWifiNow();
        bWifiNow.setSSID(wifiInfo.getSSID());
        bWifiNow.setIpAddress(FormatString(dhcpInfo.ipAddress));
        bWifiNow.setNetmask(FormatString(dhcpInfo.netmask));
        bWifiNow.setGateway(FormatString(dhcpInfo.gateway));
        bWifiNow.setDns1(FormatString(dhcpInfo.dns1));
        return  bWifiNow;
    }


    public String FormatString(int value){
        String strValue="";
        byte[] ary = intToByteArray(value);
        for(int i=ary.length-1;i>=0;i--){
            strValue += (ary[i] & 0xFF);
            if(i>0){
                strValue+=".";
            }
        }
        return strValue;
    }
    public  byte[] intToByteArray(int value){
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++){
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return b;
    }
    //得到Wifi配置好的信息
    public  List<WifiConfiguration> getConfiguration(){

        wifiConfigList = wifiManager.getConfiguredNetworks();//得到配置好的网络信息

        return wifiConfigList;

    }

    //判定指定WIFI是否已经配置好,依据WIFI的地址BSSID,返回NetId
    public int IsConfiguration(List<WifiConfiguration> list,String SSID,String BSSID){
        Log.e("zlz",list.size() + "条");
        for(int i = 0; i < list.size(); i++){
            String name = list.get(i).SSID;

            name = name.substring(1, name.length() - 1);
            if(name.equals(SSID)){//地址相同

                Log.e("zlz",list.get(i).networkId + "");
//
                return list.get(i).networkId;
            }
        }

        return -1;
    }

    //添加指定WIFI的配置信息,原列表不存在此SSID
    public int AddWifiConfig(List<ScanResult> wifiList,String ssid,String pwd){

        int wifiId = -1;
        for(int i = 0;i < wifiList.size(); i++){
            ScanResult wifi = wifiList.get(i);
            if(wifi.SSID.equals(ssid)){
                WifiConfiguration wifiCong = new WifiConfiguration();
                wifiCong.SSID = "\""+wifi.SSID+"\"";//\"转义字符，代表"
                wifiCong.preSharedKey = "\""+pwd+"\"";//WPA-PSK密码
                wifiCong.hiddenSSID = false;
                wifiCong.status = WifiConfiguration.Status.ENABLED;
                wifiId = wifiManager.addNetwork(wifiCong);//将配置好的特定WIFI密码信息添加,添加完成后默认是不激活状态，成功返回ID，否则为-1
                if(wifiId != -1){

                    return wifiId;
                }
            }
        }

        return wifiId;

    }

    //连接指定Id的WIFI
    public boolean ConnectWifi(int wifiId){

        for(int i = 0; i < wifiConfigList.size(); i++){
            WifiConfiguration wifi = wifiConfigList.get(i);
            if(wifi.networkId == wifiId){

                while(!(wifiManager.enableNetwork(wifiId, true))){//激活该Id，建立连接
                    //status:0--已经连接，1--不可连接，2--可以连接
                    Log.i("ConnectWifi",String.valueOf(wifiConfigList.get(wifiId).status));
                }

                return true;
            }
        }
        return false;
    }

}

