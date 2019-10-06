package com.example.operatingcharge.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.operatingcharge.R;

/**
 *
 * Author : 赵彬彬
 * Date   : 2019/6/10
 * Time   : 14:32
 * Desc   : HomeFragment，我的界面
 */
public class ReservationFragment extends Fragment implements View.OnClickListener {
    private RadioGroup radioGroup;
    private RadioButton ordered, order;
    private Context activity;
    private OrderedFragment orderedF;
    private OrderFragment orderF;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_reservation_fragment, null);
        return v;
    }

//    初始化主界面
    protected void initView() {
//        监听事件：为底部的RadioGroup绑定状态改变的监听事件
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
    }

    private void toast(String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        radioGroup = getActivity().findViewById(R.id.radiogroup);
        ordered = getActivity().findViewById(R.id.ordered);
        order = getActivity().findViewById(R.id.order);
        ordered.setOnClickListener(this);
        order.setOnClickListener(this);
        initView();
        setDefaultFragment();
    }
    /**
     * 设置默认的Fragment
     */
    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        orderedF = new OrderedFragment();
        transaction.add(R.id.fragme, orderedF).commit();
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();

        switch (v.getId()) {
            case R.id.ordered:
                if (orderedF == null) {
                    orderedF = new OrderedFragment();
                }
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.fragme, orderedF);
                break;
            case R.id.order:
                if (orderF == null) {
                    orderF = new OrderFragment();
                }
                transaction.replace(R.id.fragme, orderF);
                break;
        }
        // transaction.addToBackStack();
        // 事务提交
        transaction.commit();
    }
}
