package service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.ind4.myapplication.R;

import java.util.Random;

/**
 * 文件描述:
 *
 * @author Administrator
 * @date 2019/2/15
 */
public class NewPlayService extends Service {
    private MediaPlayer mediaPlayer;

    public class MyBinder extends Binder{
        public NewPlayService getService(){
            return NewPlayService.this;
        }
    }
    private MyBinder myBinder=new MyBinder();
    private final Random generator = new Random();
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        mediaPlayer=MediaPlayer.create(this,R.raw.house);
        mediaPlayer.start();
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        super.onDestroy();
    }
    public int getRandomNumber() {
        return generator.nextInt();
    }
}
