package com.example.wantouch_project;

import android.graphics.Color;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.wantouch_project.forem.activitys.ForemActivity;
import com.example.wantouch_project.forem.annotation.Writer;
import com.example.wantouch_project.forem.views.HorizontalLayout;
import com.example.wantouch_project.forem.views.Root;


@Writer("そうま")
public class AccountCreateActivity extends ForemActivity {
    @Override
    public void render(Root root) {
        //画面全体のバックグラウンドカラーを変更
        root.setBackgroundColor(Consts.baseBackgroundColor);
        space(0, 0, 20);
        this.<HorizontalLayout>create(() -> {
            layout(match_parent, 0, 10);
        }).render(() -> {
            space(0, 0, 10);
            this.<EditText>create(() -> {
                layout(0, match_parent, 80);
                craftmincho();
                hint("id");
                alignBottom();
                textSize(30);
            });
        });
        this.<HorizontalLayout>create(() -> {
            layout(match_parent, 0, 10);
        }).render(() -> {
            space(0, 0, 10);
            this.<EditText>create(() -> {
                layout(0, match_parent, 80);
                craftmincho();
                hint("パスワード");
                alignBottom();
                textSize(30);
            });
        });

        space(0, 0, 5);

        this.<HorizontalLayout>create(() -> {
            layout(match_parent, 0, 10);
        }).render(() -> {
            space(0, 0, 10);
            this.<Button>create(() -> {
                layout(0, match_parent, 80);
                craftmincho();
                text("作成");
                textSize(30);
//                backgroundColor(Color.rgb(0xFF, 0xA5, 0x00));
                backgroundResource(R.drawable.radius);
                onClick(() -> {
                    startActivity(WanTouchHomeActivity.class);
                });
            });
        });

        space(0,0,5);
        this.<TextView>create(() -> {
            layout(match_parent, 0, 10);
            craftmincho();
            text("ログインしたい方はこちら");
            align(center, top);
            textColor(Color.BLUE);
            textSize(30);
            onClick(() -> {
                startActivity(AccountLoginActivity.class);
                finish();
            });
        });

//        this.<WebComponentView>create(webView->{
//            layout(match_parent,0,30);
//            url("https://megamiel.github.io/kamoquB");
//        });

    }

    @Override
    public void onBackPressed() {

    }

}
