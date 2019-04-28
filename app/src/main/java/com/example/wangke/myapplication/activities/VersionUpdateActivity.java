package com.example.wangke.myapplication.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.okhttp.CallBackUtil;
import com.example.wangke.myapplication.okhttp.OkhttpUtil;
import com.example.wangke.myapplication.utils.PackageUtils;
import com.example.wangke.myapplication.utils.ToastUtil;

import java.io.File;

import okhttp3.Call;

public class VersionUpdateActivity extends BaseActivity {
    private int versionCode;
    private String versionName;
    private int lineVersionCode;
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.activity_versionupdate);
    }

    @Override
    public void initView() {
        textView=findViewById(R.id.tv_progress);
        progressBar=findViewById(R.id.progress_bar);
        versionCode = PackageUtils.getVersionCode(this);
        versionName = PackageUtils.getVersionName(this);
    }

    @Override
    public void initBind() {

    }

    @Override
    public void initData() {
      //  if (lineVersionCode > versionCode) {
            OkhttpUtil.okHttpDownloadFile("https://njyf.jskingen.com/images/app-release.apk.1", new CallBackUtil.CallBackFile("/sdcard/updateApk","Prepare.apk") {
                @Override
                    public void onFailure(Call call, Exception e) {
                    ToastUtil.showToast(VersionUpdateActivity.this,"失败");
                }

                @Override
                public void onResponse(File response) {
                    installAPK(response);
                }

                @Override
                public void onProgress(float progress, long total) {
                    super.onProgress(progress, total);
                    if (progress<100){
                        progressBar.setProgress(Math.round(progress));
                        textView.setText(String.valueOf(progress));
                    }
                  else {
                      textView.setText("完成");
                    }
                }
            });

      //  }
    }


    @Override
    public void widgetClick(View v) {

    }

    /**
     * @return
     * @Description 安装apk
     */
    private void installAPK(File savedFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 7.0+以上版本
            //与manifest中定义的provider中的authorities=""保持一致
            Uri apkUri = FileProvider.getUriForFile(this, "com.example.wangke.myapplication.fileprovider", savedFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(savedFile), "application/vnd.android.package-archive");
        }
        startActivity(intent);


    }

}
