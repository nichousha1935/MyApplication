package com.example.wangke.myapplication.refresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * 文件描述:
 *
 * @author Administrator
 * @date 2019/1/9
 */
public class ind4Header extends LinearLayout implements RefreshHeader {
    private LottieAnimationView lottieAnimationView;

    public ind4Header(Context context) {
        super(context);
        initView(context);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        lottieAnimationView.playAnimation();
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        lottieAnimationView.cancelAnimation();
        return 500;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

    }

    private void initView(Context context) {
        setGravity(Gravity.CENTER);
        lottieAnimationView = new LottieAnimationView(context);
        lottieAnimationView.setAnimation("a_very_angry_sushi.json");
        addView(lottieAnimationView, LayoutParams.MATCH_PARENT, DensityUtil.dp2px(20));
        //addView(new View(context), DensityUtil.dp2px(20), DensityUtil.dp2px(20));
        setMinimumHeight(DensityUtil.dp2px(60));
    }
}
