package com.nbhero.equipment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nbhero.flower.R;

/**
 * Created by zhenglingzhong on 2016/11/3.
 */

public class EquipmentAbout extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View  v = inflater.inflate(R.layout.equipment_about,null);
      return  v;
    }
}
