package com.example.ind4.myapplication.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.ind4.myapplication.R;

import java.lang.ref.WeakReference;

/**
 * 文件描述:
 *
 * @author Administrator
 * @date 2019/1/29
 */
public class WeakActivity extends AppCompatActivity {
    private MyHandle myHandle;
    private static TextView textView;
    private long u=0;
    private String[] strings =new String[]{"我","喜","欢","android","啊"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weak);
        textView=findViewById(R.id.tv_love);
        myHandle=new MyHandle(this);
        init();
    }

    public void init(){
        for (String string : strings) {
            u=u+500;
            Message message=Message.obtain();
            message.what=1;
            message.obj=string;
            myHandle.sendMessageDelayed(message,u);
        }
    }
    private static class MyHandle extends Handler{
        private WeakReference<WeakActivity> weakReference;
        public MyHandle(WeakActivity activity){
            weakReference=new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WeakActivity activity=weakReference.get();
            if (activity!=null){
                if (msg.what==1){
                    String result= (String) msg.obj;
                    textView.setText(result);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandle.removeCallbacksAndMessages(null);
    }
}
