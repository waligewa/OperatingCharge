package com.example.operatingcharge.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.operatingcharge.R;

/**
 *
 * Author : 赵彬彬
 * Date   : 2019/6/10
 * Time   : 14:32
 * Desc   : HomeFragment，我的界面
 */
public class OrderFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_order_fragment, null);
        return view;
    }

    private void toast(String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
