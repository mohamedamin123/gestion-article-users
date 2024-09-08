package com.example.gestion_de_stock.util;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverteur {

    @TypeConverter
    public static LocalDate toDate(String date)
    {
        if(date!=null)
        {
            DateTimeFormatter formatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            }
            LocalDate dates = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                dates = LocalDate.parse(date, formatter);
            }
            return dates;
        }

        return null;
        //return milliseconde==null?null:new Date(milliseconde);
    }
    @TypeConverter
    public static String fromDate(LocalDate date)
    {
        if(date!=null)
        {
            DateTimeFormatter formatter = null; // format de date souhaité
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            }
            String formattedDate = null; // conversion en chaîne de caractères
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formattedDate = date.format(formatter);
            }
            return formattedDate;
        }
        return null;
    }
}
