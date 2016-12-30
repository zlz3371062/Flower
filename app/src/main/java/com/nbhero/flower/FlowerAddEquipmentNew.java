package com.nbhero.flower;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nbhero.DIYview.FlowerLoading;
import com.nbhero.adapter.Forequ;
import com.nbhero.bean.BWifiNow;
import com.nbhero.equimentSet.CMDManage;
import com.nbhero.model.Module;
import com.nbhero.util.Constants;
import com.nbhero.util.UdpBroadcast;
import com.nbhero.util.Utils;
import com.nbhero.util.ZlzRootActivity;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenglingzhong on 2016/12/14.
 * 观察发现在 udpsocket实例化过程中 如果添加了端口 会接受到自己发送的数据 猜测是 在通过端口实例化的时候
 * 客户端本身也加入了网络所以广播中 自己会接受到数据，但是无参的时候 自己没有加入到广播中去
 */

public class FlowerAddEquipmentNew extends ZlzRootActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private WifiManager wifiManager;    //网络管理
    private ConnectivityManager connectManager; //管理网络连接
    private NetworkInfo netInfo;                 //网络连接
    private WifiInfo wifiInfo;                   //wifi
    private DhcpInfo dhcpInfo;                   //动态主机配置协议信息对象获取ip等网关信息
    private List<WifiConfiguration> wifiConfigList;
    //数据摸
    BWifiNow WifiNow = new BWifiNow();

    private  LinearLayout btnhomeadd;
    private  TextView wifiName,txtTip,txtfind;
    private  int txtTipid = R.id.addrquipment_txt_tip;

    private UdpBroadcast udpBroadcast;
    private List<Module> mModules;
    private Handler mHandler = new Handler();
    private Forequ mAdapter;
    private ListView lv;
    FlowerLoading.Builder dialog;
    Dialog dialogtip;
    //wifi测试部分
    private TextView txt_wifistate,txt_test;
    private Spinner wifi;
    private EditText et_psw;
    private ArrayAdapter<String> adapter;
    private  List<String> wifilist = new ArrayList<String>();
    private  ArrayList<ScanResult> nearwifi;
    private  String SSID="";

    private  String testPSW = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floweraddequipmentnew);

        udpBroadcast = new UdpBroadcast() {
            @Override
            public void onReceived(List<DatagramPacket> packets) {


                mModules =   decodePackets(packets);
                mAdapter = new Forequ(mModules,FlowerAddEquipmentNew.this);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        if(mModules.size() == 0){

                        }else {

                            lv.setAdapter(mAdapter);
                        }
                    }
                });

            }
        };


        stepView();



    }

    private void stepView() {
        settitle("添加设备");
        back();
        dialog = new FlowerLoading.Builder(this);
        //测试部分
        txt_wifistate = (TextView) findViewById(R.id.txt_wifistate);
        txt_test = (TextView) findViewById(R.id.txt_test);
        et_psw = (EditText) findViewById(R.id.et_psw);
        wifi = (Spinner) findViewById(R.id.sp_wifi);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,wifilist);
        wifi.setAdapter(adapter);
        wifi.setOnItemSelectedListener(this);
        txt_test.setOnClickListener(this);


        btnhomeadd = (LinearLayout) findViewById(R.id.homeadd);
        btnhomeadd.setVisibility(View.VISIBLE);
        btnhomeadd.setOnClickListener(this);
        wifiName = (TextView) findViewById(R.id.addrquipment_txt_wifinow);
        txtTip = (TextView) findViewById(txtTipid);
        txtTip.setText(Html.fromHtml("<font color=red>请先测试希望设备连接的网络<br/>然后请点击右上角\"+\"切换为设备网络，进行配置"));
        lv = (ListView) findViewById(R.id.addequipment_list);
