package com.example.wantouch_project.forem.ui;

import android.view.View;
import android.view.ViewGroup;

import com.example.wantouch_project.forem.functionalInterfaces.ForemNullarySetter;

public interface ForemOnLongClickEventScope {
    static void render(View view, ForemNullarySetter fs) {
        ViewGroup parent = ForemFocusViewGroup.focusViewGroup;
        View self = ForemFocusView.focusView;
        view.setOnLongClickListener(v -> {
            ForemScope.render(parent, self, fs::set);
            return true;
        });
    }
}

