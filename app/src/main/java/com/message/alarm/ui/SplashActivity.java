package com.message.alarm.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.message.alarm.MainActivity;
import com.message.alarm.R;

public class SplashActivity extends Activity {
    private ImageView welcomeImg = null;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        welcomeImg = (ImageView) this.findViewById(R.id.welcome_img);
        AlphaAnimation anima = new AlphaAnimation(0.3f, 1.0f);//渐变透明动画效果
        anima.setDuration(8000);// 设置动画显示时间
        welcomeImg.startAnimation(anima);

        welcomeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip();
            }
        });

        anima.setAnimationListener(new AnimationImpl());

        player = MediaPlayer.create(this, R.raw.dream);
        player.start();
    }

    private class AnimationImpl implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            welcomeImg.setBackgroundResource(R.drawable.welcome);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            skip();
        }// 动画结束后跳转到别的页面

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }

    private void skip() {
        player.stop();
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtra("showed",true);
        startActivity(intent);
        finish();
    }
}
