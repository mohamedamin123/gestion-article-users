package com.example.gestion_de_stock.database.interne.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.gestion_de_stock.database.interne.DAO.PhotoDAO;
import com.example.gestion_de_stock.database.interne.MyRoomDataBase;
import com.example.gestion_de_stock.database.interne.entity.LigneCommande;
import com.example.gestion_de_stock.database.interne.entity.Photo;

import java.util.List;

public class RepositoryPhoto {

   PhotoDAO PhotoDAO;


    public RepositoryPhoto(Application application) {
        MyRoomDataBase db=MyRoomDataBase.getDatabase(application);
        PhotoDAO= db.photoDAO();

    }

    public void insertPhoto(Photo... Photos)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                PhotoDAO.insert(Photos);
            }
        });
    }
    public  void updatePhoto(Photo... Photos)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                PhotoDAO.update(Photos);
            }
        });
    }


    public LiveData<List<Photo>> findAllPhotos()
    {
        return PhotoDAO.findAllPhotos();
    }


    public LiveData<List<Photo>> findAllPhotosByCommande(Integer id) {
        return PhotoDAO.findAllPhotosByCommande(id);
    }


    public void deletePhoto(Photo... Photos)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                PhotoDAO.delete(Photos);
            }
        });
    }

    public void deletePhotoByID(Integer id)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                PhotoDAO.deletePhotoByID(id);
            }
        });
    }

    public void deleteAllByCommande(Integer id)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                PhotoDAO.deleteAllByCommande(id);
            }
        });
    }

}
