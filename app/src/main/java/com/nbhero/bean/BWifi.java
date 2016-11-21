package com.nbhero.bean;

/**
 * Created by zhenglingzhong on 2016/10/27.
 */

public class BWifi {

//    SSID: nbheyi703_Phone, BSSID: b8:a3:86:8a:f0:0d, capabilities: [WPA-PSK-TKIP][WPA2-PSK-TKIP][ESS], level: -64, frequency: 2462, timestamp: 539154439932, distance: ?(cm), distanceSd: ?(cm), wpsState :null, wpsDeviceName :null


    private  String SSID;
    private  String isConfig;

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getIsConfig() {
        return isConfig;
    }

    public void setIsConfig(String isConfig) {
        this.isConfig = isConfig;
    }
}
