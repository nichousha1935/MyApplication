package com.example.ind4.myapplication.activities;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ind4.myapplication.R;
import com.example.ind4.myapplication.service.NewPlayService;

/**
 * 文件描述:
 *
 * @author Administrator
 * @date 2019/1/14
 */
public class HotFixActivity extends AppCompatActivity {
    private ServiceConnection serviceConnection = new MyServiceConnection();
    private NewPlayService newPlayService;

    //    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotfix);
        //startService(new Intent(this,PlayService.class));
        Intent intent = new Intent(this, NewPlayService.class);
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
//        textView=findViewById(R.id.text);
//        textView.setText("修复成功");
    }

    @Override
    protected void onDestroy() {
        //stopService(new Intent(this,PlayService.class));
        unbindService(serviceConnection);
        super.onDestroy();
    }

    private class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            NewPlayService.MyBinder myBinder= (NewPlayService.MyBinder) service;
            newPlayService=myBinder.getService();
            newPlayService.getRandomNumber();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
