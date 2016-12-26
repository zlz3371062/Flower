package com.nbhero.equimentSet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nbhero.DIYview.FlowerLoading;
import com.nbhero.flower.R;
import com.nbhero.util.NetworkProtocol;
import com.nbhero.util.Utils;
import com.nbhero.util.ZlzRootActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenglingzhong on 2016/12/15.
 */

public class CMDManage extends ZlzRootActivity implements ATCommandListener,TransparentTransmissionListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    public String moudelID;
    public TextView txtip,txtmodule,txtrefersh,txtSet;
    private EditText et_ip,et_sort;

    Spinner spinner;

    public FlowerLoading.Builder dialog;

    public String mIP;

    ArrayAdapter<String> adapter = null;
    List<String> ap = new ArrayList<String>();

    public static final String sendForAp = "AT+WSCAN\r";

    public int mUdpPort;


    private String strAP="";
    private  String strip ="";
    private  String strsort ="";

    public UdpUnicast mUdpUnicast;
    public ATCommand mATCommand;
    public TransparentTransmission mTTransmission;


    //状态
    private  boolean isTurn = false;
    private  boolean isSend = false;
    private  int state = 0; //0 无模式 1 命令模式 2。透传模式

    private String enterCMDModeResponse;

    private  String tryEnterCMDModeResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makecmd);
        getData();
        stepView();
    }
    //得到数据
    private void getData() {

        mIP = getIntent().getStringExtra("IP");
        moudelID = getIntent().getStringExtra("ModuleID");

        mUdpPort = Utils.getUdpPort(this);

        mUdpUnicast = new UdpUnicast(mIP, mUdpPort);
        mUdpUnicast.open();
        mATCommand = new ATCommand(mUdpUnicast);
        mATCommand.setListener(this);
        mTTransmission = new TransparentTransmission();
        mTTransmission.setListener(this);
    }

    //设置布局
    private void stepView() {
        settitle("配置");
        back();
        dialog = new FlowerLoading.Builder(this);
        txtip = (TextView) findViewById(R.id.txt_ip);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        txtmodule = (TextView) findViewById(R.id.txt_id);
        txtrefersh = (TextView) findViewById(R.id.txt_refersh);
        txtSet = (TextView) findViewById(R.id.txt_set);
        et_ip = (EditText) findViewById(R.id.et_ip);
        et_sort  = (EditText) findViewById(R.id.et_sort);
        txtSet.setOnClickListener(this);
        txtrefersh.setOnClickListener(this);
        txtip.setText("I  P:     " + mIP);
        txtmodule.setText("设备:     " + moudelID);
        enterCMDMode();


    }

    //一键配置
    private  void set(){



    }


    //    //进入命令模式
    private void enterCMDMode() {

        if(!isSend && !isTurn){

            dialog.setMessage("初始化中...").create().show();
            isTurn = true;
            if(state != 1){

                new Thread(){
                    @Override
                    public void run() {
                        mATCommand.enterCMDMode();
                    }
                }.start();
            }

        } else {

            Toast.makeText(this,"正在处理一些东西",Toast.LENGTH_SHORT).show();

        }

    }

    //    //退出命令模式
    private void enterTransparentTransmissionMode() {


    }



    @Override
    protected void onPause() {
        mUdpUnicast.close();
        super.onPause();

    }

    @Override
    protected void onStop() {
        if(dialog != null){

            dialog = null;
        }

        super.onStop();
    }

    //tcp模式接受到的数据
    @Override
    public void onReceive(byte[] data, int length) {


//        String str =  ((MainSetFragment)mainSetFragment).editshow.getText().toString().trim();
//        byte[] d =  str.getBytes();
//        if(d.length>100){
//            ((MainSetFragment)mainSetFragment).editshow.setText("");
//        }
//
//        ((MainSetFragment)mainSetFragment).echo(Utils.gernerateEchoText(Utils.RESPONSE_TTS, new String(data, 0, length)));
    }

    @Override
    public void onOpen(boolean success) {

        if (success) {
//            resultCode = RESULT_TTS_OK;
//            showMode(MODE_TTS);
        }else {
//            resultCode = RESULT_TTS_NOK;
        }
    }

    //重启设备
    private void reset() {

        if (!isTurn) {

            isTurn = true;

            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("确定重启设备吗？")
                    .setPositiveButton("是的", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            mATCommand.resetTimes();
                            new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    mATCommand.reset();
                                }
                            }).start();
                        }
                    })
                    .setNegativeButton("不是", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            isTurn = false;
                        }
                    })
                    .create().show();
        }
    }





    private void waitforcmd(long wait) {

        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < wait) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }
    }



    private void waitReceiveResponse(long wait, String response) {

        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < wait  && response == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }
    }
    //进入命令模式结果
    @Override
    public void onEnterCMDMode(boolean success) {
        if(dialog != null){

            dialog.dismiss();
        }
        if(success){

            state = 1;

        }
        isTurn = false;
    }
    //退出命令模式结果
    @Override
    public void onExitCMDMode(boolean success, NetworkProtocol protocol) {

    }

    @Override
    public void onSendFile(boolean success) {

    }

    @Override
    public void onReload(boolean success) {

    }

    //重启结果
    @Override
    public void onReset(boolean success) {
        if(success){

            Log.e("zlz","重启成功");
        }else {
            Log.e("zlz","重启失败");
        }


    }
    //发送反回结果
    @Override
    public void onResponse(String response, String text) {



        if(text.equals("AT+WSCAN\r") && (!response.contains("SSID") && response.contains(","))){
            Log.e("zlz","扫描结果");
            ap.add(getSSID(response));
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ap);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.spinner.setAdapter(adapter);
        }else  if(text.equals("AT+WMODE=APSTA\r")){
            Log.e("zlz","设置模式结果" + response);
            if(response.equals("+ok\r\n\r\n")){
                Log.e("zlz","设置模式结果成功");
                APlink();

            }
        }else  if(text.equals("AT+WSSSID=" + strAP +"\r")){
            Log.e("zlz","设置网络结果" + response);
            if(response.equals("+ok\r\n\r\n")){
                Log.e("zlz","设置网络结果成功");
                setServer();

            }

        }else  if(text.equals("AT+NETP=TCP,CLIENT,"+strsort + "," + strip+"\r")){
            Log.e("zlz","设置服务器结果" + response);

            if(response.equals("+ok\r\n\r\n")){
                reset();

            }
        }


    }

    @Override
    public void onResponseOfSendFile(String response) {

    }


    @Override
    public void onClick(View view) {

        if(view == txtrefersh){

            if(state!=1){

                Toast.makeText(this,"当前不是命令模式",Toast.LENGTH_SHORT).show();

            }else {


                getAP();
            }

        }else if(view == txtSet){
            strip = et_ip.getText().toString().trim();
            strsort = et_sort.getText().toString().trim();
            if(strAP.equals("")){

                Toast.makeText(this,"请选择服务器所在网络",Toast.LENGTH_SHORT).show();
                return;
            }else  if(strip.equals("")){
                Toast.makeText(this,"请输入服务器的地址",Toast.LENGTH_SHORT).show();
                return;

            }else if(strsort.equals("")){

                Toast.makeText(this,"请选择服务器端口",Toast.LENGTH_SHORT).show();
                return;
            }

            setOne();
        }


    }

    private  void setOne(){

        new Thread(){
            @Override
            public void run() {

                mATCommand.send("AT+WMODE=APSTA\r");

            }
        }.start();


    }

    private  void  APlink(){

        new Thread(){
            @Override
            public void run() {

                mATCommand.send("AT+WSSSID=" + strAP +"\r");

            }
        }.start();

    }

    private  void setServer(){
        new Thread(){
            @Override
            public void run() {

                mATCommand.send("AT+NETP=TCP,CLIENT,"+strsort + "," + strip+"\r");

            }

        }.start();


    }

    private  void getAP(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                mATCommand.send("AT+WSCAN\r");

            }
        }).start();
    }
    public   String getSSID(String str){

        String ssid = null;
        String data[] = str.split(",");
        ssid  = data[1];

        return ssid;

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        strAP = ap.get(i);
        Log.e("zlz","ap=" + strAP);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }
}
