package com.example.wangke.myapplication.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.service.NotificationClickReceiver;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtils {
    private Vibrator mVibrator;
    private SoundPool mSoundPool;


    private NotificationUtils(){

    }


    private static class HolderNotificationUtils {
        private static final NotificationUtils notificationUtils = new NotificationUtils();
    }

    public static NotificationUtils getInstance() {
        return HolderNotificationUtils.notificationUtils;
    }
    //通知栏
    public void showNotification(Context context,boolean sound,boolean reminder) {
        Intent intent = new Intent(context, NotificationClickReceiver.class);
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pd = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long timestamp = System.currentTimeMillis();
        String channelId = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = "tm_notification2";
            String channelName = "提醒推送";
            createNotificationChannel(context, channelId, channelName, NotificationManager.IMPORTANCE_HIGH, manager);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setTicker("这是通知栏")//通知在状态栏显示时的文本
                .setWhen(timestamp)//自定义消息时间以毫秒为单位
                .setContentTitle("通知")//通知栏标题
                .setContentText("上班时间到了")//通知栏内容
                .setContentIntent(pd)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                //  .setLargeIcon()
                 .setSmallIcon(R.mipmap.ic_launcher_round)
               // .setDefaults(NotificationCompat.DEFAULT_SOUND)//设置消息的提醒方式，震动提醒：DEFAULT_VIBRATE     声音提醒：NotificationCompat.DEFAULT_SOUND  三色灯提醒NotificationCompat.DEFAULT_LIGHTS     以上三种方式一起：DEFAULT_ALL
               // .setVibrate(new long[]{0, 1000, 1000, 1000})//设置震动方式，延迟零秒，震动一秒，延迟一秒、震动一秒
                .setPriority(NotificationCompat.PRIORITY_HIGH);//设置优先级


//        mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.tm_icon_money));
//        mBuilder.setSmallIcon(R.drawable.tm_icon_money);

        Notification notification = mBuilder.build();
        if (sound) {
            notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.message);
        }
        if (reminder) {
            notification.defaults |= Notification.DEFAULT_VIBRATE;//通知栏_震动提示
        }
        notification.defaults |= Notification.DEFAULT_LIGHTS;//通知栏_指示灯
        notification.flags = Notification.FLAG_AUTO_CANCEL;//通知栏点击后消失
        manager.notify(0, notification);//发出状态栏通知
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(Context context, String channelId, String channelName, int importance, NotificationManager manager) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.enableVibration(true);
        channel.shouldVibrate();
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
//                NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }

    //播放声音
    public void playSound(Context context) {

        if (mSoundPool == null) {
            mSoundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 5);
        }
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//                priority —— 流的优先级，值越大优先级高，影响当同时播放数量超出了最大支持数时SoundPool对该流的处理；
//                loop —— 循环播放的次数，0为值播放一次，-1为无限循环，其他值为播放loop+1次（例如，3为一共播放4次）.
//                rate —— 播放的速率，范围0.5-2.0(0.5为一半速率，1.0为正常速率，2.0为两倍速率)
                soundPool.play(sampleId, 1.0f, 1.0f, 1, 5, 1);//播放
            }
        });
        mSoundPool.load(context, R.raw.accept_reminder, 1);//加载资源
    }

    //震动
    public void playVibrator(Context context) {
        if (mVibrator == null) {
            mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }
        mVibrator.vibrate(500);//震动1次，震动500ms
    }

    public void cancelSound() {
        if (mSoundPool != null) {
            mSoundPool.release();
            mSoundPool = null;
        }
    }

    public void releaseVibrate() {
        if (mVibrator != null) {
            mVibrator.cancel();
        }
    }
}
