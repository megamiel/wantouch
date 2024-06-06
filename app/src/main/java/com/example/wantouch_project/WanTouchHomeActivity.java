package com.example.wantouch_project;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.wantouch_project.forem.activitys.ForemActivity;
import com.example.wantouch_project.forem.annotation.Writer;
import com.example.wantouch_project.forem.views.HorizontalLayout;
import com.example.wantouch_project.forem.views.Root;
import com.example.wantouch_project.forem.views.VerticalLayout;

@Writer("だん")
public class WanTouchHomeActivity extends ForemActivity {
    @Override
    public void render(Root root) {
        root.setBackgroundColor(Consts.baseBackgroundColor);
        this.<VerticalLayout>create(()-> {
            layout(match_parent, 0, 8);
//            backgroundResource(R.drawable.border_brown);
            backgroundColor(Color.rgb(0xff,0x0,0xff));
        }).render(()->{
            space(0,0,5);
            this.<HorizontalLayout>create(() -> {
                layout(match_parent,0,90);
            }).render(() -> {
                space(0, 0, 1);
                this.<ImageView>create(profileEditButton -> {
                    layout(0, match_parent, 15);
                    image(R.drawable.wantouch_logo_);
                    backgroundResource(R.drawable.circle);
                    onClick(()-> {
                        startActivity(ProfileEditActivity.class);
                    });
                });
            });
            space(0,0,5);
        });

        space(0,0,10);

        this.<HorizontalLayout>create(()->{
            layout(match_parent,0,20);
        }).render(()->{
            this.<TextView>create(()->{
                layout(0,match_parent,70);
                craftmincho();
                text("ワンタッチ");
                textSize(50);
                // タイトルテキストを中央揃え、左寄せとする
                align(center,right);
                // フォントの変更が必要
            });
            this.<ImageView>create(()->{
                layout(0,match_parent,20);
                image(R.drawable.wantouch_logo_);
                // タイトルロゴの回転率を指定
                rotate(10);
            });
        });

        space(0,0,10);

        this.<VerticalLayout>create(()->{
            layout(match_parent,0,70);
        }).render(()->{
            this.<HorizontalLayout>create(()->{
                layout(match_parent,0,20);
            }).render(()->{
                space(0,0,15);
                this.<Button>create(()->{
                    layout(0,match_parent,70);
                    craftmincho();
                    text("さがす");
                    textSize(35);
                    backgroundResource(R.drawable.radius);
                    onClick(()->{
                        startActivity(RoomListDisplayActivity.class);
                    });
                });
                space(0,0,15);
            });
            space(0,0,5);
            this.<HorizontalLayout>create(()->{
                layout(match_parent,0,20);
            }).render(()->{
                space(0,0,15);
                this.<Button>create(()->{
                    layout(0,match_parent,70);
                    craftmincho();
                    text("つくる");
                    textSize(35);
                    backgroundResource(R.drawable.radius);
                    onClick(()->{
                        startActivity(MakeRoomActivity.class);
                    });
                });
                space(0,0,15);
            });
        });
    }
    @Override
    public void onBackPressed(){

    }
}
