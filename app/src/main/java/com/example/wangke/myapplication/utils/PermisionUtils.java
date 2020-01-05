package com.example.wangke.myapplication.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

/**
 * 文件描述:
 *
 * @author Administrator
 * @date 2019/1/15
 */
public class PermisionUtils {
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     * <p>
     *      * @param com.example.wangke.myapplication.activity
     *    
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
       // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE , REQUEST_EXTERNAL_STORAGE);
        }
    }


    public static  void requestOverlayPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
//            if (!Settings.canDrawOverlays(context)) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + context.getPackageName()));
//                context.startActivityForResult(intent, REQUEST_OVERLAY);
//            } else {
//
//            }
        }
    }
}
