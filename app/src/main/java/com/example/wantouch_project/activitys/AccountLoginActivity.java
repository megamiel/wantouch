package com.example.wantouch_project.activitys;

import android.graphics.Color;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.wantouch_project.backgrounds.BaseBackground;
import com.example.wantouch_project.backgrounds.DefaultButtonBackground;
import com.example.wantouch_project.data_store.firebase.FirestoreController;

import java.util.Map;

import forem.java.activitys.ForemActivity;
import forem.java.annotation.Writer;
import forem.java.extensions.ForemList;
import forem.java.views.HorizontalLayout;
import forem.java.views.Root;

@Writer("そうま")
public class AccountLoginActivity extends ForemActivity {
    @Override
    public void render(Root root) {
        onBack = none;
        new BaseBackground();
        var idInput = varType(EditText.class);
        var passwordInput = varType(EditText.class);
        space(0, 0, 20);
        $.<HorizontalLayout>create(() -> {
            layout(match_parent, 0, 10);
        }).render(() -> {
            space(0, 0, 10);
            $.<EditText>create(idInput, () -> {
                layout(0, match_parent, 80);
                craftmincho();
                hint("id");
                alignBottom();
                textSize(30);
            });
        });
        $.<HorizontalLayout>create(() -> {
            layout(match_parent, 0, 10);
        }).render(() -> {
            space(0, 0, 10);
            $.<EditText>create(passwordInput, () -> {
                layout(0, match_parent, 80);
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
            $.<Button>create(a -> {
                layout(0, match_parent, 80);
                craftmincho();
                text("ログイン");
                textSize(30);
                new DefaultButtonBackground();
                onClick(() -> {
                    var id = idInput[v].getText().toString();
                    var password = passwordInput[v].getText().toString();
                    FirestoreController.db.collection("users").document(id).get().addOnSuccessListener(task -> {
                        Map<String,Object> userData = task.getData();
                        if (userData != null && password.equals(userData.get("password").toString())) {
                            putShared("id", id);
                            startActivity(WanTouchHomeActivity.class);
                        } else {
                            showToast("ID 又は パスワードを間違えています");
                        }
                    });
                });
            });
        });

        space(0, 0, 5);
        $.<TextView>create(() -> {
            layout(match_parent, 0, 10);
            craftmincho();
            text("作成したい方はこちら");
            align(center, top);
            textColor(Color.BLUE);
            textSize(30);
            onClick(() -> {
                startActivity(AccountCreateActivity.class);
                finish();
            });
        });
    }
}