//        addequipment_txt_next
        txtfind = (TextView) findViewById(R.id.addequipment_txt_next);
        txtfind.setOnClickListener(this);
        lv.setOnItemClickListener(this);
    }



    @Override
    public void onClick(View view) {

        if(view == btnhomeadd){

            Intent intent = new Intent();
            intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
            intent.putExtra("extra_prefs_show_button_bar", true);
            intent.putExtra("wifi_enable_next_on_connect", true);

            startActivity(intent);

        }else  if(view==txtfind){

            find();

        }else  if(view == txt_test){

            String psw = et_psw.getText().toString().trim();
            if(psw.equals("")){

                Toast.makeText(this,"请填写wifi密码",Toast.LENGTH_SHORT).show();
                return;
            }
            int code =  AddWifiConfig(nearwifi,SSID,psw);
            if(code == -1){

                txt_wifistate.setText("未通过");
            }else {
                testPSW = psw;
                txt_wifistate.setText("通过");
            }

        }

    }
    //查询设备
    private void find() {

        dialog.setMessage("查询中...").create().show();
        udpBroadcast.send(Constants.CMD_SCAN_MODULES);
    }


    public BWifiNow wifiNow(){

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

    @Override
    protected void onStart() {
        super.onStart();
        udpBroadcast.open();

    }



    @Override
    protected void onStop() {
        super.onStop();
        udpBroadcast.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("zlz","onresume");
        wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        connectManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        dhcpInfo = wifiManager.getDhcpInfo();
        wifiInfo = wifiManager.getConnectionInfo();
        WifiNow =  wifiNow();
        wifiName.setText(wifiNow().getSSID());
        nearwifi =  nearWifi();
        wifilist.clear();
        if(nearWifi().size()>0){

            for (int i = 0;i< nearwifi.size();i++){

                wifilist.add(nearwifi.get(i).SSID);
            }

        }
        adapter.notifyDataSetChanged();


//        ArrayList<ScanResult> nearlist = nearWifi();
//       int returu = AddWifiConfig(nearlist,"nbheyi_704_pc","nbheyi0428");
//
//        Log.e("zlz","---------------------------------------" + returu);
//        for (int i =0 ;i<wifiConfigList.size();i++) {
//
//            String ssid = wifiConfigList.get(i).SSID;
//            int id = wifiConfigList.get(i).networkId;
//            String key = wifiConfigList.get(i).preSharedKey;
////            wifiConfigList.get(i).wepKeys
//            Log.e("zlz", ssid + "------" + id + "-------" + key);
//        }


    }

    private List<Module> decodePackets(List<DatagramPacket> packets) {

        int i = 1;
        Module module;
        List<String> list = new ArrayList<String>();
        List<Module> modules = new ArrayList<Module>();

        DECODE_PACKETS:
        for (DatagramPacket packet : packets) {

            String data = new String(packet.getData(), 0, packet.getLength());
            Log.d("zlz", i + ": " + data);
            if (data.equals(Utils.getCMDScanModules(this))) {
                continue;
            }

            for (String item : list) {
                if (item.equals(data)) {
                    continue DECODE_PACKETS;
                }
            }

            list.add(data);
            if ((module = Utils.decodeBroadcast2Module(data)) != null) {
                module.setId(i);
                modules.add(module);
                i++;
            }
        }

        return modules;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        udpBroadcast.close();
        Intent intent = new Intent(this, CMDManage.class);
        intent.putExtra("IP",mModules.get(i).getIp());
        intent.putExtra("ModuleID",mModules.get(i).getModuleID());
        intent.putExtra("wifipsw",testPSW);
        startActivity(intent);


    }
    public  List<WifiConfiguration> getConfiguration(){

        wifiConfigList = wifiManager.getConfiguredNetworks();//得到配置好的网络信息

        return wifiConfigList;

    }

    public int AddWifiConfig(List<ScanResult> wifiList, String ssid, String pwd){

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
    //附近的wifi
    public  ArrayList<ScanResult> nearWifi(){

        ArrayList<ScanResult>  bWifis = new ArrayList<ScanResult>();

        bWifis = (ArrayList<ScanResult>) wifiManager.getScanResults();

        return   bWifis;
    }
    //下拉选择
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        SSID = wifilist.get(i);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
