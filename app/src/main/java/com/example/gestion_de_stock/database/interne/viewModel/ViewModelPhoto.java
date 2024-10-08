package com.example.gestion_de_stock.database.interne.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gestion_de_stock.database.interne.entity.LigneCommande;
import com.example.gestion_de_stock.database.interne.entity.Photo;
import com.example.gestion_de_stock.database.interne.repository.RepositoryPhoto;

import java.util.List;

public class ViewModelPhoto extends AndroidViewModel {
    RepositoryPhoto repositiry;
    public ViewModelPhoto(@NonNull Application application) {
        super(application);
        repositiry=new RepositoryPhoto(application);
    }

    public void insertPhoto(Photo... Photos)
    {
       repositiry.insertPhoto(Photos);
    }
    public  void updatePhoto(Photo... Photos)
    {
       repositiry.updatePhoto(Photos);
    }
    public void deletePhoto(Photo... Photos)
    {
       repositiry.deletePhoto(Photos);
    }

    public void deleteAllByCommande(Integer id)
    {
       repositiry.deleteAllByCommande(id);
    }


    public void deletePhotoByID(Integer id)
    {
        repositiry.deletePhotoByID(id);
    }


    public LiveData<List<Photo>> findAllLigneCommande()
    {
        return  repositiry.findAllPhotos();
    }

    public LiveData<List<Photo>> findAllPhotosByCommande(Integer id) {
        return repositiry.findAllPhotosByCommande(id);
    }

    public LiveData<List<Photo>> findAllPhotosByArticle(Integer id) {
        return repositiry.findAllPhotosByArticle(id);
    }

    public LiveData<Photo> findPhtoById(Integer id) {
        return repositiry.findPhtoById(id);
    }

}
