package com.example.wantouch_project.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;


import com.example.wantouch_project.R;
import com.example.wantouch_project.backgrounds.BaseBackground;
import com.example.wantouch_project.components.ChatComponent;
import com.example.wantouch_project.components.ChatUiComponent;
import com.example.wantouch_project.data_store.Api;
import com.example.wantouch_project.data_store.UserInfo;
import com.example.wantouch_project.data_store.firebase.FirestoreController;
import com.example.wantouch_project.data_store.firebase.StorageContoller;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import forem.java.activitys.ForemActivity;
import forem.java.annotation.Writer;
import forem.java.extensions.ForemList;
import forem.java.views.listLayoutModules.ListLayout;
import forem.java.views.CircleImageView;
import forem.java.views.HorizontalLayout;
import forem.java.views.Root;
import forem.java.views.VarArea;
import forem.java.views.VerticalLayout;


@Writer({"ひかる", "だん"})
public class ChatRoomActivity extends ForemActivity {
    private static final int REQUEST_GALLERY = 0;
    public static String myName;
    public ForemList<HashMap<String, String>>[] chatList;

    @Override
    public void render(Root root) {

        onBack = none;
        new BaseBackground();
        if (myName == null) {
            // ここは連番で名前を割り振るようにする
            myName = "Y";
        }

        $.<HorizontalLayout>create(() -> {
            layout(match_parent, 0, 8);
            backgroundColor(Color.DKGRAY);
        }).render(() -> {
            $.<VerticalLayout>create(() -> {
                layout(0, match_parent, 35);
            }).render(() -> {
                space(0, 0, 8);
                $.<HorizontalLayout>create(() -> {
                    layout(match_parent, 0, 84);
                }).render(() -> {
                    space(0, 0, 10);
                    $.<Button>create(() -> {
                        layout(0, match_parent, 75);
                        craftmincho();
                        textSize(25);
                        switch (myName) {
                            case "HOST" -> {
                                text("解散");
                                onClick(() -> {
                                    alert("解散しますか？", args -> {
                                        args.yesButton(() -> {
                                            // 部屋を閉じる処理をする(データベースなど)
                                            myName = null;
                                            UserInfo.roomId="";
                                            FirestoreController.db.collection("users").document(UserInfo.id).update("roomId","");
                                            finish();

                                        });
                                    });
                                });
                            }
                            default -> {
                                text("退出");
                                onClick(() -> {
                                    alert("退出しますか？", args -> {
                                        args.yesButton(() -> {
                                            myName = null;
                                            FirestoreController.db.collection("users").document(UserInfo.id).update("roomId","");
                                            finish();
                                        });
                                    });
                                });
                            }
                        }
                    });
                });
            });
            //メンバー表示
            $.<VerticalLayout>create(() -> {
                layout(0, match_parent, 50);//大森もとき
            }).render(() -> {
                $.<HorizontalLayout>create(() -> {
                    space(0, 0, 8);
                    layout(match_parent, 0, 86);
                }).render(() -> {
                    $.<CircleImageView>create(() -> {
                        layout(0, match_parent, 25);
                        padding(3);
                        image(R.drawable.wantouch_logo_);
                    });
                    $.<CircleImageView>create(() -> {
                        layout(0, match_parent, 25);
                        padding(3);
                        image(R.drawable.wantouch_logo_);
                    });
                    $.<CircleImageView>create(() -> {
                        layout(0, match_parent, 25);
                        padding(3);
                        image(R.drawable.wantouch_logo_);
                    });
                    $.<CircleImageView>create(() -> {
                        layout(0, match_parent, 25);
                        padding(3);
                        image(R.drawable.wantouch_logo_);
                    });
                });
            });
            //設定

            if (myName.equals("HOST")) {
                $.<VerticalLayout>create(() -> {
                    layout(0, match_parent, 15);
                }).render(() -> {
                    space(0, 0, 8);
                    $.<ImageView>create(() -> {
                        layout(match_parent, 0, 86);
                        image(R.drawable.setting_button);
                        onClick(() -> {
                            MakeRoomActivity.isMaking = false;
                            startActivity(MakeRoomActivity.class);

                        });
                    });
                });
            }
        });

        chatList = var(new ForemList<>());
        FirestoreController.db.collection("rooms").document(UserInfo.roomId).addSnapshotListener((snapshot, ex) -> {
            var roomData = snapshot.getData();
            var newChatList = new ForemList<HashMap<String, String>>();
            newChatList.addAll((ArrayList<HashMap<String, String>>) roomData.get("chat_list"));
            if (chatList[v].size() < newChatList.size()) {
                chatList[v] = newChatList;
                notify(chatList);
            }

            if (chatList[v].size() == 0) {
                HashMap<String, String> initChat = new HashMap<>();
                initChat.put("name", "IGNORE");
                initChat.put("message", "こちらはチャットルームです");
                initChat.put("timeStamp", "");
                chatList[v].add(initChat);

                FirestoreController.db.collection("rooms").document(UserInfo.roomId).update("chat_list", chatList[v]);
                notify(chatList);
            }
        });


        // ホストがゲストのアイコンを押すと位置情報送信などのメニューがでるようにする


        $.<VarArea>create(() -> {
            layout(match_parent, 0, 84);
            onNotify(chatList, () -> {
                $.<ListLayout>create(listLayout -> {
                    layout(match_parent, match_parent);
                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
                    listLayout.addItemDecoration(itemDecoration);
                    listRender(chatList[v], (chat, i) -> {
                        $.<ChatComponent>export(args -> {
                            args.chat = chat;
                        });
                    });
                    listLayout.scrollToPosition(listLayout.getAdapter().getItemCount() - 1);
                });
            });
        });

        $.<HorizontalLayout>create(() -> {
            layout(match_parent, 0, 8);
            backgroundColor(Color.DKGRAY);
        }).render(() -> {
            $.<ChatUiComponent>export(args -> {
                args.myName = myName;
                args.chatList = chatList;
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImageUri = intent.getData();
            UserInfo.imageUri = selectedImageUri;

            ImageView selectedImage = new ImageView($);
            selectedImage.setImageURI(selectedImageUri);

            StringBuilder imageId = new StringBuilder();
            IntStream.range(0, 10).forEach(i -> {
                imageId.append(Api.random.nextLong());
            });
            StorageContoller.uploadImage(imageId.toString(), selectedImage);

            HashMap<String, String> chat = new HashMap<>();
            chat.put("name", myName);
            chat.put("isImage", "true");
            chat.put("message", imageId.toString());
            Calendar calendar = Calendar.getInstance();
            chat.put("timeStamp", String.format("%d:%02d", calendar.get(Calendar.HOUR) + calendar.get(Calendar.AM_PM) * 12, calendar.get(Calendar.MINUTE)));
            chatList[v].add(chat);
            FirestoreController.db.collection("rooms").document(UserInfo.roomId).update("chat_list", chatList[v]);
            notify(chatList);
        }
    }
}