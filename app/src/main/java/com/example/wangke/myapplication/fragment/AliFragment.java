package com.example.wangke.myapplication.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.wangke.myapplication.activities.HotFixActivity;
import com.example.wangke.myapplication.R;

import com.example.wangke.myapplication.utils.FixDexUtils;

public class AliFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {
    private WindowManager windowManager;
    private TextView textView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_content,null);
        TextView textView=view.findViewById(R.id.tv_content);
        textView.setText("阿里");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionStrorage = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                int permissionCalendar = ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_CALENDAR);
                if (permissionStrorage != PackageManager.PERMISSION_GRANTED||permissionCalendar !=PackageManager.PERMISSION_GRANTED) {
                    //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true,它在用户选择"不再询问"的情况下返回false
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_CALENDAR)) {
                    }
                    else {
                        // We don't have permission so prompt the user
                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CALENDAR,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                }
                else {
                    FixDexUtils.loadFixedDex(getActivity(), Environment.getExternalStorageDirectory());
                    startActivity(new Intent(getContext(),HotFixActivity.class));
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //init();
        super.onActivityCreated(savedInstanceState);
    }

    public void init(){
        windowManager= (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params=new WindowManager.LayoutParams();
        params.type=WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        params.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format=PixelFormat.RGBA_8888;
        params.gravity=Gravity.RIGHT|Gravity.CENTER_VERTICAL;
        params.width=WindowManager.LayoutParams.WRAP_CONTENT;
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        textView=new TextView(getContext());
        textView.setText("悬浮窗123");
        textView.setBackgroundColor(Color.parseColor("#FF3499"));
        windowManager.addView(textView,params);

    }

    @Override
    public void onDestroy() {
       // windowManager.removeView(textView);
        super.onDestroy();
    }

}
