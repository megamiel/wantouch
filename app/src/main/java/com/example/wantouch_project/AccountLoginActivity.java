package com.example.wantouch_project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.graphics.drawable.DrawableContainerCompat;

import com.example.wantouch_project.forem.activitys.ForemActivity;
import com.example.wantouch_project.forem.annotation.Writer;
import com.example.wantouch_project.forem.views.HorizontalLayout;
import com.example.wantouch_project.forem.views.Root;

@Writer("そうま")
public class AccountLoginActivity extends ForemActivity {
    @Override
    public void render(Root root) {
        root.setBackgroundColor(Consts.baseBackgroundColor);//画面全体のバックグラウンドカラーを変更
        space(0,0,20);
        this.<HorizontalLayout>create(()->{
            layout(match_parent,0,10);
        }).render(()->{
            space(0,0,10);
            this.<EditText>create(()->{
                layout(0,match_parent,80);
                craftmincho();
                hint("id");
                alignBottom();
                textSize(30);
            });
        });
        this.<HorizontalLayout>create(()->{
            layout(match_parent,0,10);
        }).render(()->{
            space(0,0,10);
            this.<EditText>create(()->{
                layout(0,match_parent,80);
                craftmincho();
                hint("パスワード");
                alignBottom();
                textSize(30);
            });
        });

        space(0,0,5);

        this.<HorizontalLayout>create(()->{
            layout(match_parent,0,10);
        }).render(()->{
            space(0,0,10);
            this.<Button>create(a->{
                layout(0,match_parent,80);
                craftmincho();
                text("ログイン");
                textSize(30);
//                backgroundColor(Color.rgb(0xFF, 0xA5, 0x00));
                backgroundResource(R.drawable.radius);

                onClick(()->{
                     startActivity(WanTouchHomeActivity.class);
                });
            });
        });


        space(0,0,5);
        this.<TextView>create(()->{
            layout(match_parent,0,10);
            craftmincho();
            text("作成したい方はこちら");
            align(center,top);
            textColor(Color.BLUE);
            textSize(30);
            onClick(()->{
                startActivity(AccountCreateActivity.class);
                finish();
            });
        });
    }

    @Override
    public void onBackPressed(){

    }
}
