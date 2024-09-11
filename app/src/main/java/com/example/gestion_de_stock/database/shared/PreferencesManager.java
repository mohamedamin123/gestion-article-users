package com.example.gestion_de_stock.database.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.gestion_de_stock.database.interne.entity.LigneCommande;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PreferencesManager {

    private static final String PREFS_NAME = "app_prefs";
    private static final String ITEMS_KEY = "item_commandes";
    private static SharedPreferences sharedPreferences;
    private static Gson gson = new Gson();

    // Initialize SharedPreferences
    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Save item commandes to SharedPreferences
    public static void saveItemCommandes(Context context, List<LigneCommande> ligneCommandes) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(ligneCommandes);
        editor.putString(ITEMS_KEY, json);
        editor.apply();
    }

    // Clear specific data from SharedPreferences
    public static void clearData(Context context) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ITEMS_KEY); // Remove specific key
        editor.apply(); // Save changes
    }

    // Get item commandes from SharedPreferences
    public static List<LigneCommande> getItemCommandes(Context context) {
        String json = sharedPreferences.getString(ITEMS_KEY, "[]");
        Type type = new TypeToken<ArrayList<LigneCommande>>(){}.getType();
        return gson.fromJson(json, type);
    }
}
