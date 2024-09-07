package com.example.gestion_de_stock.database.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

public class AjouterClientShared {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "ajouter_client";
    private static final String KEY_USER_PRENOM = "prenom";
    private static final String KEY_USER_NOM = "nom";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_TEL = "telephone";

    public AjouterClientShared(Context context) {
        sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    // Charger les données dans les EditText
    public void loadData(EditText nom, EditText prenom, EditText email, EditText tel) {
        nom.setText(sp.getString(KEY_USER_NOM, ""));
        prenom.setText(sp.getString(KEY_USER_PRENOM, ""));
        email.setText(sp.getString(KEY_USER_EMAIL, ""));
        tel.setText(sp.getString(KEY_USER_TEL, ""));
    }

    // Sauvegarder les données dans SharedPreferences
    public void saveData(String email, String nom, String prenom, String tel) {
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_NOM, nom);
        editor.putString(KEY_USER_PRENOM, prenom);
        editor.putString(KEY_USER_TEL, tel);
        editor.apply(); // Utiliser apply() pour les opérations asynchrones
    }

    public void removeData() {
        editor.clear(); // Utiliser clear() pour supprimer toutes les données
        saveData("","","","");
    }
}
