package com.example.wantouch_project;

import android.graphics.Color;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wantouch_project.forem.activitys.ForemActivity;
import com.example.wantouch_project.forem.annotation.Writer;
import com.example.wantouch_project.forem.views.HorizontalLayout;
import com.example.wantouch_project.forem.views.Root;
import com.example.wantouch_project.forem.views.VerticalLayout;

import java.util.Calendar;

@Writer("そうま")
public class MakeRoomActivity extends ForemActivity {

    @Override
    public void render(Root root) {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        //現在時刻取得
        root.setBackgroundColor(Consts.baseBackgroundColor);
        this.<HorizontalLayout>create(() -> {
            layout(match_parent, 0, 15);

        }).render(() -> {
            this.<ImageView>create(dogIcon -> {
                layout(0, match_parent, 30);
                image(R.drawable.wantouch_logo_);
                new CircleBorderBackground();
                onClick(() -> {
                    //アイコン変更に遷移したくて。
                    startActivity(ImagePickActivity.class);
                });

            });

            space(0, 0, 20);

            this.<VerticalLayout>create(() -> {
                layout(0, match_parent, 50);
            }).render(() -> {
                this.<EditText>create(() -> {
                    layout(match_parent, 0, 50);
                    hint("犬の名前");
                    craftmincho();
                    alignLeft();
                    textSize(38);
                });
                this.<HorizontalLayout>create(() -> {
                    layout(match_parent, 0, 50);
                    onClick(() -> {
                        System.out.println("おされたよ");
                        var month = calendar.get(Calendar.MONTH) + 1;
                        var date = calendar.get(Calendar.DATE);
                        var hour = calendar.get(Calendar.HOUR);




                    });
                }).render(() -> {
                    this.<TextView>create(() -> {
                        layout(0, match_parent, 70);
                        text("期間");
                        craftmincho();
                        textSize(38);
                        alignLeft();

                    });
                    this.<TextView>create(() -> {
                        layout(0, match_parent, 25);
                        text("▼");
                        craftmincho();
                        textSize(38);
                        alignRight();
                    });
                });

            });

        });
        this.<VerticalLayout>create(() -> {
            layout(match_parent, 0, 70);
        }).render(() -> {
            //マップを入れる
        });
        this.<Button>create(() -> {
            layout(match_parent, 0, 15);
            text("作成");
            textSize(38);
            craftmincho();
            backgroundColor(Color.rgb(0xFF, 0xA5, 0x00));
        });


    }

}
