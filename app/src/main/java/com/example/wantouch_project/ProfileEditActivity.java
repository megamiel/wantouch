package com.example.wantouch_project;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.wantouch_project.forem.activitys.ForemActivity;
import com.example.wantouch_project.forem.annotation.Writer;
import com.example.wantouch_project.forem.views.HorizontalLayout;
import com.example.wantouch_project.forem.views.Root;
import com.example.wantouch_project.forem.views.VerticalLayout;

import java.util.Calendar;
import java.util.stream.IntStream;

@Writer("だん")
public class ProfileEditActivity extends ForemActivity {
    @Override
    public void render(Root root) {
        root.setBackgroundColor(Consts.baseBackgroundColor);
        space(0, 0, 3);
        this.<HorizontalLayout>create(() -> {
            layout(match_parent, 0, 20);
        }).render(() -> {
            this.<ImageView>create(() -> {
                layout(0, match_parent, 40);
                image(R.drawable.wantouch_logo_);
            });

            this.<VerticalLayout>create(() -> {
                layout(0, match_parent, 60);
            }).render(() -> {
                this.<HorizontalLayout>create(() -> {
                    layout(match_parent, 0, 50);
                }).render(() -> {
                    space(0, 0, 50);
                    this.<Button>create(() -> {
                        layout(0, match_parent, 45);
                        craftmincho();
                        text("保存");
                        textSize(30);
                    });
                });
                this.<EditText>create(dogNameEditText -> {
                    craftmincho();
                    hint("犬の名前");
                    textSize(35);
                });
            });
        });

        this.<HorizontalLayout>create(() -> {
            layout(match_parent, 0, 70);
        }).render(() -> {
            space(0, 0, 5);
            this.<VerticalLayout>create(() -> {
                layout(0, match_parent, 95);
            }).render(() -> {
                var calendar = Calendar.getInstance();
                IntStream.range(0, 5).forEach(i -> {
                    this.<HorizontalLayout>create(() -> {
                        layout(match_parent, 0, 20);
                    }).render(() -> {
                        IntStream.range(0, 3).forEach(j -> {
                            var newCalendar = (Calendar) calendar.clone();
                            var dayLater = i * 3 + j;
                            newCalendar.add(Calendar.DAY_OF_MONTH, dayLater);
                            var year = newCalendar.get(Calendar.YEAR);
                            var month = newCalendar.get(Calendar.MONTH) + 1;
                            var day = newCalendar.get(Calendar.DAY_OF_MONTH);
                            var displayDate = String.format("%d年\n%d月%d日", year, month, day);
                            this.<Button>create(() -> {
                                layout(0, match_parent, 31);
                                text(displayDate);
                                craftmincho();
                                onClick(() -> {
                                    text("おされたよ");
                                });
                            });
                        });
                    });
                });
            });
        });
    }
}
