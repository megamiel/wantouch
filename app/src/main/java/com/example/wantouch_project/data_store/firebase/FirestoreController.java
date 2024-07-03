package com.example.wantouch_project.data_store.firebase;

import com.example.wantouch_project.data_store.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import forem.java.annotation.Writer;

@Writer("だん")
public class FirestoreController {
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static void pushNewUserData(String id, String password) {
        Map<String, Object> user = new HashMap<>();
        UserInfo.id = id;
        user.put("id", id);
        user.put("password", password);
        user.put("dogName", "");
        user.put("roomId","");
        FirestoreController.db.collection("users").document(id).set(user);
    }


}
