package com.nbhero.InterFace;

import com.nbhero.bean.BWifi;
import com.nbhero.bean.BWifiNow;

import java.util.List;

/**
 * Created by zhenglingzhong on 2016/10/27.
 */

public interface IZaboutWifi extends  IMainManger{


    public  void makeWifi(BWifiNow pbWifiNow,List<BWifi> pbWifis);



}
