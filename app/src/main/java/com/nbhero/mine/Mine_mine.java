package com.nbhero.mine;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nbhero.DIYview.CircleImageView;
import com.nbhero.flower.R;
import com.nbhero.util.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by zhenglingzhong on 2016/11/2.
 */

public class Mine_mine extends Fragment implements View.OnClickListener{

    private final  int id_ll_img = R.id.mine_ll_img;
    private final int id_ll_name = R.id.mine_ll_name;
    private final int id_ll_sex = R.id.mine_ll_sex;
    private final int id_ll_birthday = R.id.mine_ll_birthday;
    //    private final int id_ll_sure = R.id.mine_ll_sure;
    private LinearLayout llsv[] = new LinearLayout[4];
    private int llids[] = new int[]{id_ll_img,id_ll_name,id_ll_sex,id_ll_birthday};
    private CircleImageView img;
    private final int imgid = R.id.mine_img_img;
    private  TextView changetxt;
    private  int  changetxtid =  R.id.mine_txt_img;
    private TextView txtsv[] = new TextView[4];
    private int txtids[] = new int[]{R.id.mine_txt_name,R.id.mine_txt_sex,R.id.mine_txt_birthday};
    private  String item[] = new String[]{"拍照","相册","取消"};
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.flower_mine,null);
        aboutView(v);
        changetxt = (TextView) v.findViewById(changetxtid);
        changetxt.setOnClickListener(this);
        IntentFilter intentFilter = new IntentFilter("COM.ZLZ");
        s a = new s();
        getActivity().registerReceiver(a,intentFilter);
        img = (CircleImageView) v.findViewById(imgid);
        img.setOnClickListener(this);
        String dir2 = getActivity().getExternalCacheDir().toString() + "/head/tempmin.jpg";
        File file = new File(dir2);


        if(file.exists()){
            Log.e("zlz","true");
            Bitmap bit = BitmapFactory.decodeFile(dir2);
            img.setImageBitmap(bit);
        }else {
            Log.e("zlz","false");
        }
        return v;

    }

    private  void aboutView(View v){

        for (int i = 0;i<llids.length;i++ ){

            llsv[i] = (LinearLayout) v.findViewById(llids[i]);
            llsv[i].setOnClickListener(this);
        }


    }
    //头像
    private void setHeadImg(){


    }
    //昵称
    private  void setName(){


    }
    //性别
    private  void setSex(){


    }

    //生日
    private  void setBirthday(){


    }
    //确定
    private  void sure(){


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.mine_txt_img:

                Intent intent  = new Intent();
                getActivity().sendBroadcast(intent);

                showlog();
                break;


//            case id_ll_img:
//
//                Intent intent  = new Intent();
//                getActivity().sendBroadcast(intent);
//
//                showlog();
//                break;
            case imgid:

                Dialog dialog;
                String dir = getActivity().getExternalCacheDir().toString() + "/head/temp.jpg";
                View v = LayoutInflater.from(getActivity()).inflate(R.layout.showhead,null);
                ImageView img = (ImageView) v.findViewById(R.id.showimg);
                img.setOnClickListener(this);
                Bitmap bitmap = BitmapFactory.decodeFile(dir);
                img.setImageBitmap(bitmap);
                new AlertDialog.Builder(getActivity()).setView(v).create().show();

                break;
            case id_ll_name:

                showLog();

                break;
            case id_ll_sex:


                break;
            case id_ll_birthday:

                showBirthday();
                break;

        }
    }

    //选择生日
    private void showBirthday(){

        Dialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                Log.e("zlz",i + ","+ i1 + "," + i2 +"" );

            }

        },2017,1,1);

        dialog.show();
    }

    //拍照
    private  void takePhoto(){


        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        String dir = getActivity().getExternalCacheDir().toString() + "/head/temp.jpg";
        File file = new File(dir);
        Uri imageUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 0);


    }
    //相册
    private   void  photos(){

        Intent _intent = new Intent(Intent.ACTION_GET_CONTENT);

        _intent.setType("image/*");

        startActivityForResult(_intent,1);

    }
    private  void  showlog(){

        Dialog dialog = new AlertDialog.Builder(getActivity())//
                .setTitle("请选择头像")//
                .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Log.e("zlz",item[i]);


                        if(i==0){

                            takePhoto();
                        }else  if(i==1){
                            photos();
                        }


                    }
                }).create();

        dialog.show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){

            String dir = getActivity().getExternalCacheDir().toString() + "/head/temp.jpg";
            String dirmin = getActivity().getExternalCacheDir().toString() + "/head/tempmin.jpg";
            File file  = new File(dir);
            File filemin = new File(dirmin);
            if(file.exists()){

                try {

                    Bitmap bitmap = BitmapFactory.decodeFile(dir);
                    FileOutputStream out = new FileOutputStream(filemin);
                    (tool.compressImage(dir)).compress(Bitmap.CompressFormat.JPEG,100,out);
                    out.flush();
                    Intent intent = new Intent("COM.ZLZ");
                    getActivity().sendBroadcast(intent);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }else  if(requestCode == 1){
            if(data != null) {
                String uri = data.getDataString();
                uri = uri.substring(7);
                Bitmap bitmap = BitmapFactory.decodeFile(uri);

                String dir = getActivity().getExternalCacheDir().toString() + "/head/temp.jpg";
                String dirmin = getActivity().getExternalCacheDir().toString() + "/head/tempmin.jpg";
                File file = new File(dir);
                File filemin = new File(dirmin);
                if (file.exists()) {

                    try {

                        FileOutputStream out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        out.flush();
                        out.close();
                        FileOutputStream outmin = new FileOutputStream(filemin);
                        (tool.compressImage(dir)).compress(Bitmap.CompressFormat.JPEG, 100, outmin);
                        out.flush();
                        out.close();
                        Intent intent = new Intent("COM.ZLZ");
                        getActivity().sendBroadcast(intent);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }


            }
        }
    }
    private void  showLog(){

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_name,null);
        dialog = new Dialog(getActivity());
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setBackgroundDrawableResource(R.drawable.back_dialog);
        dialogWindow.requestFeature(Window.FEATURE_NO_TITLE);
        dialogWindow.setContentView(v);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        dialogWindow.setAttributes(lp);

        dialog.show();


    }
    private class s extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals("COM.ZLZ")){

                String dir = getActivity().getExternalCacheDir().toString() + "/head";
                String dir2 = getActivity().getExternalCacheDir().toString() + "/head/tempmin.jpg";
                File file = new File(dir);

                if(file.exists()){

                    Bitmap bit = BitmapFactory.decodeFile(dir2);
                    img.setImageBitmap(bit);
                }

            }
        }
    }
}
