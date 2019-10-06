package com.example.operatingcharge.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.operatingcharge.MyApplication;
import com.example.operatingcharge.R;
import com.example.operatingcharge.adapter.MainViewAdapter;
import com.example.operatingcharge.fragment.HomeFragment;
import com.example.operatingcharge.fragment.ReservationFragment;
import com.example.operatingcharge.fragment.MineFragment;
import com.example.operatingcharge.listener.OnTabSelectedListener;
import com.example.operatingcharge.widget.Tab;
import com.example.operatingcharge.widget.TabContainerView;

/**
 *
 * Author : 赵彬彬
 * Date   : 2019/6/4
 * Time   : 22:26
 * Desc   :
 */
public class MainActivity extends AppCompatActivity {

    public static MainActivity instance2 = null;
    public static boolean isForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getInstance().addActivity(this);
        instance2 = this;
        init();
        //initNFC();

        /*PgyUpdateManager.register(mActivity, new UpdateManagerListener() {
            @Override
            public void onUpdateAvailable(final String result) {
//              将新版本信息封装到AppBean中
                final AppBean appBean = getAppBeanFromString(result);
                new MyAlertDialog(mActivity)
                        .builder()
                        .setTitle("更新软件")
                        .setMsg("请点击“确定”按钮下载新版本")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startDownloadTask( MainActivity.this, appBean.getDownloadURL());
                            }
                        })
                        .show();
            }
            @Override
            public void onNoUpdateAvailable() { }
        });*/
    }

    private void init(){

//      控件的初始化，然后加上适配器，控件的点击事件
        TabContainerView tabContainerView = (TabContainerView) findViewById(R.id.tab_container);
        MainViewAdapter mainViewAdapter = new MainViewAdapter(getSupportFragmentManager(),
                new Fragment[]{new HomeFragment(), new ReservationFragment(), new MineFragment()});
        mainViewAdapter.setHasMsgIndex(0);
        tabContainerView.setAdapter(mainViewAdapter);
        tabContainerView.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                Log.e("Fuck the code!", tab.getIndex() + "");
                if (tab.getIndex() == 0) {
                    //HomeFragment.speechInput.setText("");
                }
            }
        });
    }

//  for receive customer msg from jpush server
    /*private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.shenzhen.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";*/

//  极光推送的广播接收器
    /*public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY); // priority优先权
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }*/

    /*public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    toast(showMsg.toString());
                }
            } catch (Exception e) { }
        }
    }*/

    //  连续点击两次返回键，退出应用程序
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
//              如果两次按键的时间间隔大于2秒，则不退出
                if (secondTime - firstTime > 2000) {
                    toast("再按一次退出程序");
                    firstTime = secondTime; // 更新firstTime
                    return true;
                } else {
                    MyApplication.getInstance().exit();
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void toast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
