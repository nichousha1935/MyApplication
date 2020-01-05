package com.example.wangke.myapplication.fragment;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.activities.DragActivity;
import com.example.wangke.myapplication.activities.GSYVideoActivity;
import com.example.wangke.myapplication.activities.ListMultiVideoActivity;
import com.example.wangke.myapplication.activities.MapActivity;
import com.example.wangke.myapplication.activities.VersionUpdateActivity;
import com.example.wangke.myapplication.activities.WeakActivity;
import com.example.wangke.myapplication.activities.WebViewActivity;
import com.example.wangke.myapplication.utils.DeviceUtils;
import com.example.wangke.myapplication.utils.FileProvider7;
import com.example.wangke.myapplication.utils.FileUtil;
import com.example.wangke.myapplication.utils.ToastUtil;
import com.example.wangke.myapplication.utils.Tool;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.RequiresApi;

public class TengxunFragment extends Fragment implements View.OnClickListener {
    //扇形菜单按钮
    private int res[] = {R.id.circle_menu_button_1, R.id.circle_menu_button_2, R.id.circle_menu_button_3, R.id.circle_menu_button_4, R.id.circle_menu_button_5};
    private List<ImageView> imageViews = new ArrayList<>();
    //菜单是否展开的flag,false表示没展开
    private boolean mFlag = false;
    private TextView textView;
    private TextView updateTextView, pictureTextView, videoPlayTextView, weakReferenceTextView,
            photographTextView, haikangTextVIew, dragTextView, h5TextView, multiscreenTextView,
            apptoappTextview,mapTextView;
    public static final int RC_CHOOSE_PHOTO = 2;
    public static final int RC_UPDATE_APP = 3;
    private ImageView showImageView;
    public static final int RC_TAKE_PHOTO = 1;
    private String mTempPhotoPath;
    private Uri imageUri;
    private String fileName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tecent, null);
        for (int i = 0; i < res.length; i++) {
            ImageView imageView = view.findViewById(res[i]);
            imageView.setOnClickListener(this);
            imageViews.add(imageView);
        }

        showImageView = view.findViewById(R.id.img_show);
        updateTextView = view.findViewById(R.id.tv_update);
        pictureTextView = view.findViewById(R.id.tv_select_picture);
        videoPlayTextView = view.findViewById(R.id.tv_video_play);
        weakReferenceTextView = view.findViewById(R.id.tv_weak_references);
        photographTextView = view.findViewById(R.id.tv_photograph);
        haikangTextVIew = view.findViewById(R.id.tv_haikang);
        dragTextView = view.findViewById(R.id.tv_drag);
        h5TextView = view.findViewById(R.id.tv_h5);
        multiscreenTextView = view.findViewById(R.id.tv_multiscreen);
        apptoappTextview = view.findViewById(R.id.tv_apptoapp);
        mapTextView=view.findViewById(R.id.tv_map);

        updateTextView.setOnClickListener(this);
        pictureTextView.setOnClickListener(this);
        videoPlayTextView.setOnClickListener(this);
        weakReferenceTextView.setOnClickListener(this);
        photographTextView.setOnClickListener(this);
        haikangTextVIew.setOnClickListener(this);
        dragTextView.setOnClickListener(this);
        h5TextView.setOnClickListener(this);
        multiscreenTextView.setOnClickListener(this);
        apptoappTextview.setOnClickListener(this);
        mapTextView.setOnClickListener(this);

        textView = view.findViewById(R.id.tv_content);
        textView.setText("腾讯");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });

                    }
                }).start();
            }
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_h5:
                startActivity(new Intent(getContext(), WebViewActivity.class));
                break;
            case R.id.tv_drag:
                startActivity(new Intent(getContext(), DragActivity.class));
                break;
            case R.id.circle_menu_button_1:
                if (mFlag == false) {
                    showEnterAnim(100); //100为扇形半径dp值
                } else {
                    showExitAnim(100);
                }
                break;
            case R.id.tv_update:
                int permissionStrorage = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                int permissionCalendar = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionStrorage != PackageManager.PERMISSION_GRANTED || permissionCalendar != PackageManager.PERMISSION_GRANTED) {
                    //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true,它在用户选择"不再询问"的情况下返回false
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_CALENDAR)) {
                        ToastUtil.showToast(getContext(), "请赋予App读写权限");
                    } else {
                        // We don't have permission so prompt the user
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, RC_UPDATE_APP);
                    }
                } else {
                    startActivity(new Intent(getContext(), VersionUpdateActivity.class));
                }
                break;
            case R.id.tv_select_picture:
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //未授权，申请授权(从相册选择图片需要读取存储卡的权限)
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RC_CHOOSE_PHOTO);
                } else {
                    //已授权，获取照片
                    Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
//                    intentToPickPic.setType("video/*;image/*");
//                    intentToPickPic.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //intentToPickPic.setAction(Intent.ACTION_GET_CONTENT);
                    intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*;video/*");
                    startActivityForResult(intentToPickPic, RC_CHOOSE_PHOTO);
                }
                break;
            case R.id.tv_photograph:
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //未授权，申请授权(从相册选择图片需要读取存储卡的权限)
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, RC_TAKE_PHOTO);
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    this.fileName = "Smart_Campus_" + format.format(date);

                    // Intent intentToTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Intent intentToTakePhoto = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    File fileDir = new File(Environment.getExternalStorageDirectory() + File.separator + "photoTest" + File.separator);
                    if (!fileDir.exists()) {
                        fileDir.mkdirs();
                    }

                    File photoFile = new File(fileDir, fileName);
                    mTempPhotoPath = photoFile.getAbsolutePath();
                    imageUri = FileProvider7.getUriForFile(getContext(), photoFile);
                    intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intentToTakePhoto, RC_TAKE_PHOTO);
                }

                break;
            case R.id.tv_video_play:
                startActivity(new Intent(getContext(), GSYVideoActivity.class));
                break;
            case R.id.tv_weak_references:
                startActivity(new Intent(getContext(), WeakActivity.class));
                break;
//            case R.id.tv_haikang:
//                startActivity(new Intent(getContext(), HaiKangActivity.class));
//                break;
            case R.id.tv_multiscreen:
                startActivity(new Intent(getContext(), ListMultiVideoActivity.class));
//                if (!Settings.canDrawOverlays(getContext())) {
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                            Uri.parse("package:" + getContext().getPackageName()));
//                    startActivityForResult(intent, 200);
//                }
//                NotificationUtils.getInstance().showNotification(getContext(),true,true);
//                NotificationUtils.getInstance().playSound(getContext());
//                NotificationUtils.getInstance().playVibrator(getContext());
                break;
            case R.id.tv_apptoapp:
                if (isInstalledApp(getContext())) {
                    //跳转到app 指定的界面，并且传递参数
                    ComponentName componentName = new ComponentName(appPackageName, "com.jskingen.jsxf_water_collection.LoginActivity");
                    Intent intent = new Intent();
                    // intent.putExtra("test", "你好 ，MainActivity");
                    intent.setComponent(componentName);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);


                    //根据包名来跳转  不能指定跳转界面
/*                    Intent intent = getPackageManager().getLaunchIntentForPackage(appPackageName);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("test", "你好 ，MainActivity");
                        startActivity(intent);*/

                } else {
                    goToBrowser();
                }
                break;
            case R.id.tv_map:
                startActivity(new Intent(getContext(), MapActivity.class));
                break;
            default:
                break;
        }
    }

    //显示扇形菜单的属性动画
    private void showEnterAnim(int dp) {
        //for循环来开始小图标的出现动画
        for (int i = 1; i < res.length; i++) {
            AnimatorSet set = new AnimatorSet();
            double x = -Math.cos(0.5 / (res.length - 2) * (i - 1) * Math.PI) * DeviceUtils.dip2px(getContext(), dp);
            double y = -Math.sin(0.5 / (res.length - 2) * (i - 1) * Math.PI) * DeviceUtils.dip2px(getContext(), dp);
            set.playTogether(
                    ObjectAnimator.ofFloat(imageViews.get(i), "translationX", (float) (x * 0.25), (float) x),
                    ObjectAnimator.ofFloat(imageViews.get(i), "translationY", (float) (y * 0.25), (float) y),
                    ObjectAnimator.ofFloat(imageViews.get(i), "alpha", 0, 1).setDuration(2000)
            );
            set.setInterpolator(new BounceInterpolator());
            set.setDuration(500).setStartDelay(100 * i);
            set.start();
        }
        //转动加号大图标本身45°
        ObjectAnimator rotate = ObjectAnimator.ofFloat(imageViews.get(0), "rotation", 0, 45).setDuration(300);
        rotate.setInterpolator(new BounceInterpolator());
        rotate.start();

        //菜单状态置打开
        mFlag = true;
    }

    //退出扇形菜单的属性动画
    private void showExitAnim(int dp) {
        //for循环来开始小图标的出现动画
        for (int i = 1; i < res.length; i++) {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(imageViews.get(i), "translationX", 0, 0),
                    ObjectAnimator.ofFloat(imageViews.get(i), "translationY", 0, 0)
                    , ObjectAnimator.ofFloat(imageViews.get(i), "alpha", 1, 0).setDuration(2000)
            );
            set.setInterpolator(new BounceInterpolator());
            set.setDuration(500).setStartDelay(100 * i);
            set.start();
        }
        //转动加号大图标本身45°
        ObjectAnimator rotate = ObjectAnimator.ofFloat(imageViews.get(0), "rotation", 45, 0).setDuration(300);
        rotate.setInterpolator(new BounceInterpolator());
        rotate.start();

        //菜单状态置打开
        mFlag = false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_CHOOSE_PHOTO:
                if (!Tool.isEmpty(data)) {
                    Uri uri = data.getData();
                    String filePath = FileUtil.getFilePathByUri(getContext(), uri);

                    if (!Tool.isEmpty(filePath)) {
                        //将照片显示在 ivImage上
                        Glide.with(showImageView.getContext()).load(filePath).into(showImageView);
                    }
                }
                break;
            case RC_TAKE_PHOTO:
                //将图片显示在ivImage上
                Glide.with(this).load(mTempPhotoPath).into(showImageView);
                break;
            default:
                break;
        }
    }

    private String appPackageName = "com.jskingen.jsxf_water_collection";

    public boolean isInstalledApp(Context myContext) {
        PackageManager myPackageMgr = myContext.getPackageManager();
        try {
            myPackageMgr.getPackageInfo(appPackageName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            ToastUtil.showToast(myContext, "没有安装 app: " + appPackageName);
            return (false);
        }
        return true;
    }

    //应用市场下载
    public void goToMarket(Context myContext) {
        ToastUtil.showToast(myContext, "没有安装 app  市场下载");
        Uri marketUri = Uri.parse("market://details?id=" + appPackageName);
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myContext.startActivity(myIntent);
        return;
    }

    //网页下载
    public void goToBrowser() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("http://218.75.249.59:31010/hnxf/down1/JSXF_SY.apk");
        intent.setData(content_url);
        startActivity(intent);
    }

    public static String sha1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
            }
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
