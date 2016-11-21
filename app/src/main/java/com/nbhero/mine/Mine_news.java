package com.nbhero.mine;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nbhero.adapter.ForNews;
import com.nbhero.flower.FlowerDetailForNews;
import com.nbhero.flower.R;

/**
 * Created by zhenglingzhong on 2016/11/2.
 */

public class Mine_news extends Fragment{



    //    id
    private  int lsid = R.id.news_ls;
    //view
    private ListView ls;
    //adapter
    ForNews forNews;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.mine_news,null);
        forNews = new ForNews(getActivity());
        aboutView(v);

        return v;


    }
    private  void aboutView(View v){
        ls = (ListView)v. findViewById(lsid);
        ls.setAdapter(forNews);
        ls.setOnItemClickListener(myItemClick);

    }

    private AdapterView.OnItemClickListener myItemClick = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent go = new Intent(getActivity(),FlowerDetailForNews.class);
            startActivity(go);

        }
    };
}
