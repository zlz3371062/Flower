package com.nbhero.equimentSet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nbhero.flower.R;
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
 */

public class GetEquiment extends ZlzRootActivity {

    private ListView lv;
    private TextView refresh;
    private List<Module> mModules;
    private ListAdapter mAdapter;
    private  long lastTime;
    public UdpBroadcast udpBroadcast;
    private Handler mHandler = new Handler();
    WifiReceiver wifiReceiver;
    IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getequimentset);

        udpBroadcast = new UdpBroadcast() {
            @Override
            public void onReceived(List<DatagramPacket> packets) {

                mModules =   decodePackets(packets);
                mAdapter = new ArrayAdapter<Module>(GetEquiment.this, android.R.layout.simple_list_item_1, mModules);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        lv.setAdapter(mAdapter);
                    }
                });

            }
        };


        setpView();
    }

    //处理得到的数据
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
    //布局
    private void setpView() {
        settitle("设备");
        lv= (ListView) findViewById(R.id.lv);
        mAdapter = new ArrayAdapter<Module>(GetEquiment.this, android.R.layout.simple_list_item_1, mModules);

    }

    @Override
    protected void onStart() {
        super.onStart();
        udpBroadcast.open();
        udpBroadcast.send(Constants.CMD_SCAN_MODULES);
    }

    @Override
    protected void onStop() {

        super.onStop();
        udpBroadcast.close();

    }
    public class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)){

            }else if(intent.getAction().equals(
                    WifiManager.NETWORK_STATE_CHANGED_ACTION)){
                 //wifi网络变化
                NetworkInfo info =intent.
                        getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if(info.getState().equals(NetworkInfo.State.DISCONNECTED)){
                    Log.e("zlz","网络断开了");
                    //网络断开了
                }else if(info.getState().equals(NetworkInfo.State.CONNECTED)){
                    //网络连接上了
                    Log.e("zlz","网络连接上了");
                    WifiManager wifiManager = (WifiManager)context.getSystemService(
                            Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    Log.e("zlz","当前wifi名称");
                    //获取当前wifi名称

                }
            }else if(intent.getAction().equals(
                    WifiManager.WIFI_STATE_CHANGED_ACTION)){
                //wifi打开与否
                Log.e("zlz","wifi是否打开");
                int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);

                if(wifistate == WifiManager.WIFI_STATE_DISABLED){
                    Log.e("zlz","网络关闭");
                }
                else if(wifistate == WifiManager.WIFI_STATE_ENABLED){
                    Log.e("zlz","网络打开");
                }
            }
        }
    }



}
