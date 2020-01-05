package com.example.wangke.myapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;

import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.adapter.CommonAdapter;
import com.example.wangke.myapplication.adapter.ViewHolder;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

public class MultiscreenActivity extends BaseActivity {
    private GridView gv_player;
    private List<String> listPlayer = new ArrayList<>();
    private CommonAdapter<String> commonAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.activity_multiscreen);
    }

    @Override
    public void initView() {
        gv_player = findViewById(R.id.gv_player);
    }

    @Override
    public void initBind() {

    }

    @Override
    public void initData() {
        listPlayer.add("http://alvideo.ippzone.com/zyvd/98/90/b753-55fe-11e9-b0d8-00163e0c0248");
        listPlayer.add("http://alvideo.ippzone.com/zyvd/98/90/b753-55fe-11e9-b0d8-00163e0c0248");
        listPlayer.add("http://alvideo.ippzone.com/zyvd/98/90/b753-55fe-11e9-b0d8-00163e0c0248");
        listPlayer.add("http://alvideo.ippzone.com/zyvd/98/90/b753-55fe-11e9-b0d8-00163e0c0248");
        listPlayer.add("http://alvideo.ippzone.com/zyvd/98/90/b753-55fe-11e9-b0d8-00163e0c0248");
        listPlayer.add("http://alvideo.ippzone.com/zyvd/98/90/b753-55fe-11e9-b0d8-00163e0c0248");
        commonAdapter = new CommonAdapter<String>(MultiscreenActivity.this,R.layout.item_multiscreen,listPlayer) {
            @Override
            public void convert(ViewHolder viewHolder, String s) {
                StandardGSYVideoPlayer gsyVideoPlayer=viewHolder.getView(R.id.video_player);
                gsyVideoPlayer.setUpLazy(s, true, null, null, "这是title");
               //增加title
                gsyVideoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
               //设置返回键
                gsyVideoPlayer.getBackButton().setVisibility(View.VISIBLE);
               //设置全屏按键功能
               // gsyVideoPlayer.getFullscreenButton().setOnClickListener(v -> gsyVideoPlayer.startWindowFullscreen(MultiscreenActivity.this, false, true));
                //防止错位设置
                //gsyVideoPlayer.setPlayTag(TAG);
                gsyVideoPlayer.setPlayPosition(viewHolder.getPosition());
                //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
                gsyVideoPlayer.setAutoFullWithSize(false);
                //音频焦点冲突时是否释放
                gsyVideoPlayer.setReleaseWhenLossAudio(false);
                //全屏动画
                gsyVideoPlayer.setShowFullAnimation(true);
                //小屏时不触摸滑动
                gsyVideoPlayer.setIsTouchWiget(false);
                gsyVideoPlayer.startPlayLogic();
            }
        };
        gv_player.setAdapter(commonAdapter);
    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }
}
