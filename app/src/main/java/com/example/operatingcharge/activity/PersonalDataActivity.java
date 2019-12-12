package com.example.operatingcharge.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.operatingcharge.R;
import com.example.operatingcharge.util.ActionSheetDialog;
import com.example.operatingcharge.util.FileUtils;
import com.example.operatingcharge.util.Loadding;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * 个人资料activity，我的界面点击【个人资料】按钮过来的activity
 *
 */
public class PersonalDataActivity extends AppCompatActivity {

    @ViewInject(R.id.username)
    EditText username;
    @ViewInject(R.id.gender)
    EditText gender;
    @ViewInject(R.id.worknumber)
    EditText workNumber;
    @ViewInject(R.id.apartment)
    EditText apartment;
    @ViewInject(R.id.wechat)
    EditText wechat;
    @ViewInject(R.id.telephone)
    EditText telephone;
    @ViewInject(R.id.submit)
    TextView submit;
    private Loadding loading;
    private Intent intent;
    private SharedPreferences prefs1;
    private SharedPreferences.Editor editor;
    private Uri imageUri;
    private String imagePath2 = "";
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        x.view().inject(this);
        init();
    }

    private void init(){
        intent = new Intent();
        loading = new Loadding(this);
        prefs1 = getSharedPreferences("UserInfo", 0);
        editor = prefs1.edit();
        mActivity = this;
        imagePath2 = prefs1.getString("imagepath", "");
        getData();
    }

    @Event(value = {R.id.iv_back, R.id.submit}, type = View.OnClickListener.class)
    private void btnClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.head_portrait:
                new ActionSheetDialog(this)
                        .builder()
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .setTitle("获取相片")
                        .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Red,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        File outputImage = new File(getExternalCacheDir(),
                                                "output_image.jpg");
                                        try {
                                            if(outputImage.exists()){
                                                outputImage.delete();
                                            }
                                            outputImage.createNewFile();
                                        } catch (IOException e){
                                            e.printStackTrace();
                                        }
                                        if (Build.VERSION.SDK_INT >= 24){ // 大于等于android7.0
                                            imageUri = FileProvider.getUriForFile(
                                                    PersonalDataActivity.this,
                                                    "com.example.operatingcharge.activity",
                                                    outputImage);
                                            // 这个imagePaht2打印出来是不一样的，这个字符串是带
                                            // 有“content”的。8.0的手机用下面的imagePath2得不到图片
                                            imagePath2 = imageUri.toString();
                                        } else {
                                            imageUri = Uri.fromFile(outputImage);
                                            // 这个imagePath2打印出来是正常的路径，4.4.4的手机没有
                                            // 问题
                                            imagePath2 = imageUri.getPath();
                                        }
                                        //Log.e("imagePath2", imagePath2);
                                        Intent intent = new Intent(
                                                "android.media.action.IMAGE_CAPTURE");
                                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                        startActivityForResult(intent, 1);
                                    }
                                })
                        .addSheetItem("从相册中选择", ActionSheetDialog.SheetItemColor.Red,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Intent intent = new Intent("android.intent.action.GET_CONTENT");
                                        intent.setType("image/*");
                                        startActivityForResult(intent,2);
                                    }
                                })
                        .show();
                break;
            case R.id.submit:
                upData();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){// 请求码
            case 1:
                if(resultCode == RESULT_OK){// 结果码
                    if(imagePath2.equals("")){
                        editor.putString("imagepath", "");
                        editor.apply();
                    } else if (imagePath2.contains("content")){
                        displayImage(imagePath2);
                    } else {
                        displayImage2(imagePath2);
                    }
                }
                break;
            case 2:
                // KitKat是Google（谷歌公司）Android 4.4（安卓系统）的代号  kitkat是奇巧巧克力
                if(resultCode == RESULT_OK){
                    if(Build.VERSION.SDK_INT >= 19){
                        handleImageOnKitKat(data);
                    }else{
                        handleImageBeforeKitKat(data);
                    }
                }
            break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this, uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            //authority 权力、权威、学术权威
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        imagePath2 = imagePath;
        displayImage2(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        imagePath = getImagePath(uri, null);
        imagePath2 = imagePath;
        displayImage2(imagePath);
    }

    private String getImagePath(Uri uri, String selection){
        String Path = null;
        Cursor cursor = getContentResolver().query(uri,null, selection,
                null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                //column列
                Path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return Path;
    }

    private void displayImage(String Path){
        try{
            if(Build.VERSION.SDK_INT >= 24){
                try {
                    Uri uri = Uri.parse(Path);
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 1, baos);
                    editor.putString("imagepath", Path);
                    editor.apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void displayImage2(String Path){
        try{
            Bitmap bitmap = FileUtils.resizeImage(BitmapFactory.decodeFile(Path), 600);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 1, baos);
            editor.putString("imagepath", Path);
            editor.apply();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 上传数据的接口
    private void upData() {
        RequestParams params = new RequestParams("http://222.173.103.228:10107/WebHaifCut.asmx/ModMessageinfo");
        params.addBodyParameter("UserId", prefs1.getString("ID", ""));
        params.addBodyParameter("PassWord", prefs1.getString("PassWord", ""));
        params.addBodyParameter("Sex", gender.getText().toString().trim());
        params.addBodyParameter("Department", apartment.getText().toString().trim());
        params.addBodyParameter("TelePhone", telephone.getText().toString().trim());
        params.addBodyParameter("WeChat", wechat.getText().toString().trim());
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {}

            @Override
            public void onError(Throwable ex, boolean arg1) {
                toast(ex.getMessage());
            }

            @Override
            public void onFinished() {}

            @Override
            public void onSuccess(String arg0) {
                try {
                    JSONObject object1 = new JSONObject(arg0);
                    if (object1.getString("Code").equals("0")) {
                        toast(object1.getString("Message"));
                    } else if (object1.getString("Code").equals("1")){
                        toast(object1.getString("Message"));
                        finish();
                    } else {
                        toast(object1.getString("Message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getData() {
        RequestParams params = new RequestParams("http://222.173.103.228:10107/WebHaifCut.asmx/LoadMessageinfo");
        params.addBodyParameter("UserId", prefs1.getString("ID", ""));
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {}

            @Override
            public void onError(Throwable ex, boolean arg1) {
                toast(ex.getMessage());
            }

            @Override
            public void onFinished() {}

            @Override
            public void onSuccess(String arg0) {
                try {
                    JSONObject object1 = new JSONObject(arg0);
                    if (object1.getString("Code").equals("0")) {
                        toast(object1.getString("Message"));
                    } else if (object1.getString("Code").equals("1")){
                        JSONArray array = new JSONArray(object1.getString("data"));
                        JSONObject j = array.getJSONObject(0);
                        username.setText(j.getString("Name"));
                        gender.setText(j.getString("Sex"));
                        workNumber.setText(j.getString("WorkNo"));
                        apartment.setText(j.getString("Department"));
                        wechat.setText(j.getString("WeChat"));
                        telephone.setText(j.getString("TelePhone"));
                    } else {
                        toast(object1.getString("Message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 手机返回按钮返回功能
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                setResult(RESULT_OK, intent);// 返回的时候给一个RESULT_OK
                finish();
                break;
            default:
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void toast(String text){
        Toast.makeText(PersonalDataActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
