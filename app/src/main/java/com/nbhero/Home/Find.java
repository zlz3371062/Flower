package com.nbhero.Home;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nbhero.flower.R;

/**
 * Created by zhenglingzhong on 2016/10/26.
 */

public class Find extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View  view = inflater.inflate(R.layout.fragment_find,container,false);

        WebView web = (WebView) view.findViewById(R.id.homefind_web_main);
        web.loadUrl("http://www.baidu.com");
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("zlz","onDestroy'");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("zlz","onResume'");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.e("zlz","onPause'");
    }


}
