package com.example.wantouch_project.components;

import forem.java.annotation.*;
import forem.java.ui.*;
import forem.java.views.*;

import android.widget.*;

import com.example.wantouch_project.R;
import com.example.wantouch_project.activitys.ChatRoomActivity;
import com.example.wantouch_project.data_store.UserInfo;
import com.example.wantouch_project.data_store.firebase.FirestoreController;
import com.example.wantouch_project.data_store.firebase.StorageContoller;
import com.example.wantouch_project.records.Room;

@Writer({"ひかる", "だん"})
public class RoomInfoComponent extends Component {
    public Room room;

    public void export() {
        $.<HorizontalLayout>create(() -> {
            layout(match_parent, 450);
            marginLeft(50);
            onClick(() -> {
                UserInfo.roomId=room.id();
                FirestoreController.db.collection("users").document(UserInfo.id).update("roomId",UserInfo.roomId);
                startActivity(ChatRoomActivity.class);
            });
        }).render(() -> {
            $.<CircleImageView>create(dogImage -> {
                // サイズをうまく定義できてないから
                layout(0, match_parent, 30);
                StorageContoller.getImageUri($, room.id(), dogImage);
            });
            space(0, match_parent, 5);
            $.<TextView>create(() -> {
                layout(0, match_parent, 35);
                craftmincho();
                text(room.dogName());
                textSize(25);
                align(center, left);
            });
            $.<VerticalLayout>create(() -> {
                layout(0, match_parent, 30);
            }).render(() -> {
                $.<TextView>create(() -> {
                    layout(match_parent, 0, 30);
                    craftmincho();
                    text(room.month() + "月" + room.day() + "日");
                    textSize(20);
                    align(center, bottom);
                });
                $.<TextView>create(timeText -> {
                    layout(match_parent, 0, 25);
                    craftmincho();
                    text(room.startHour() + ":" + formatMinute(room.startMinute()));
                    textSize(20);
                    align(center, bottom);
                });
                $.<ImageView>create(() -> {
                    layout(match_parent, 0, 20);
                    image(R.drawable.tilde);
                });
                $.<TextView>create(() -> {
                    layout(match_parent, 0, 25);
                    craftmincho();
                    text(room.endHour() + ":" + formatMinute(room.endMinute()));
                    textSize(20);
                    align(center, top);
                });
            });
        });
    }

    private String formatMinute(int minute) {
        return (minute < 10 ? "0" : "") + minute;
    }
}