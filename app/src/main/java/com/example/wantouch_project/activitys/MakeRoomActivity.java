package com.example.wantouch_project.activitys;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.wantouch_project.R;
import com.example.wantouch_project.animations.InputRequestAnimation;
import com.example.wantouch_project.animations.SelectRequestAnimation;
import com.example.wantouch_project.data_store.firebase.FirestoreController;
import com.example.wantouch_project.data_store.UserInfo;
import com.example.wantouch_project.data_store.firebase.StorageContoller;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import androidx.fragment.app.FragmentActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import forem.java.annotation.Writer;
import forem.java.views.CircleImageView;


@Writer({"比嘉", "だん"})
public class MakeRoomActivity extends FragmentActivity implements OnMapReadyCallback {
    public static boolean isMaking = true;
    private static final int REQUEST_GALLERY = 0;
    private boolean isImageSelected = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;


    //グローバル変数で保存用データの引数宣言
    public LatLng lastLocation;//緯度経度
    public String dogName;//ペットの名前
    public int year;//年
    public int month;//月
    public int day;//日


    public int startHour;//開始時間
    public int startMinute;//開始分
    public int endHour;//終了時間
    public int endMinute;//終了分

    public boolean isTimeSelected = false;
    private CircleImageView dogImage;

    public ArrayList<HashMap<String, String>> chatList = new ArrayList<>();

    private double latitude;
    private double longitude;

    @Writer({"比嘉", "だん"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_room_activity);
        EditText nameText = findViewById(R.id.nameEdit);
        Button dateoption = findViewById(R.id.dateEdit);
        Button timeoption = findViewById(R.id.timeEdit);

        dogImage = findViewById(R.id.dogImage);

        StorageContoller.getImageUri(UserInfo.id, uri -> {
            Glide.with(this).load(uri).into(dogImage);
            isImageSelected = true;
        }, ex -> {

        });

        dogImage.setOnClickListener(v -> {
            // 画像選択時の処理はここを参考にしてね①
            Intent intent = new Intent();
            intent.setType("image/*");//ギャラリー内で画像のみが表示される
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, REQUEST_GALLERY);
        });

        if (UserInfo.dogName != null) {
            nameText.setText(UserInfo.dogName);
        }

