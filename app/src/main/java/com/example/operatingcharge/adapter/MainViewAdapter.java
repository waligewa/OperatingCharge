package com.example.operatingcharge.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.operatingcharge.R;

/**
 * Created by chengxi on 17/4/26.
 */

public class MainViewAdapter extends BaseAdapter {

    private Fragment[] fragmentArray;
    private FragmentManager fragmentManager;
    private int hasMsgIndex = 0;

    public void setHasMsgIndex(int hasMsgIndex) {
        this.hasMsgIndex = hasMsgIndex;
    }

    public MainViewAdapter(FragmentManager fragmentManager, Fragment[] fragmentArray) {
        this.fragmentManager = fragmentManager;
        this.fragmentArray = fragmentArray;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int hasMsgIndex() {
        return hasMsgIndex;
    }


    @Override
    public String[] getTextArray() {
        return new String[] {"首页", "预约", "我的"};
    }

    @Override
    public Fragment[] getFragmentArray() {
        return fragmentArray;
    }

    @Override
    public int[] getIconImageArray() {
        return new int[] { R.mipmap.homepage2, R.mipmap.operation2,
                R.mipmap.service2 };
    }

    @Override
    public int[] getSelectedIconImageArray() {
        return new int[] { R.mipmap.homepage1, R.mipmap.operation1,
                R.mipmap.service1 };
    }

    @Override
    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }
}
