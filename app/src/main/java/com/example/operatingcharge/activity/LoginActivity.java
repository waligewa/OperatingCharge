package com.example.operatingcharge.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.operatingcharge.MyApplication;
import com.example.operatingcharge.R;
import com.example.operatingcharge.entity.HairBean;
import com.example.operatingcharge.util.AntiShakeUtils;
import com.example.operatingcharge.util.Loadding;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "dah_LoginActivity";

    private String account, password;
    private SharedPreferences prefs, prefs2;
    private SharedPreferences.Editor editor, editor2;
    private Loadding loadding, loaddingTest;
    private Boolean isRemember = false;
    private Intent intent;
    private List<HairBean> list1 = new ArrayList<>();
    private Handler handler = new Handler();
    private Activity mActivity;
    @ViewInject(R.id.username)
    EditText userName;
    @ViewInject(R.id.password)
    EditText passWord;
    @ViewInject(R.id.checkbox1)
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 18) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.login);
        x.view().inject(this);
        MyApplication.getInstance().addActivity(this);

        mActivity = this;
        loaddingTest = new Loadding(mActivity);
    }

    // 初始化
    private void init() {
        loadding = new Loadding(this);
        prefs = getSharedPreferences("UserInfo", 0);  // 创建一个偏好文件
        prefs2 = getSharedPreferences("device", Context.MODE_PRIVATE);  // 创建一个偏好文件
        editor = prefs.edit();
        editor2 = prefs2.edit();
        // 从数据库中取出remember_password参数的值
        isRemember = prefs.getBoolean("ischeck", false);
        checkBox.setChecked(isRemember);
        // 从数据库中取出account参数的值
        account = prefs.getString("WorkNo", "");
        // 从数据库中取出password参数的值
        password = prefs.getString("userpass", "");
        intent = new Intent();
        if (isRemember) {
            userName.setText(account);
            userName.setSelection(account.length());
            passWord.setText(password);
            passWord.setSelection(password.length());
        } else {
            userName.setText(account);
            userName.setSelection(account.length());
            passWord.setText("");
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    isRemember = true;
                } else {
                    isRemember = false;
                }
            }
        });
        requestPermission();
    }

    @Event(value = {R.id.login_main_interface}, type = View.OnClickListener.class)
    private void btnClick(View v) {
        switch (v.getId()) {
            case R.id.login_main_interface:
                if (!AntiShakeUtils.isInvalidClick(v)) {
                    if (userName.getText().toString().trim().length() <= 0) {
                        toast("请输入用户名");
                    } else if (passWord.getText().toString().trim().length() < 1) {
                        toast("密码少于1位");
                    } else {
                        loadding.show("正在加载中...");
                        // 登录界面网络请求
                        getData();
                    }
                }
                break;
        }
    }

    // 登录界面网络请求
    private void getData() {
        RequestParams params = new RequestParams("http://222.173.103.228:10107/WebHaifCut.asmx/UserLogin");
        params.addBodyParameter("WorkNo", userName.getText().toString().trim());
        params.addBodyParameter("PassWord", passWord.getText().toString().trim());
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {}

            @Override
            public void onError(Throwable ex, boolean arg1) {
                toast("服务器异常");
            }

            @Override
            public void onFinished() {
                if (loadding.isShow()) {
                    loadding.close();
                }
            }

            @Override
            public void onSuccess(String arg0) {
                try {
                    JSONObject json = new JSONObject(arg0);
                    if (json.getString("Code").equals("1")) {
                        JSONArray array = new JSONArray(json.getString("data"));
                        JSONObject jt = array.getJSONObject(0);
                        editor.putString("ID", jt.getString("ID"));
                        editor.putString("Name", jt.getString("Name"));
                        editor.putString("Sex", jt.getString("Sex"));
                        editor.putString("WorkNo", jt.getString("WorkNo"));
                        editor.putString("Department", jt.getString("Department"));
                        editor.putString("WeChat", jt.getString("WeChat"));
                        editor.putString("TelePhone", jt.getString("TelePhone"));
                        editor.putString("PassWord", jt.getString("PassWord"));
                        if (isRemember) {
                            editor.putBoolean("ischeck", isRemember);
                            editor.putString("userpass", passWord.getText().toString().trim());
                        } else {
                            editor.putBoolean("ischeck", isRemember);
                            editor.putString("userpass", "");
                        }
                        editor.apply();
                        editor2.apply();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        toast(json.getString("Message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (loadding.isShow()) {
                    loadding.close();
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        // 申请权限
        LoginActivityPermissionsDispatcher.getPermissionsWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE})
    void getPermissions() { }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LoginActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE})
    void showRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("申请相关权限")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 再次执行请求
                        request.proceed();
                    }
                })
                .show();
    }

    @OnPermissionDenied({Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE})
    void permissionsDenied() {
        toast("权限被拒绝，软件部分功能可能会失效！");
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE})
    void neverAskAgain() {
        toast("权限被拒绝，软件部分功能可能会失效！");
    }

    private void toast(String text){
        Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
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
        if (loaddingTest.isShow()) {
            loaddingTest.close();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
