package com.example.gestion_de_stock.database.interne.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.gestion_de_stock.database.interne.DAO.CategoriesDAO;
import com.example.gestion_de_stock.database.interne.MyRoomDataBase;
import com.example.gestion_de_stock.database.interne.entity.Categories;

import java.util.List;

public class ReposirotyCategories {

   CategoriesDAO CategoriesDAO;


    public ReposirotyCategories(Application application) {
        MyRoomDataBase db=MyRoomDataBase.getDatabase(application);
        CategoriesDAO= db.categoriesDAO();

    }

    public void insertCategories(Categories... Categoriess)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                CategoriesDAO.insertCategories(Categoriess);
            }
        });
    }
    public  void updateCategories(Categories... Categoriess)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                CategoriesDAO.updateCategories(Categoriess);
            }
        });
    }
    public void deleteCategories(Categories... Categoriess)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                CategoriesDAO.deleteCategories(Categoriess);
            }
        });
    }

    public void deleteCategoriesById(Integer id)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                CategoriesDAO.deleteCategoriesById(id);
            }
        });
    }

    public LiveData<Categories> findCategoriesById(Integer id)
    {

              return  CategoriesDAO.findCategoriesById(id);

    }

    public LiveData<List<Categories>> findAllCategories()
    {
              return  CategoriesDAO.findAllCategories();
    }

    public LiveData<Categories> findCategoriesByNom(String nom)
    {
        return CategoriesDAO.findCategoriesByNom(nom);
    }



}
