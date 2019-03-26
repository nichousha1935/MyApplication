package com.example.wangke.myapplication.mvp.p;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.example.wangke.myapplication.mvp.v.ISplashView;

public class PresenterSplash implements IPresenterSplash {
    private ISplashView iSplashView;

    public PresenterSplash(ISplashView iSplashView) {
        this.iSplashView = iSplashView;
    }

    @Override
    public void startAction(Animation animation) {
        animation = new TranslateAnimation(0, 500, 0, 500);
        animation.setDuration(3000);
        iSplashView.startAnim(animation);
    }

    @Override
    public void endAction(Animation animation) {
        animation = new TranslateAnimation(500, 0, 500, 0);
        animation.setDuration(3000);
        iSplashView.endAnim(animation);
    }
}
