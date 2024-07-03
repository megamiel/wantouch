package com.example.wantouch_project.activitys;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;


import com.example.wantouch_project.R;
import com.example.wantouch_project.animations.InputRequestAnimation;
import com.example.wantouch_project.backgrounds.BaseBackground;
import com.example.wantouch_project.backgrounds.CircleBorderBackground;
import com.example.wantouch_project.data_store.UserInfo;
import com.example.wantouch_project.data_store.firebase.FirestoreController;
import com.example.wantouch_project.data_store.firebase.StorageContoller;

import java.util.Calendar;
import java.util.stream.IntStream;

import forem.java.activitys.ForemActivity;
import forem.java.annotation.Writer;
import forem.java.views.CircleImageView;
import forem.java.views.HorizontalLayout;
import forem.java.views.Root;
import forem.java.views.VerticalLayout;

@Writer("だん")
public class ProfileEditActivity extends ForemActivity {
    private static final int REQUEST_GALLERY = 0;
    private boolean isImageSelected =false;

    CircleImageView[] profileImage = varType(CircleImageView.class);

    @Override
    public void render(Root root) {
        new BaseBackground();
        space(0, 0, 3);
        $.<HorizontalLayout>create(() -> {
            layout(match_parent, 0, 20);
            marginBottom(50);
        }).render(() -> {
            space(0, 0, 3);
            $.<CircleImageView>create(profileImage, uriView -> {
                layout(0, match_parent, 40);
                image(R.drawable.wantouch_logo_);
                StorageContoller.getImageUri(this, UserInfo.id, uriView);
                new CircleBorderBackground();
                padding(2);
                onClick(() -> {
                    // 画像選択時の処理はここを参考にしてね①
                    Intent intent = new Intent();
                    intent.setType("image/*");//ギャラリー内で画像のみが表示される
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, REQUEST_GALLERY);
                });
            });
            space(0, 0, 3);

            $.<VerticalLayout>create(() -> {
                layout(0, match_parent, 60);
            }).render(() -> {
                var dogNameInput = varType(EditText.class);
                $.<HorizontalLayout>create(() -> {
                    layout(match_parent, 0, 60);
                }).render(() -> {
                    space(0, 0, 50);
                    $.<Button>create(() -> {
                        layout(0, match_parent, 35);
                        craftmincho();
                        text("保存");
                        textSize(24);
                        onClick(() -> {
                            String dogName = dogNameInput[v].getText().toString();
                            if (!dogName.equals("")) {
                                UserInfo.dogName = dogName;
                                FirestoreController.db.collection("users").document(UserInfo.id).update("dogName",dogName);
                                if(isImageSelected) {
                                    StorageContoller.uploadImage(UserInfo.id, profileImage[v]);
                                }
                                finish();
                            } else {
                                InputRequestAnimation.start(dogNameInput[v]);
                            }
                        });
                    });
                });
                space(0,0,40);
                $.<EditText>create(dogNameInput, () -> {
                    layout(match_parent, wrap_content);
                    marginRight(100);
                    craftmincho();
                    hint("犬の名前");
                    if (UserInfo.dogName != null) {
                        text(UserInfo.dogName);
                    }
                    textSize(30);
                });
            });
        });

        $.<HorizontalLayout>create(() -> {
            layout(match_parent, 0, 70);
        }).render(() -> {
            space(0, 0, 5);
            $.<VerticalLayout>create(() -> {
                layout(0, match_parent, 95);
            }).render(() -> {
                var calendar = Calendar.getInstance();
                IntStream.range(0, 5).forEach(i -> {
                    $.<HorizontalLayout>create(() -> {
                        layout(match_parent, 0, 20);
                    }).render(() -> {
                        IntStream.range(0, 3).forEach(j -> {
                            var newCalendar = (Calendar) calendar.clone();
                            var dayLater = i * 3 + j;
                            newCalendar.add(Calendar.DAY_OF_MONTH, dayLater);
                            var year = newCalendar.get(Calendar.YEAR);
                            var month = newCalendar.get(Calendar.MONTH) + 1;
                            var day = newCalendar.get(Calendar.DAY_OF_MONTH);
                            var displayDate = format("{}年\n{}月{}日", year, month, day);
                            $.<Button>create(() -> {
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


    // 画像選択時の処理はここを参考にしてね②
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImageUri = intent.getData();
            UserInfo.imageUri = selectedImageUri;
            profileImage[v].setImageURI(selectedImageUri);
            isImageSelected =true;
        }
    }
}
