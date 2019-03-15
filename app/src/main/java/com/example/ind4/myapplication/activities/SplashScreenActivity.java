package com.example.ind4.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ind4.myapplication.R;
import com.example.ind4.myapplication.mvp.p.IPresenterSplash;
import com.example.ind4.myapplication.mvp.p.PresenterSplash;
import com.example.ind4.myapplication.mvp.v.ISplashView;
import com.example.ind4.myapplication.okhttp.CallBackUtil;
import com.example.ind4.myapplication.okhttp.OkhttpUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * 文件描述:
 *
 * @author Administrator
 * @date 2019/1/18
 */
public class SplashScreenActivity extends BaseActivity implements ISplashView {
    private IPresenterSplash iPresenterSplash;
    private Animation animationMain=null;
    private ImageView imageViewOne,imageViewTwo,imageViewThree;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.activity_splash);
        imageViewOne=findViewById(R.id.img_splashone);
        imageViewTwo=findViewById(R.id.img_splashtwo);
        imageViewThree=findViewById(R.id.img_splashthree);
        iPresenterSplash=new PresenterSplash(this);
        iPresenterSplash.startAction(animationMain);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        },6000);


//        //GET请求
//        String url = "http://211.103.33.70:13382/parkingInterface/payment/getPayRecord";
//        Map<String,String> map=new HashMap<>();
//        map.put("openid","123");
//        map.put("carownerId","26");
//        map.put("sysType","1");
//
//        Map<String,String> heaherMap=new HashMap<>();
//        heaherMap.put("content-type","application/json");
//        OkhttpUtil.okHttpGet(url,map,heaherMap, new CallBackUtil.CallBackString() {
//            @Override
//            public void onFailure(Call call, Exception e) {
//
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(SplashScreenActivity.this,"Success",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        //POST请求
//        String url = "https://www.baidu.com/";
//        HashMap<String, String> paramsMap = new HashMap<>();
//        paramsMap.put("title","title");
//        OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
//            @Override
//            public void onFailure(Call call, Exception e) {
//
//            }
//
//            @Override
//            public void onResponse(String response) {
//
//            }
//        });
//
//        //PUT请求
//        String url = "https://www.baidu.com/";
//        HashMap<String, String> paramsMap = new HashMap<>();
//        paramsMap.put("title","title");
//        OkhttpUtil.okHttpPut(url, paramsMap, new CallBackUtil.CallBackString() {
//            @Override
//            public void onFailure(Call call, Exception e) {
//
//            }
//
//            @Override
//            public void onResponse(String response) {
//
//            }
//        });
//
//        //DELETE请求
//        String url = "https://www.baidu.com/";
//        HashMap<String, String> paramsMap = new HashMap<>();
//        paramsMap.put("title","title");
//        OkhttpUtil.okHttpDelete(url, paramsMap, new CallBackUtil.CallBackString() {
//            @Override
//            public void onFailure(Call call, Exception e) {
//
//            }
//
//            @Override
//            public void onResponse(String response) {
//
//            }
//        });
//
//        //上传文件
//        File file = new File(Environment.getExternalStorageDirectory()+"/kwwl/abc.jpg");
//        HashMap<String, String> paramsMap = new HashMap<>();
//        paramsMap.put("title","title");
//        OkhttpUtil.okHttpUploadFile(url, file, "image", OkhttpUtil.FILE_TYPE_IMAGE, paramsMap, new CallBackUtil.CallBackString() {
//            @Override
//            public void onFailure(Call call, Exception e) {
//
//            }
//
//            @Override
//            public void onProgress(float progress, long total) {
//
//            }
//
//            @Override
//            public void onResponse(String response) {
//
//            }
//        });
//
//        //下载文件
//        OkhttpUtil.okHttpDownloadFile("url", new CallBackUtil.CallBackFile("fileDir","fileName") {
//            @Override
//            public void onFailure(Call call, Exception e) {
//
//            }
//
//            @Override
//            public void onProgress(float progress, long total) {
//            }
//
//            @Override
//            public void onResponse(File response) {
//
//            }
//        });
    }

    @Override
    public void initView() {

    }


    @Override
    public void initData() {

    }

    @Override
    public void initBind() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void startAnim(Animation animation) {
        imageViewOne.startAnimation(animation);
        imageViewTwo.startAnimation(animation);
        imageViewThree.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iPresenterSplash.endAction(animationMain);
            }
        },3000);
    }

    @Override
    public void endAnim(Animation animation) {
        imageViewOne.startAnimation(animation);
        imageViewTwo.startAnimation(animation);
        imageViewThree.startAnimation(animation);
    }
}

