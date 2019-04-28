package com.example.wangke.myapplication.fragment;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import com.example.wangke.myapplication.activities.GSYVideoActivity;
import com.example.wangke.myapplication.activities.VersionUpdateActivity;
import com.example.wangke.myapplication.activities.WeakActivity;
import com.example.wangke.myapplication.utils.DeviceUtils;
import com.example.wangke.myapplication.utils.FileProvider7;
import com.example.wangke.myapplication.utils.FileUtil;
import com.example.wangke.myapplication.utils.Tool;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TengxunFragment extends Fragment implements View.OnClickListener {
    //扇形菜单按钮
    private int res[] = {R.id.circle_menu_button_1, R.id.circle_menu_button_2, R.id.circle_menu_button_3, R.id.circle_menu_button_4, R.id.circle_menu_button_5};
    private List<ImageView> imageViews = new ArrayList<>();
    //菜单是否展开的flag,false表示没展开
    private boolean mFlag = false;
    private TextView textView;
    private TextView updateTextView, pictureTextView, videoPlayTextView, weakReferenceTextView, photographTextView;
    public static final int RC_CHOOSE_PHOTO = 2;
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
            ImageView imageView = (ImageView) view.findViewById(res[i]);
            imageView.setOnClickListener(this);
            imageViews.add(imageView);
        }

        showImageView = view.findViewById(R.id.img_show);
        updateTextView = view.findViewById(R.id.tv_update);
        pictureTextView = view.findViewById(R.id.tv_select_picture);
        videoPlayTextView = view.findViewById(R.id.tv_video_play);
        weakReferenceTextView = view.findViewById(R.id.tv_weak_references);
        photographTextView = view.findViewById(R.id.tv_photograph);

        updateTextView.setOnClickListener(this);
        pictureTextView.setOnClickListener(this);
        videoPlayTextView.setOnClickListener(this);
        weakReferenceTextView.setOnClickListener(this);
        photographTextView.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                    } else {
                        // We don't have permission so prompt the user
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
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
                    intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intentToPickPic, RC_CHOOSE_PHOTO);
                }
                break;
            case R.id.tv_photograph:
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //未授权，申请授权(从相册选择图片需要读取存储卡的权限)
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 3);
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    this.fileName = "Smart Campus"+format.format(date);

                    Intent intentToTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
                Uri uri = data.getData();
                String filePath = FileUtil.getFilePathByUri(getContext(), uri);

                if (!Tool.isEmpty(filePath)) {
                    //将照片显示在 ivImage上
                    Glide.with(this).load(filePath).into(showImageView);
                }
                break;
            case RC_TAKE_PHOTO:
                //将图片显示在ivImage上
                Glide.with(this).load(mTempPhotoPath).into(showImageView);
                break;
        }
    }
}
