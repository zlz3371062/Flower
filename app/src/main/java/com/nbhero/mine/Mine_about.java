package com.nbhero.mine;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nbhero.flower.R;

/**
 * Created by zhenglingzhong on 2016/11/3.
 */

public class Mine_about extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.mine_about,null);
        TextView txt = (TextView) v.findViewById(R.id.about_txt);
        txt.setText(Html.fromHtml("帕乔香氛注重高品质产品和个性化服务<br/>致力于让客户享受到真正健康、安心、体贴<br/>放心的服务"));

        return v;
    }
}
