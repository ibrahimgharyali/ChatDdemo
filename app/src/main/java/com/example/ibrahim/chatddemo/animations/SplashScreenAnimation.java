package com.example.ibrahim.chatddemo.animations;

import android.animation.ObjectAnimator;
import android.view.View;

import com.daimajia.androidanimations.library.BaseViewAnimator;

/**
 * By katepratik on 22/3/17.
 */

public class SplashScreenAnimation extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, "scaleY", 1f, 0.8f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, "scaleX", 1f, 0.8f, 1f);
        scaleX.setRepeatCount(1);
        scaleY.setRepeatCount(1);
        getAnimatorAgent().playTogether(scaleY, scaleX);
    }
}
