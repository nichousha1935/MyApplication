package com.example.wangke.myapplication.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.wangke.myapplication.activities.DragActivity;

public class NotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, DragActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("goodsBean", null);
//        bundle.putString("pager", "");
//        newIntent.putExtras(bundle);
        context.startActivity(newIntent);
    }
}
