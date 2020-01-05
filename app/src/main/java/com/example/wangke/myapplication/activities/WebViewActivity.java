package com.example.wangke.myapplication.activities;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.wangke.myapplication.R;

import androidx.annotation.RequiresApi;

public class WebViewActivity extends BaseActivity {
    //声明引用
    private WebView mWVmhtml;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.activity_webview);
        //获取控件对象
        mWVmhtml=findViewById(R.id.WV_Id);
        //加载本地html文件
        // mWVmhtml.loadUrl("file:///android_asset/hello.html");
        //加载网络URL
        //mWVmhtml.loadUrl("https://blog.csdn.net/qq_36243942/article/details/82187204");
        //设置JavaScrip
        mWVmhtml.getSettings().setJavaScriptEnabled(true);
        mWVmhtml.getSettings().setMediaPlaybackRequiresUserGesture(true);
        mWVmhtml.getSettings().setAllowFileAccess(true);
        mWVmhtml.getSettings().setPluginState(WebSettings.PluginState.ON);

        mWVmhtml.setBackgroundColor(0);

        //访问百度首页
        mWVmhtml.loadUrl("http://101.37.247.48:8081/iosView/showAddLeave");
        //设置在当前WebView继续加载网页
        mWVmhtml.setWebViewClient(new MyWebViewClient());

    }

    @Override
    public void initView() {

    }

    @Override
    public void initBind() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void widgetClick(View v) {

    }

    class MyWebViewClient extends WebViewClient {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override  //WebView代表是当前的WebView
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            //表示在当前的WebView继续打开网页
            //view.loadUrl(request.getUrl().toString());
            return super.shouldOverrideUrlLoading(view,request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("WebView","开始访问网页");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d("WebView","访问网页结束");
        }
    }

}
