package com.example.wantouch_project.data_store.firebase;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wantouch_project.data_store.UserInfo;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

import forem.java.annotation.Writer;
import forem.java.functionalInterfaces.ForemUnarySetter;

@Writer("だん")
public class StorageContoller {
    public static FirebaseStorage storage = FirebaseStorage.getInstance();

    public static void uploadImage(String id, ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        StorageReference storageRef = storage.getReference().child(id + ".jpg");
        storageRef.putBytes(baos.toByteArray());
    }

    public static void getImageUri(String id, ForemUnarySetter<Uri> successFs, ForemUnarySetter<Exception> failureFs) {
        storage.getReference().child(id + ".jpg").getDownloadUrl().addOnSuccessListener(successFs::set).addOnFailureListener(Throwable::printStackTrace);
    }

    public static void getImageUri(Activity activity, String id, ImageView imageView) {
        StorageContoller.getImageUri(UserInfo.id, uri -> {
            Glide.with(activity).load(uri).into(imageView);
        }, ex -> {
            System.out.println("失敗");
        });
    }
}
