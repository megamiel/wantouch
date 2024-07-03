package com.example.wantouch_project.activitys;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wantouch_project.backgrounds.BaseBackground;
import com.example.wantouch_project.components.RoomInfoComponent;
import com.example.wantouch_project.data_store.UserInfo;
import com.example.wantouch_project.data_store.firebase.FirestoreController;
import com.example.wantouch_project.data_store.firebase.StorageContoller;
import com.example.wantouch_project.records.Room;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import forem.java.activitys.ForemActivity;
import forem.java.annotation.Writer;
import forem.java.extensions.ForemList;
import forem.java.views.listLayoutModules.ListLayout;
import forem.java.views.Root;
import io.grpc.Context;

@Writer({"ひかる", "だん"})
public class RoomListActivity extends ForemActivity {
    public void render(Root root) {
        new BaseBackground();
        ForemList<Room> roomInfoList = new ForemList<>();
        FirestoreController.db.collection("rooms").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Map<String, Object> roomInfo = document.getData();
                    String id = roomInfo.get("id").toString();
                    String dogName = roomInfo.get("dogName").toString();
                    double latitude = Double.parseDouble(roomInfo.get("latitude").toString());
                    double longitude = Double.parseDouble(roomInfo.get("longitude").toString());
                    int year = Integer.parseInt(roomInfo.get("year").toString());//年
                    int month = Integer.parseInt(roomInfo.get("month").toString());//月
                    int day = Integer.parseInt(roomInfo.get("day").toString());//日
                    int startHour = Integer.parseInt(roomInfo.get("start_hour").toString());//開始時間
                    int startMinute = Integer.parseInt(roomInfo.get("start_minute").toString());//開始分
                    int endHour = Integer.parseInt(roomInfo.get("end_hour").toString());//終了時間
                    int endMinute = Integer.parseInt(roomInfo.get("end_minute").toString());//終了分
                    Calendar currentCalendar = Calendar.getInstance();
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month - 1);
                    calendar.set(Calendar.DAY_OF_MONTH, day);
                    calendar.set(Calendar.HOUR, endHour);
                    calendar.set(Calendar.MINUTE, endMinute);
                    if (!currentCalendar.after(calendar)) {
                        roomInfoList.add(new Room(id, dogName, latitude, longitude, year, month, day, startHour, startMinute, endHour, endMinute));
                    }
                }

                $.<ListLayout>create(listLayout -> {
                    layout(match_parent, match_parent);
                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
                    listLayout.addItemDecoration(itemDecoration);
                    listRender(roomInfoList, room -> {
                        $.<RoomInfoComponent>export(args -> {
                            args.room = room;
                        });
                    });
                });
            }
        });
    }
}