package com.example.wantouch_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wantouch_project.forem.annotation.Writer;

@Writer({"だん","あとはそうまが全部やる"})
public class ImagePickActivity extends AppCompatActivity {
    private static final int REQUEST_GALLERY = 0;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        startPickImageActivity();
    }

    protected void startPickImageActivity() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    // 写真選択されたときのイベント処理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {

        }
    }
}
