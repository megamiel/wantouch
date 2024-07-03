package com.example.wantouch_project.backgrounds;

import android.graphics.Color;

import forem.java.annotation.Writer;
import forem.java.drawable.Background;

@Writer("だん")
public class CircleBorderBackground extends Background {
    {
        setCornerRadius(Float.MAX_VALUE);
        setStroke(2, Color.BLACK);
    }
}
