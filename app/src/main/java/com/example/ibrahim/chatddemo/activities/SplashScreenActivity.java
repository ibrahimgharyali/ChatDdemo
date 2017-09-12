package com.example.ibrahim.chatddemo.activities;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.ibrahim.chatddemo.R;


/**
 * By aakash on 19/4/16.
 */

public class SplashScreenActivity extends Activity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        animateAndGo();
        super.onCreate(savedInstanceState);
    }

    private void animateAndGo() {
        setContentView(R.layout.activity_splash_screen);
        ImageView logo = findViewById(R.id.logo);
        YoYo.with(Techniques.Pulse)
                .withListener(
                        new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                Intent m = new Intent(SplashScreenActivity.this, ChatActivity.class);
                                m.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(m);
                                finish();
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        }
                )
                .delay(800)
                .duration(1000)
                .repeat(1)
                .playOn(logo);

    }
}