package com.example.wantouch_project.records;

import com.google.android.gms.maps.model.LatLng;

public record Room(
        String id,
        String dogName,
        double latitude,
        double longitude,
        int year,
        int month,
        int day,
        int startHour,
        int startMinute,
        int endHour,
        int endMinute) {
}
