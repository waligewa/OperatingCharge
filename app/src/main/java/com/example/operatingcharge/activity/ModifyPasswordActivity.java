package com.example.operatingcharge.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.operatingcharge.MyApplication;
import com.example.operatingcharge.R;
import com.example.operatingcharge.util.AntiShakeUtils;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.x;

public class ModifyPasswordActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private EditText oldPassword, newPassword, confirmPassword;
    private String password, oldPwdValue, newPwdValue, confirmPwdValue;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        x.view().inject(this);
        MyApplication.getInstance().addActivity(this);
        init();
    }

    private void init() {
        intent = new Intent();
        prefs = getSharedPreferences("UserInfo", 0); // 创建一个数据库
        editor = prefs.edit();
        oldPassword = (EditText) findViewById(R.id.old_password);          // 原密码
        newPassword = (EditText) findViewById(R.id.new_password);          // 新密码
        confirmPassword = (EditText) findViewById(R.id.confirm_password);  // 确认新密码
        password = prefs.getString("PassWord", "");
        // 三个输入框的初始化
        oldPassword.setText("");
        newPassword.setText("");
        confirmPassword.setText("");
    }

    @Event(value = { R.id.change_password, R.id.iv_back }, type = View.OnClickListener.class)
    private void btnClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.change_password:
                oldPwdValue = oldPassword.getText().toString().trim(); // 原密码字符串
                newPwdValue = newPassword.getText().toString().trim(); // 新密码字符串
                confirmPwdValue = confirmPassword.getText().toString().trim(); //确认新密码字符串
                if (oldPwdValue.equals("")) {
                    toast("原密码输入不能为空!");
                }else if(!(oldPwdValue.equals(password))){
                    toast("原密码输入错误");
                }else if(newPwdValue.equals("")){
                    toast("新密码输入不能为空!");
                } else if (confirmPwdValue.equals("")) {
                    toast("确认新密码输入不能为空!");
                } else if (!(confirmPwdValue.equals(newPwdValue))) {
                    toast("两次密码输入不一致!");
                    confirmPassword.setText("");
                } else if(!AntiShakeUtils.isInvalidClick(v)){
                    uploadData();
                }
                break;
            default:
                break;
        }
    }

    // 进行网络请求的方法
    private void uploadData() {
        RequestParams params = new RequestParams("http://222.173.103.228:10107/WebHaifCut.asmx/ModMessageinfo");
        params.addBodyParameter("UserId", prefs.getString("ID", ""));
        params.addBodyParameter("PassWord", confirmPassword.getText().toString().trim());
        params.addBodyParameter("Sex", prefs.getString("Sex", ""));
        params.addBodyParameter("Department", prefs.getString("Department", ""));
        params.addBodyParameter("TelePhone", prefs.getString("TelePhone", ""));
        params.addBodyParameter("WeChat", prefs.getString("WeChat", ""));
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) { }

            @Override
            public void onError(Throwable ex, boolean arg1) {
                toast(ex.getMessage());
            }

            @Override
            public void onFinished() { }

            @Override
            public void onSuccess(String arg0) {
                try{
                    JSONObject jsonObject = new JSONObject(arg0);
                    if(jsonObject.getString("Code").equals("1")){
                        toast("修改密码成功");
                        editor.putBoolean("ischeck", false);
                        editor.commit();
                        MainActivity.instance2.finish();
                        intent.setClass(ModifyPasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        toast(jsonObject.getString("Message"));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void toast(String text){
        Toast.makeText(ModifyPasswordActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
