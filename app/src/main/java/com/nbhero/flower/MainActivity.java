package  com.nbhero.flower;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nbhero.Home.EquipmentList;
import com.nbhero.Home.Find;
import com.nbhero.Home.Map;
import com.nbhero.Home.Mine;
import com.nbhero.util.ZlzRootActivity;

import java.io.File;


public class MainActivity extends ZlzRootActivity  implements View.OnClickListener{

    private LinearLayout  llback,btnhomeadd;
    private  String title[] = new String[]{"设备","地图","发现","我的"};

    EquipmentList equipmentList = new EquipmentList();
    Map map = new Map();
    Find find = new Find();
    Mine mine = new Mine();

    private  Fragment  fragments[] =  new Fragment[]{equipmentList, map,find,mine};
    private  final  int btnids[] = new int[]{R.id.main_equipment,R.id.main_map,R.id.main_find,R.id.main_mine};
    private  final  int imgvids[] = new int[]{R.id.main_img_equipment,R.id.main_img_map,R.id.main_img_find,R.id.main_img_mine};
    private  final  int txtids[] = new int[]{R.id.main_txt_equipment,R.id.main_txt_map,R.id.main_txt_find,R.id.main_txt_mine};
    private  final  int imgoffids[] = new int[]{R.drawable.taboneoff,R.drawable.tabtwooff,R.drawable.tabthreeoff,R.drawable.tabfouroff};
    private  final  int imgonids[] = new int[]{R.drawable.taboneon,R.drawable.tabtwoon,R.drawable.tabthreeon,R.drawable.tabfouron};

    private  LinearLayout  btns[] = new LinearLayout[4];
    private ImageView  imgvs[] = new ImageView[4];
    private TextView  txts[] = new TextView[4];

    // 当前选择页面
    private Fragment fragmentNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setBarColor(R.color.headcolor);
        setContentView(R.layout.activity_main);
        String dir = getExternalCacheDir().toString() + "/head";
        File file = new File(dir);
        if(!file.exists()){

            file.mkdir();
        }
        aboutView();



    }
    //获取控件实例
    private void aboutView(){

        llback = (LinearLayout) findViewById(R.id.flowerback);
        llback.setVisibility(View.INVISIBLE);
        btnhomeadd = (LinearLayout) findViewById(R.id.homeadd);
        btnhomeadd.setVisibility(View.VISIBLE);
        btnhomeadd.setOnClickListener(this);
        for (int i=0;i<btnids.length;i++){

            btns[i] = (LinearLayout) findViewById(btnids[i]);
            btns[i].setOnClickListener(new myClick(i));

        }

        for (int i=0;i<btnids.length;i++){

            imgvs[i] = (ImageView) findViewById(imgvids[i]);

        }

        for (int i=0;i<btnids.length;i++){

            txts[i] = (TextView) findViewById(txtids[i]);

        }

        switchContent(fragments[0],"设备");
        fragmentNow = equipmentList;

    }


    //改变选项卡
    private void change(Fragment pfragment){
        FragmentManager  fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.home_home,pfragment);
        transaction.commit();
    }



    public void switchContent(Fragment to,String title) {
        settitle(title);
        FragmentManager  fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (fragmentNow != to) {
            fragmentNow = to;
            Log.e("zlz","1");

            if (!to.isAdded()) {    // 先判断是否被add过

                transaction.add(R.id.home_home , to);
//                transaction.addToBackStack(null);
                transaction.show(to);
                transaction.commit();

            } else {
//                transaction.addToBackStack(null);
                transaction.show(to);
                transaction.commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @Override
    public void onClick(View view) {

        Intent go = new Intent(this, FlowerAddEquipmentNew.class);
        startActivity(go);



    }


    private  class  myClick implements  View.OnClickListener{

        private  int id;


        public  myClick(int pid){

            this.id = pid;

        }

        //重写接口方法
        @Override
        public void onClick(View view) {


            if(fragmentNow !=  fragments[id]) {

                for (int i = 0; i < btnids.length; i++) {

                    //更改按钮
                    if (view.getId() == btnids[i]) {


                        if(i == 0){
                            btnhomeadd.setVisibility(View.VISIBLE);

                        }else{

                            btnhomeadd.setVisibility(View.INVISIBLE);
                        }

                        //更改fragment
                        switchContent(fragments[id],title[id]);

                        for (int j = 0; j < btnids.length; j++) {
                            imgvs[j].setImageResource(imgoffids[j]);
                            txts[j].setTextColor(getApplication().getResources().getColor(R.color.textcolor));

                        }


                        imgvs[i].setImageResource(imgonids[i]);
                        txts[i].setTextColor(getApplication().getResources().getColor(R.color.headcolor));

                        //更改按钮结束了




                        break;
                    }

                }
            }else {

                Toast.makeText(MainActivity.this,"已经是当前页面，不需要更换",Toast.LENGTH_SHORT).show();

            }
//            fragmentNow = fragments[id];
        }
    }

    private void hideFragments(FragmentTransaction transaction) {

        for (int i = 0 ; i < fragments.length ;i++){

            if(fragments[i].isAdded()){

                transaction.hide( fragments[i]);

            }

        }
    }
}
