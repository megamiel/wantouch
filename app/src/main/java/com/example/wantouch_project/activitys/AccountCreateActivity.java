package com.example.wantouch_project.activitys;

import android.graphics.Color;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.wantouch_project.R;
import com.example.wantouch_project.backgrounds.BaseBackground;
import com.example.wantouch_project.backgrounds.DefaultButtonBackground;
import com.example.wantouch_project.data_store.UserInfo;
import com.example.wantouch_project.data_store.firebase.FirestoreController;

import java.util.Optional;
import java.util.Random;

import forem.java.activitys.ForemActivity;
import forem.java.annotation.Writer;
import forem.java.extensions.ForemList;
import forem.java.views.HorizontalLayout;
import forem.java.views.Root;
import forem.java.views.VerticalLayout;

@Writer({"そうま", "だん"})

public class AccountCreateActivity extends ForemActivity {
    @Override
    public void render(Root root) {
        onBack = none;
        if (!getShared("id", "#####UNDEFIND#####").equals("#####UNDEFIND#####")) {
            UserInfo.id = getShared("id", "");
            FirestoreController.db.collection("users").document(UserInfo.id).get().addOnSuccessListener(task->{
                Object roomId=task.getData().get("roomId");
                if(roomId==null){
                    UserInfo.roomId="";
                }else{
                    UserInfo.roomId=roomId.toString();
                }
                startActivity(WanTouchHomeActivity.class);
                if(!UserInfo.id.equals("")&&UserInfo.id.equals(UserInfo.roomId)) {
                    ChatRoomActivity.myName="HOST";
                    startActivity(ChatRoomActivity.class);
                }
            });
            return;
        }
        new BaseBackground();
        space(0, 0, 20);
        var idInput = varType(EditText.class);
        var passwordInput = varType(EditText.class);
        $.<HorizontalLayout>create(() -> {
            layout(match_parent, 0, 10);
        }).render(() -> {
            space(0, 0, 10);

            $.<EditText>create(idInput, () -> {
                layout(0, match_parent, 75);
                craftmincho();
                hint("id");
                alignBottom();
                textSize(30);
            });

            $.<VerticalLayout>create(() -> {
                layout(0, match_parent, 8);
            }).render(() -> {
                space(0, 0, 40);
                $.<ImageView>create(generate -> {
                    layout(match_parent, 0, 50);
                    image(R.drawable.auto_generate);
                    onClick(() -> {
                        Random random = new Random();

                        var id = "";
                        do {
                            var count = 0;
                            while (count < 6) { // 10個の文字を生成して表示
                                var randomCharCode = random.nextInt(123 - 48) + 48; // 英数字の文字コードを生成
                                if ((randomCharCode >= 48 && randomCharCode <= 57) || // 数字の範囲
                                        (randomCharCode >= 65 && randomCharCode <= 90) || // 大文字の範囲
                                        (randomCharCode >= 97 && randomCharCode <= 122)) { // 小文字の範囲
                                    var randomChar = (char) randomCharCode; // 文字コードから文字への変換
                                    id += randomChar;
                                    count++;
                                }
                            }
                        } while (false);
                        idInput[v].setText(id);
                    });
                });
            });
        });
        $.<HorizontalLayout>create(() -> {
            layout(match_parent, 0, 10);
        }).render(() -> {
            space(0, 0, 10);
            $.<EditText>create(passwordInput, () -> {
                layout(0, match_parent, 75);
                craftmincho();
                hint("パスワード");
                alignBottom();
                textSize(30);
            });
        });

        space(0, 0, 5);

        $.<HorizontalLayout>create(() -> {
            layout(match_parent, 0, 10);
        }).render(() -> {
            space(0, 0, 10);
            $.<Button>create(() -> {
                layout(0, match_parent, 80);
                craftmincho();
                text("作成");
                textSize(30);
                new DefaultButtonBackground();
                onClick(() -> {
                    var id = idInput[v].getText().toString();
                    var password = passwordInput[v].getText().toString();
                    FirestoreController.db.collection("users").get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            ForemList<String> idList = new ForemList<>();
                            task.getResult().forEach(document -> {
                                idList.add(document.getData().get("id").toString());
                            });
                            idList.add("#####UNDEFIND#####");
                            if (!idList.contains(id)) {
                                FirestoreController.pushNewUserData(id, password);
                                putShared("id", id);
                                startActivity(WanTouchHomeActivity.class);
                                finish();
                            } else {
                                showToast("既に存在するIDです");
                            }
                        } else {
                            alert("インターネットに接続してください", args -> {
                                args.yesButton(this::finish);
                                args.noButton(this::finish);
                            });
                        }
                    });
                });
            });
        });


        $.<TextView>create(() -> {
            layout(match_parent, 0, 5);
        });
        $.<TextView>create(() -> {
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


    }

}
