package com.example.wantouch_project.animations;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.widget.TextView;

public class InputRequestAnimation {
    public static void start(TextView textView){
        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(Color.GRAY, Color.RED, Color.RED, Color.RED, Color.GRAY, Color.RED, Color.RED, Color.RED, Color.GRAY); // (2)
        animator.setEvaluator(new ArgbEvaluator());
        animator.addUpdateListener(anim -> {
            int color = (Integer) anim.getAnimatedValue();
            textView.setHintTextColor(color);
        });
        animator.setDuration(1700);
        animator.start();
    }
}
