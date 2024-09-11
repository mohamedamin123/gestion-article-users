package com.example.gestion_de_stock.database.interne.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.gestion_de_stock.database.interne.entity.Photo;

import java.util.List;

@Dao
public interface PhotoDAO {
//----------------------------------------------------------------------------------------------crud
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Photo... photo);

    @Update
    void update(Photo... photo);

    @Delete
    void delete(Photo... photo);
//----------------------------------------------------------------------------------------------find
    @Query("SELECT * FROM photo")
    LiveData<List<Photo>> findAllPhotos();

    @Query("SELECT * FROM photo where commande_id=:CommandeId")
    LiveData<List<Photo>> findAllPhotosByCommande(Integer CommandeId);
//--------------------------------------------------------------------------------------------delete
    @Query("delete  from photo ")
    void deleteAllPhoto();
    @Query("delete FROM photo where  commande_id=:CommandeId")
    void deleteAllByCommande(Integer CommandeId);

    @Query("delete  from photo where photo_id=:photo_id ")
    void deletePhotoByID(int photo_id);



}
