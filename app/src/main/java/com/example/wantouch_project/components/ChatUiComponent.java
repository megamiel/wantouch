package com.example.wantouch_project.components;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.number.Scale;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.wantouch_project.R;
import com.example.wantouch_project.data_store.UserInfo;
import com.example.wantouch_project.data_store.firebase.FirestoreController;
import com.google.firebase.firestore.FieldValue;

import java.util.Calendar;
import java.util.HashMap;

import forem.java.annotation.Writer;
import forem.java.extensions.ForemList;
import forem.java.ui.Component;
import forem.java.views.HorizontalLayout;
import forem.java.views.VerticalLayout;

@Writer({"ひかる", "だん"})
public class ChatUiComponent extends Component {
    private static final int REQUEST_GALLERY = 0;
    public String myName;
    public ForemList<HashMap<String, String>>[] chatList;

    @Override
    public void export() {
        space(0, match_parent, 1);
        $.<VerticalLayout>create(() -> {
            layout(0, match_parent, 96);
        }).render(() -> {
            space(match_parent, 0, 10);
            $.<HorizontalLayout>create(() -> {
                layout(match_parent, 0, 70);
            }).render(() -> {
                switch (myName) {
                    case "HOST" -> {
                        $.<ImageView>create(() -> {
                            layout(0, match_parent, 15);
                            image(R.drawable.image_pick);
                            onClick(() -> {
                                Intent intent = new Intent();
                                intent.setType("image/*");//ギャラリー内で画像のみが表示される
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                $.startActivityForResult(intent, REQUEST_GALLERY);
                            });
                        });
                    }
                    default -> {
                        space(0, match_parent, 15);
                    }
                }

                var chatInput = varType(EditText.class);
                $.<EditText>create(chatInput, () -> {
                    layout(0, match_parent, 70);
                    backgroundColor(Color.WHITE);
                    craftmincho();
//                   textSize(20);
                });
                space(0, 0, 3);
                $.<ImageView>create(() -> {
                    layout(0, match_parent, 10);
                    image(R.drawable.submit);
                    onClick(() -> {
                        if (chatInput[v].getText().toString().equals("")) {
                            return;
                        }

                        View view = $.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) $.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }

                        HashMap<String, String> chat = new HashMap<>();
                        chat.put("name", myName);
                        chat.put("isImage", "false");
                        chat.put("message", chatInput[v].getText().toString());
                        Calendar calendar = Calendar.getInstance();
                        chat.put("timeStamp", String.format("%d:%02d", calendar.get(Calendar.HOUR) + calendar.get(Calendar.AM_PM) * 12, calendar.get(Calendar.MINUTE)));
                        chatList[v].add(chat);
                        FirestoreController.db.collection("rooms").document(UserInfo.roomId).update("chat_list", chatList[v]);
                        chatInput[v].setText("");
                        notify(chatList);
                    });
                });
            });
        });
    }
}
