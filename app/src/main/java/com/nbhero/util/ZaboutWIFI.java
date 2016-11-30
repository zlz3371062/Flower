package com.nbhero.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.nbhero.InterFace.IZaboutWifi;
import com.nbhero.bean.BWifi;
import com.nbhero.bean.BWifiNow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenglingzhong on 2016/10/27.
 */

public class ZaboutWIFI {

    //Activity ac;
    //wifi
    public WifiManager wifiManager;    //网络管理
    public ConnectivityManager connectManager; //管理网络连接
    public NetworkInfo netInfo;                 //网络连接
    public WifiInfo wifiInfo;                   //wifi
    public DhcpInfo dhcpInfo;                   //动态主机配置协议信息对象获取ip等网关信息
    ArrayList<ScanResult> list;


    private IZaboutWifi iZaboutWifi;
    private List<BWifi> bWifis = new ArrayList<BWifi>();
    private List<WifiConfiguration> wifiConfigList;

    public  ZaboutWIFI(WifiManager wifiManager,ConnectivityManager connectManager){

        this.wifiManager  = wifiManager;
        this.connectManager = connectManager;
        netInfo =  connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        dhcpInfo = wifiManager.getDhcpInfo();
        wifiInfo = wifiManager.getConnectionInfo();
    }

    public  List<BWifi> nearWifi(){
        List<BWifi> bWifis = new ArrayList<BWifi>();
        list = (ArrayList<ScanResult>) wifiManager.getScanResults();

        for (int i =0 ; i<list.size();i++){
            BWifi bWifi = new BWifi();
            bWifi.setSSID(list.get(i).SSID);
            bWifis.add(bWifi);
        }
        return   bWifis;
    }
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
    public int IsConfiguration(List<WifiConfiguration> list,String SSID){

        for(int i = 0; i < list.size(); i++){
            String name = list.get(i).SSID;
            name = name.substring(1, name.length() - 1);
            if(name.equals(SSID)){//地址相同
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
