package com.nbhero.presenter;

import com.nbhero.InterFace.IMainManger;
import com.nbhero.model.MMainModel;

/**
 * Created by zhenglingzhong on 2016/11/10.
 */

public class PMainManger {

    protected IMainManger listerner;
    protected MMainModel model;



    public void setModel(MMainModel model){

        this.model = model;

    }



    public void setSelfListerner(IMainManger listerner){

        this.listerner = listerner;


    }



}
