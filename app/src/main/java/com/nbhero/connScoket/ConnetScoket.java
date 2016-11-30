package com.nbhero.connScoket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nbhero.flower.R;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by zhenglingzhong on 2016/11/29.
 */

public class ConnetScoket extends Activity implements View.OnClickListener {
    Socket scoket;
    boolean isconn = false;
    private EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connscoket);
        findViewById(R.id.connect).setOnClickListener(this);
        et = (EditText) findViewById(R.id.et);
        findViewById(R.id.et).setOnClickListener(this);
        findViewById(R.id.send).setOnClickListener(this);


    }

    //连接
    private  void connect(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    scoket = new Socket("192.168.0.109",9999);
//                    scoket = new Socket("10.10.100.254",8899);
                    isconn = scoket.isConnected();
                    DataInputStream reader;
                    reader = new DataInputStream( scoket.getInputStream());
                    String msg = reader.readUTF();
                    Log.e("zlz",msg + "方法1");
                    InputStream in = scoket.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
//                    Bu
                    StringBuilder sb = null;
                    Log.e("zlz","方法2-sb");
                    String line = "";
                    Log.e("zlz", "方法2");
                    while ((line = br.readLine()) != null){
                        Log.e("zlz","方法2-while");
                        sb.append(line);
                        Log.e("zlz",line + "方法2");
                    }

                    if(isconn){

                        myhandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ConnetScoket.this,"连接成功",Toast.LENGTH_SHORT).show();
                            }
                        });



                    }else{

                        myhandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ConnetScoket.this,"连接失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    //send
    private  void sendMessage(){

        final String Message = et.getText().toString().trim();

        new Thread(new Runnable() {
            @Override
            public void run() {


                DataOutputStream writer = null;
                InputStream in = null;
                try {
                    writer = new DataOutputStream(scoket.getOutputStream());
                    writer.writeUTF("+++");
                    DataInputStream reader;
                    reader = new DataInputStream( scoket.getInputStream());
                    String msg = reader.readUTF();
                    Log.e("zlz",msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.connect:

                connect();

                break;
            case R.id.et:
                break;
            case R.id.send:

                if(isconn){
                    Log.e("zlz","liangji e");
                    sendMessage();}
                else {
                    Log.e("zlz","未连接");
                    Toast.makeText(this,"连接已断开，请重新连接。",Toast.LENGTH_SHORT).show();

                }
                break;

        }
    }

    private Handler myhandler = new Handler();

}
