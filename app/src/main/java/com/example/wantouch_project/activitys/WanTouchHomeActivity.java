package com.example.wantouch_project.activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wantouch_project.backgrounds.BaseBackground;
import com.example.wantouch_project.backgrounds.CircleBorderBackground;
import com.example.wantouch_project.backgrounds.DefaultButtonBackground;
import com.example.wantouch_project.R;
import com.example.wantouch_project.data_store.UserInfo;
import com.example.wantouch_project.data_store.firebase.FirestoreController;

import java.util.Map;
import java.util.Optional;

import forem.java.activitys.ForemActivity;
import forem.java.annotation.Writer;
import forem.java.views.CircleImageView;
import forem.java.views.HorizontalLayout;
import forem.java.views.Root;
import forem.java.views.VerticalLayout;

@Writer("だん")
public class WanTouchHomeActivity extends ForemActivity {
    boolean canPushed=true;
    @Override
    public void render(Root root) {
        FirestoreController.db.collection("users").document(UserInfo.id).get().addOnSuccessListener(task->{
            Map<String,Object> userData=task.getData();
            UserInfo.dogName=userData.get("dogName").toString();
        });


        onBack=none;
        new BaseBackground();
        $.<VerticalLayout>create(() -> {
            layout(match_parent, 0, 8);
            backgroundColor(Color.rgb(0xff, 0x0, 0xff));
        }).render(() -> {
            space(0, 0, 5);
            $.<HorizontalLayout>create(() -> {
                layout(match_parent, 0, 90);
            }).render(() -> {
                space(0, 0, 1);
                $.<CircleImageView>create(profileEditButton -> {
                    layout(0, match_parent, 15);
                    image(R.drawable.wantouch_logo_);
                    padding(10);
                    new CircleBorderBackground();
                    onClick(() -> {
                        if(canPushed) {
                            startThread(() -> {
                                canPushed = false;
                                sleep(1000);
                                canPushed=true;
                            });
                            startActivity(ProfileEditActivity.class);
                        }
                    });
                });
            });
            space(0, 0, 5);
        });

        space(0, 0, 8);

        $.<HorizontalLayout>create(() -> {
            layout(match_parent, 0, 20);
        }).render(() -> {
            $.<TextView>create(() -> {
                layout(0, match_parent, 70);
                craftmincho();
                text("ワンタッチ");
                textSize(50);
                align(center, right);
            });
            $.<ImageView>create(() -> {
                layout(0, match_parent, 20);
                image(R.drawable.wantouch_logo_);
                rotate(10);
            });
        });

        space(0, 0, 10);

        $.<VerticalLayout>create(() -> {
            layout(match_parent, 0, 70);
        }).render(() -> {
            $.<HorizontalLayout>create(() -> {
                layout(match_parent, 0, 20);
            }).render(() -> {
                space(0, 0, 15);
                $.<Button>create(() -> {
                    layout(0, match_parent, 70);
                    craftmincho();
                    text("さがす");
                    textSize(35);
//                    backgroundResource(R.drawable.radius);
                    new DefaultButtonBackground();
                    onClick(() -> {
                        if(canPushed) {
                            startThread(() -> {
                                canPushed = false;
                                sleep(1000);
                                canPushed=true;
                            });
                            startActivity(RoomListActivity.class);
                        }

                    });
                });
                space(0, 0, 15);
            });
            space(0, 0, 5);
            $.<HorizontalLayout>create(() -> {
                layout(match_parent, 0, 20);
            }).render(() -> {
                space(0, 0, 15);
                $.<Button>create(() -> {
                    layout(0, match_parent, 70);
                    craftmincho();
                    text("つくる");
                    textSize(35);
//                    backgroundResource(R.drawable.radius);
                    new DefaultButtonBackground();
                    onClick(() -> {
                        if(canPushed) {
                            startThread(() -> {
                                canPushed = false;
                                sleep(1000);
                                canPushed=true;
                            });
                            startActivity(MakeRoomActivity.class);
                        }
                    });
                });
                space(0, 0, 15);
            });
        });
    }
}
