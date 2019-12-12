package com.example.operatingcharge.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.operatingcharge.MyApplication;
import com.example.operatingcharge.R;
import com.example.operatingcharge.adapter.PeriodAdapter;
import com.example.operatingcharge.adapter.RvAdapter;
import com.example.operatingcharge.entity.HairBean;
import com.example.operatingcharge.entity.PeriodBean;
import com.example.operatingcharge.fragment.OrderFragment;
import com.example.operatingcharge.util.AntiShakeUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends AppCompatActivity {

    @ViewInject(R.id.recyclerView)
    RecyclerView recyclerView;
    @ViewInject(R.id.submit)
    TextView submit;
    @ViewInject(R.id.radiogroup)
    RadioGroup radioGroup;
    @ViewInject(R.id.haircut1)
    RadioButton haircut1;
    @ViewInject(R.id.haircut2)
    RadioButton haircut2;
    private PeriodAdapter pAdapter;
    private List<PeriodBean> pbList = new ArrayList<>();
    private String passVariable, flag = "0";                                    // 中间传递的变量
    private SharedPreferences prefs1;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 18) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.select);
        x.view().inject(this);
        MyApplication.getInstance().addActivity(this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        pAdapter = new PeriodAdapter(this, pbList);
        recyclerView.setAdapter(pAdapter);
        prefs1 = getSharedPreferences("UserInfo", 0);
        editor = prefs1.edit();
        if(getIntent().getStringExtra("activity") != null){
            flag = getIntent().getStringExtra("activity");
        }
    }

    // 初始化
    private void init() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.haircut1){
                    getData("理发师1");
                }
                if(checkedId == R.id.haircut2){
                    getData("理发师2");
                }
            }
        });
        pAdapter.setOnItemClickListener(new PeriodAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                pAdapter.setThisPosition(position);
                pAdapter.notifyDataSetChanged();
                PeriodBean p = pbList.get(position);
                passVariable = p.getOrderTime();
            }
        });
        getData("理发师1");
    }

    @Event(value = { R.id.submit }, type = View.OnClickListener.class)
    private void btnClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (!AntiShakeUtils.isInvalidClick(v)) {
                    if(haircut1.isChecked()){
                        if(flag.equals("1")){
                            editor.putString("orderedflag1", "理发师1");                // 给OrderedFragment用的
                            editor.putString("orderedflag2", passVariable);            // 给OrderedFragment用的
                        } else {
                            editor.putString("Barber_Set", "理发师1");
                            editor.putString("OrderTime", passVariable);
                        }
                        editor.apply();
                        finish();
                    }
                    if(haircut2.isChecked()){
                        if(flag.equals("1")){
                            editor.putString("orderedflag1", "理发师2");                // 给OrderedFragment用的
                            editor.putString("orderedflag2", passVariable);            // 给OrderedFragment用的
                        } else {
                            editor.putString("Barber_Set", "理发师2");
                            editor.putString("OrderTime", passVariable);
                        }
                        editor.apply();
                        finish();
                    }
                }
                break;
        }
    }

    // 登录界面网络请求
    private void getData(String s) {
        RequestParams params = new RequestParams("http://222.173.103.228:10107/WebHaifCut.asmx/LoadOrderTimeInfo");
        params.addBodyParameter("OrderDate", "2019-10-08");
        params.addBodyParameter("Barber_Set", s);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {}

            @Override
            public void onError(Throwable ex, boolean arg1) {
                toast("服务器异常");
            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String arg0) {
                try {
                    pbList.clear();
                    JSONObject json = new JSONObject(arg0);
                    if (json.getString("Code").equals("1")) {
                        Type listType = new TypeToken<List<PeriodBean>>() {}.getType();
                        Gson gson = new Gson();
                        List<PeriodBean> list = gson.fromJson(json.getString("lstOrderTime"), listType);
                        for(int i = 0;i < list.size();i++){
                            if(list.get(i).getCurrentNum().equals("0")){
                                pbList.add(list.get(i));
                            }
                        }
                        pAdapter.notifyDataSetChanged();
                    } else {
                        toast(json.getString("Message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void toast(String text){
        Toast.makeText(SelectActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
