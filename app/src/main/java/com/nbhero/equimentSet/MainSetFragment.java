package com.nbhero.equimentSet;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.nbhero.flower.R;

/**
 * @deprecated
 * Created by zhenglingzhong on 2016/12/15.
 * 取消这种呈现方式
 */

public class MainSetFragment extends Fragment implements View.OnClickListener {

    EditText editshow;
    TextView find,mode;
    EditText editinput;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.e("zlz","onCreateView");
        View view = inflater.inflate(R.layout.mainset,null);
        TextView txtmodel = (TextView) view.findViewById(R.id.model);
        editshow = (EditText) view.findViewById(R.id.showback);
        find = (TextView) view.findViewById(R.id.send);
        editinput = (EditText) view.findViewById(R.id.input);
        mode  = (TextView) view.findViewById(R.id.model);
        find.setOnClickListener(this);
        txtmodel.setText("当前为命令模式");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("zlz","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("zlz","onResume");
    }

    //输出
    public synchronized void echo(String text) {

        editshow.append(text);

        editshow.setSelection(editshow.getText().length(), editshow.getText().length());
    }

    @Override
    public void onClick(View view) {

         final CMDManage fa = ((CMDManage) getActivity());

        if(editshow.getText().toString().trim() != ""){

            editshow.setText("");
        }
        editshow.setText("    " + editinput.getText().toString().trim()+"  :>>>>>>>>>>\n");
         new Thread(new Runnable() {
             @Override
             public void run() {

                 fa.mATCommand.send("AT+"+editinput.getText().toString().trim()+"\r");
             }
         }).start();


    }

}
