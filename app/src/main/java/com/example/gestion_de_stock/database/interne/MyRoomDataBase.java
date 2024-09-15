package com.example.gestion_de_stock.database.interne;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


import com.example.gestion_de_stock.database.interne.DAO.ArticleDAO;
import com.example.gestion_de_stock.database.interne.DAO.ClientDAO;
import com.example.gestion_de_stock.database.interne.DAO.CommandeDAO;
import com.example.gestion_de_stock.database.interne.DAO.LigneCommandeDAO;
import com.example.gestion_de_stock.database.interne.DAO.PhotoDAO;
import com.example.gestion_de_stock.database.interne.entity.Article;
import com.example.gestion_de_stock.database.interne.entity.Commande;
import com.example.gestion_de_stock.database.interne.entity.Client;
import com.example.gestion_de_stock.database.interne.entity.LigneCommande;
import com.example.gestion_de_stock.database.interne.entity.Photo;
import com.example.gestion_de_stock.util.DateConverteur;
import com.example.gestion_de_stock.util.UriConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Client.class,  Photo.class, Commande.class, LigneCommande.class,Article.class
}, version = 1, exportSchema = false)
@TypeConverters({UriConverter.class, DateConverteur.class})
public abstract class MyRoomDataBase extends RoomDatabase {
    private final static String DATABASE_NAME = "test_3.1";

    public abstract ClientDAO clientDAO();
    public abstract CommandeDAO commandeDAO();
    public abstract PhotoDAO photoDAO();
    public abstract LigneCommandeDAO ligneCommandeDAO();
    public abstract ArticleDAO articleDAO();



    private static volatile MyRoomDataBase INSTANCE; //SINGLETON et volatile 1,2,3,4,5 t4aker exeple thread banque
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static MyRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MyRoomDataBase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}