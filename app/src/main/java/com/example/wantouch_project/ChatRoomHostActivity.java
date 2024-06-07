package com.example.wantouch_project;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.wantouch_project.forem.activitys.ForemActivity;
import com.example.wantouch_project.forem.annotation.Writer;
import com.example.wantouch_project.forem.views.HorizontalLayout;
import com.example.wantouch_project.forem.views.Root;
import com.example.wantouch_project.forem.views.VerticalLayout;
import com.example.wantouch_project.forem.views.WebComponentView;

@Writer("ひかる")
public class ChatRoomHostActivity extends ForemActivity {

    @Override
    public void render(Root root) {
        root.setBackgroundColor(Consts.baseBackgroundColor);
        this.<HorizontalLayout>create(()->{
            layout(match_parent,0,8); //大本もとき
            backgroundColor(Color.DKGRAY);
        }).render(()->{
            this.<VerticalLayout>create(()->{
                layout(0,match_parent,35);//中森もとき
            }).render(()->{
                space(0,0,8);
                this.<HorizontalLayout>create(()->{
                    layout(match_parent,0,84);//小森もとき
                }).render(()->{
                    space(0,0,10);
                    this.<Button>create(()->{
                        layout(0,match_parent,75);//極小もとき
                        craftmincho();
                        textSize(25);
                        text("退出");
                        onClick(()->{
                            finish();
                        });
                    });
                });
            });
            //メンバー表示
            this.<VerticalLayout>create(()->{
                layout(0,match_parent,50);//大森もとき
            }).render(()->{
                this.<HorizontalLayout>create(()->{
                    space(0,0,8);
                    layout(match_parent,0,86);
                }).render(()->{
                    this.<ImageView>create(()->{
                        layout(0,match_parent,25);
                        image(R.drawable.wantouch_logo_);
                    });
                    this.<ImageView>create(()->{
                        layout(0,match_parent,25);
                        image(R.drawable.wantouch_logo_);
                    });
                    this.<ImageView>create(()->{
                        layout(0,match_parent,25);
                        image(R.drawable.wantouch_logo_);
                    });
                    this.<ImageView>create(()->{
                        layout(0,match_parent,25);
                        image(R.drawable.wantouch_logo_);
                    });
                });
            });
            //設定
            this.<VerticalLayout>create(()->{
                layout(0,match_parent,15);
            }).render(()->{
                space(0,0,8);
                this.<ImageView>create(()->{
                    layout(match_parent,0,86);
                    image(R.drawable.setting_button);
                });
            });
        });


        this.<WebComponentView>create(()->{
            layout(match_parent,0,84);
            url("https://megamiel.github.io/dotcom");
        });

        this.<VerticalLayout>create(()->{
            layout(0,0,8);
            backgroundColor(Color.DKGRAY);
        });
    }
    @Override
    public void onBackPressed(){

    }
}