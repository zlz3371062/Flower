package com.nbhero.flower;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nbhero.adapter.ForNews;
import com.nbhero.util.ZlzRootActivity;

/**
 * Created by zhenglingzhong on 2016/11/2.
 */

/**
 * @deprecated
 * 描述 已经用fragment替换 取消了本avtivity
 */

public class FlowerNews extends ZlzRootActivity{


    //    id
    private  int lsid = R.id.news_ls;
    //view
    private ListView ls;
    //adapter
    ForNews forNews = new ForNews(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_news);
        aboutView();


    }
    private  void aboutView(){
        settitle("我的消息");
        back();
        ls = (ListView) findViewById(lsid);
        ls.setAdapter(forNews);
        ls.setOnItemClickListener(myItemClick);

    }

    private AdapterView.OnItemClickListener myItemClick = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent go = new Intent(FlowerNews.this,FlowerDetailForNews.class);
            startActivity(go);

        }
    };

}
