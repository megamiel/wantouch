package com.example.wantouch_project.components;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wantouch_project.R;
import com.example.wantouch_project.backgrounds.CircleBorderBackground;
import com.example.wantouch_project.data_store.UserInfo;
import com.example.wantouch_project.data_store.firebase.StorageContoller;
import com.google.android.gms.maps.model.Circle;

import java.util.HashMap;

import forem.java.annotation.Writer;
import forem.java.drawable.Background;
import forem.java.ui.Component;
import forem.java.views.CircleImageView;
import forem.java.views.HorizontalLayout;
import forem.java.views.VerticalLayout;

@Writer({"ひかる", "だん"})
public class ChatComponent extends Component {
    public HashMap<String, String> chat;

    @Override
    public void export() {
        $.<VerticalLayout>create(() -> {
            layout(match_parent, wrap_content);
            padding(30);
        }).render(() -> {
            switch (chat.get("name")) {
                case "IGNORE" -> {
                    $.<TextView>create(() -> {
                        layout(match_parent, 500);
                        text("こちらはチャットルームです");
                        alignCenter();
                        craftmincho();
                        textSize(24);
                    });
                }
                default -> {
                    $.<HorizontalLayout>create(() -> {
                        layout(match_parent, wrap_content);
                    }).render(() -> {
                        space(0, 0, 5);
                        if (chat.get("name") == null) {
                            return;
                        }
                        switch (chat.get("name")) {
                            case "HOST" -> {
                                $.<CircleImageView>create(hostImage -> {
                                    layout(100, 100);
                                    padding(3);
                                    image(R.drawable.wantouch_logo_);
                                    new CircleBorderBackground();
                                    StorageContoller.getImageUri($, UserInfo.id, hostImage);
                                });
                            }
                            default -> {
                                $.<TextView>create(anotherName -> {
                                    craftmincho();
                                    layout(100, 100);
                                    text(chat.get("name"));
                                    textSize(35);
                                    align(center);
                                    new Background() {{
                                        setCornerRadius(500);
                                        setColor(rgb("ffaa50"));
                                    }};
                                });
                            }
                        }

                        space(0, 0, 3);
                        $.<TextView>create(() -> {
                            layout(wrap_content, match_parent);
                            text(chat.get("timeStamp"));
                            textSize(20);
                            textColor(Color.GRAY);
                            alignCenter();
                            craftmincho();
                        });
                    });
                    $.<HorizontalLayout>create(horizontalLayout -> {
                        layout(match_parent, wrap_content);
                    }).render(() -> {
                        space(150, 0);
                        switch (chat.get("isImage")) {
                            case "true" -> {
                                $.<ImageView>create(messageImage->{
                                    layout(match_parent,wrap_content);
                                    StorageContoller.getImageUri($,chat.get("message"),messageImage);
                                });
                            }
                            case "false" -> {
                                $.<TextView>create(anotherName -> {
                                    craftmincho();
                                    layout(match_parent, wrap_content);
                                    text(chat.get("message"));
                                    textSize(20);
                                });
                            }
                        }

                    });
                }
            }

        });
    }
}
