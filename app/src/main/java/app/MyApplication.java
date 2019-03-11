package app;

import android.app.Application;
import android.content.Context;

/**
 * 文件描述:
 *
 * @author Administrator
 * @date 2019/1/10
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
