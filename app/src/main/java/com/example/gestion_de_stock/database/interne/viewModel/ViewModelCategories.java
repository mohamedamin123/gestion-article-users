package com.example.gestion_de_stock.database.interne.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gestion_de_stock.database.interne.entity.Categories;
import com.example.gestion_de_stock.database.interne.repository.ReposirotyCategories;

import java.util.List;

public class ViewModelCategories extends AndroidViewModel {
    ReposirotyCategories repositiry;
    public ViewModelCategories(@NonNull Application application) {
        super(application);
        repositiry=new ReposirotyCategories(application);
    }

    public void insertCategories(Categories... Categoriess)
    {
       repositiry.insertCategories(Categoriess);
    }
    public  void updateCategories(Categories... Categoriess)
    {
       repositiry.updateCategories(Categoriess);
    }
    public void deleteCategories(Categories... Categoriess)
    {
       repositiry.deleteCategories(Categoriess);
    }

    public void deleteCategoriesById(Integer id)
    {
       repositiry.deleteCategoriesById(id);
    }

    public LiveData<Categories> findCategoriesById(Integer id)
    {
          return repositiry.findCategoriesById(id);
    }

    public LiveData<List<Categories>> findAllCategories()
    {
        return  repositiry.findAllCategories();
    }

    public LiveData<Categories> findCategoriesByNom(String nom)
    {
        return repositiry.findCategoriesByNom(nom);
    }

}
