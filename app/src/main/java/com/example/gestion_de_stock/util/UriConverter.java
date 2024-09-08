package com.example.gestion_de_stock.util;

import android.net.Uri;

import androidx.room.TypeConverter;

public class UriConverter {

    @TypeConverter
    public static Uri fromString(String value) {
        return value != null ? Uri.parse(value) : null;
    }

    @TypeConverter
    public static String uriToString(Uri uri) {
        return uri != null ? uri.toString() : null;
    }
}
