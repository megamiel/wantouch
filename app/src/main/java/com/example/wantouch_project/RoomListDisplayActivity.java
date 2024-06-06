package com.example.wantouch_project;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wantouch_project.forem.activitys.ForemActivity;
import com.example.wantouch_project.forem.annotation.Writer;
import com.example.wantouch_project.forem.ui.ForemComponent;
import com.example.wantouch_project.forem.ui.ForemListView;
import com.example.wantouch_project.forem.views.HorizontalLayout;
import com.example.wantouch_project.forem.views.Root;
import com.example.wantouch_project.forem.views.VerticalLayout;

import java.util.stream.IntStream;

@Writer({"ひかる","だん"})
public class RoomListDisplayActivity extends ForemActivity {
    @Writer("ひかる")
    ForemComponent roomInfoComponent=args->{
        return this.<HorizontalLayout>create(() -> {
            layout(match_parent, 450);
            backgroundResource(R.drawable.border_black);
        }).render(() -> {
            this.<ImageView>create(() -> {
                layout(0, match_parent, 30);
                image(R.drawable.wantouch_logo_);
            });
            space(0, match_parent, 5);
            this.<TextView>create(() -> {
                layout(0, match_parent, 35);
                craftmincho();
                text("みるく");
                textSize(25);
                align(center, left);
            });
            this.<VerticalLayout>create(() -> {
                layout(0, match_parent, 30);
            }).render(() -> {
                this.<TextView>create(() -> {
                    layout(match_parent, 0, 30);
                    craftmincho();
                    text("6月4日");
                    textSize(20);
                    align(center,bottom);
                });
                this.<TextView>create(timeText -> {
                    layout(match_parent, 0, 25);
                    craftmincho();
                    text("17:00");
                    textSize(20);
                    align(center, bottom);
                });
                this.<ImageView>create(() -> {
                    layout(match_parent, 0, 20);
                    image(R.drawable.tilde);
                });
                this.<TextView>create(() -> {
                    layout(match_parent, 0, 25);
                    craftmincho();
                    text("19:00");
                    textSize(20);
                    align(center,top);
                });
            });
        });
    };


    @Writer("だん")
    @Override
    public void render(Root root){
        root.setBackgroundColor(Consts.baseBackgroundColor);
        ForemListView.create(()-> {
            layout(match_parent,match_parent);
        }).render(()->{
            // 実際にはIntStreamではなく、Firebaseから公開中の部屋情報一覧を取得し、forEachで表示する予定
            var roomNum=100;
            IntStream.range(0,roomNum).forEach(i-> {
                roomInfoComponent.export();
            });
        });
    }
}