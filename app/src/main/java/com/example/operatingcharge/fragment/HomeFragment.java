package com.example.operatingcharge.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.operatingcharge.R;
import com.example.operatingcharge.adapter.RvAdapter;
import com.example.operatingcharge.entity.HairBean;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Author : 赵彬彬
 * Date   : 2019/9/29
 * Time   : 23:20
 * Desc   : 
 */
public class HomeFragment extends Fragment{

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private RvAdapter mAdapter;
    private List<HairBean> datas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_home_fragment, null);
        x.view().inject(getActivity());
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new RvAdapter(getActivity(), datas);
        recyclerView.setAdapter(mAdapter);
        simulationData();
        return view;
    }

    private void simulationData(){
        datas.clear();
        HairBean h1 = new HairBean();
        h1.setUsername("张三");
        h1.setSex("男");
        h1.setWorkNumber("6583");
        h1.setApartment("技术中心 工程处");
        h1.setBookProject("剪发");
        h1.setTime("08.25 19:00-19:30");
        h1.setPhone("13210283912");
        datas.add(h1);
        HairBean h2 = new HairBean();
        h2.setUsername("赵小倩");
        h2.setSex("女");
        h2.setWorkNumber("8605");
        h2.setApartment("考官行政 考管部");
        h2.setBookProject("烫发");
        h2.setTime("08.25 19:40-20:40");
        h2.setPhone("13210283912");
        datas.add(h2);
        HairBean h3 = new HairBean();
        h3.setUsername("刘虎");
        h3.setSex("男");
        h3.setWorkNumber("5681");
        h3.setApartment("A区生产部 碳钢车间");
        h3.setBookProject("剪发");
        h3.setTime("08.25 20:50-21:20");
        h3.setPhone("13210283912");
        datas.add(h3);
        mAdapter.notifyDataSetChanged();
    }

//    设备状态的赋值操作
//    private void getData() {
//        RequestParams params;
//        params = new RequestParams("http://222.173.103.228:10082/" + "Service/C_WNMS_API.asmx/LoadDeviceJKInfo");
//        params.addBodyParameter("guid", guidString);
//        params.addBodyParameter("equNo", equNo);
//        params.addBodyParameter("userId", userId);
//        x.http().get(params, new Callback.CommonCallback<String>() {
//
//            @Override
//            public void onCancelled(CancelledException arg0) {}
//
//            @Override
//            public void onError(Throwable ex, boolean arg1) { }
//
//            @Override
//            public void onFinished() {}
//
//            @Override
//            public void onSuccess(String arg0) {
//                try {
//                    JSONObject object1 = new JSONObject(arg0);
//                    if (object1.getString("Code").equals("1")) { }
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    private void toast(String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