        dateoption.setOnClickListener(v -> {
            // 現在の日付を取得
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // DatePickerDialogを作成
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // 月は0から始まるため、1を足す
                        selectedMonth += 1;
                        // ボタンのテキストを選択された日付に設定
                        dateoption.setText(selectedYear + "/" + selectedMonth + "/" + selectedDay);
                        this.year = selectedYear;
                        this.day = selectedDay;
                        this.month = selectedMonth;
                    },
                    year, month, day);
            datePickerDialog.setTitle("予定日を選択");
            datePickerDialog.show();
        });

        timeoption.setOnClickListener(v -> {
            // 現在の時刻を取得
            final Calendar calendar = Calendar.getInstance();
            int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
            int nowMinute = calendar.get(Calendar.MINUTE);

            // 開始時刻のTimePickerDialogを作成
            TimePickerDialog startTimePickerDialog = new TimePickerDialog(
                    this,
                    (view, hourOfDay, minute) -> {
                        startHour = hourOfDay;
                        startMinute = minute;

                        // 終了時刻のTimePickerDialogを作成
                        TimePickerDialog endTimePickerDialog = new TimePickerDialog(
                                this,
                                (view1, hourOfDay1, minute1) -> {
                                    endHour = hourOfDay1;
                                    endMinute = minute1;

                                    // ボタンのテキストを開始時刻と終了時刻に設定
                                    timeoption.setText(String.format("%d:%02d ～ %d:%02d", startHour, startMinute, endHour, endMinute));
                                    isTimeSelected = true;
                                },
                                startHour, startMinute, true);
                        endTimePickerDialog.setTitle("終了時刻を選択");
                        endTimePickerDialog.show();
                    },
                    nowHour, nowMinute, true);
            startTimePickerDialog.setTitle("開始時刻を選択");
            startTimePickerDialog.show();
        });

        //部屋作成ボタン、データ保存と遷移
        Button makeButton = findViewById(R.id.makeButton);
        if (isMaking) {
            makeButton.setOnClickListener(v -> {
                dogName = nameText.getText().toString();
                if (!isImageSelected) {
                    Toast.makeText(this, "画像を選択してください", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!dogName.equals("") && year > 0 && month > 0 && isTimeSelected && lastLocation != null) {
                    openRoom();
                    UserInfo.roomId = UserInfo.id;
                    FirestoreController.db.collection("users").document(UserInfo.id).update("roomId", UserInfo.roomId);
                    ChatRoomActivity.myName = "HOST";
                    StorageContoller.uploadImage(UserInfo.id, dogImage);
                    isMaking = true;
                    Intent intent = new Intent(this, ChatRoomActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    InputRequestAnimation.start(nameText);
                    SelectRequestAnimation.start(dateoption);
                    SelectRequestAnimation.start(timeoption);
                }
            });
        } else {
            FirestoreController.db.collection("rooms").document(UserInfo.roomId).get().addOnSuccessListener(task -> {
                if (task != null) {
                    Map<String, Object> roomData = task.getData();
                    nameText.setText(roomData.get("dogName").toString());
                    year = Integer.parseInt(roomData.get("year").toString());
                    month = Integer.parseInt(roomData.get("month").toString());
                    day = Integer.parseInt(roomData.get("day").toString());
                    startHour = Integer.parseInt(roomData.get("start_hour").toString());//開始時間
                    startMinute = Integer.parseInt(roomData.get("start_minute").toString());//開始分
                    endHour = Integer.parseInt(roomData.get("end_hour").toString());//終了時間
                    endMinute = Integer.parseInt(roomData.get("end_minute").toString());//終了分
                    chatList = (ArrayList<HashMap<String, String>>) roomData.get("chat_list");
                    dateoption.setText(year + "/" + month + "/" + day);
                    timeoption.setText(String.format("%d:%02d ～ %d:%02d", startHour, startMinute, endHour, endMinute));
                    latitude = Double.parseDouble(roomData.get("latitude").toString());
                    longitude = Double.parseDouble(roomData.get("longitude").toString());
                    isTimeSelected = true;
                }
            });

            makeButton.setText("変更");

            makeButton.setOnClickListener(v -> {
                dogName = nameText.getText().toString();
                if (!isImageSelected) {
                    Toast.makeText(this, "画像を選択してください", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!dogName.equals("") && year > 0 && month > 0 && isTimeSelected && lastLocation != null) {
                    openRoom();
                    UserInfo.roomId = UserInfo.id;
                    ChatRoomActivity.myName = "HOST";
                    StorageContoller.uploadImage(UserInfo.id, dogImage);
                    isMaking = true;
                    finish();
                } else {
                    InputRequestAnimation.start(nameText);
                    SelectRequestAnimation.start(dateoption);
                    SelectRequestAnimation.start(timeoption);
                }
            });
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // 位置情報クライアントを初期化
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "craftmincho.otf");
        nameText.setTypeface(font);
        dateoption.setTypeface(font);
        timeoption.setTypeface(font);
        makeButton.setTypeface(font);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(googleMap.MAP_TYPE_SATELLITE);

        // パーミッションの確認と取得
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        mMap.setMyLocationEnabled(true);
        /*↑パーミッション確認。↓マーカーの動きのコード*/

        // タップのリスナーをセット
        mMap.setOnMapClickListener(longpushLocation -> {
            mMap.clear();
            lastLocation = new LatLng(longpushLocation.latitude, longpushLocation.longitude);
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).position(lastLocation).title(""));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newlocation, 14));
        });

        mMap.setOnMarkerClickListener(marker -> {
            /// タップしたマーカーを削除
//            marker.remove();
            /// このメソッドでは戻り値がfalseの場合、
            /// 動作が似ているため同時に発生しがちなイベントである
            /// onClickなどの他のメソッドは実行しない。
            return false;
        });

        if (isMaking) {
            //アプリ起動時の位置情報取得&権限の確保
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            lastLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(lastLocation).title("現在位置"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, 20));
                        }
                    });
        } else {
            lastLocation = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(lastLocation).title("現在位置"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, 20));
        }

    }

    public void openRoom() {
        Map<String, Object> roomData = new HashMap<>();
        roomData.put("id", UserInfo.id);
        roomData.put("dogName", dogName);
        roomData.put("latitude", lastLocation.latitude);
        roomData.put("longitude", lastLocation.longitude);
        roomData.put("year", year);
        roomData.put("month", month);
        roomData.put("day", day);
        roomData.put("start_hour", startHour);
        roomData.put("start_minute", startMinute);
        roomData.put("end_hour", endHour);
        roomData.put("end_minute", endMinute);
        roomData.put("chat_list", chatList);
        FirestoreController.db.collection("rooms").document(UserInfo.id).set(roomData);
    }


    // 画像選択時の処理はここを参考にしてね②
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImageUri = intent.getData();
            UserInfo.imageUri = selectedImageUri;
            dogImage.setImageURI(selectedImageUri);
            isImageSelected = true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isMaking = true;
    }

}

