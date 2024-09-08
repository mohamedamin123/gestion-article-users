package com.example.gestion_de_stock.database.interne.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestion_de_stock.database.interne.entity.Categories;

import java.util.List;

@Dao
public interface CategoriesDAO {

    @Insert
    void insertCategories(Categories... Categoriess);
    @Update
    void updateCategories(Categories... Categoriess);
    @Delete
    void deleteCategories(Categories... Categoriess);



    @Query("delete from Categories where idCategorie=:id")
    void deleteCategoriesById(Integer id);

    @Query("select * from Categories where idCategorie=:id")
    LiveData<Categories> findCategoriesById(Integer id);

    @Query("select * from Categories")
    LiveData<List<Categories>> findAllCategories();

    @Query("select * from Categories where nom like '%' || :nom || '%' ")
    LiveData<Categories> findCategoriesByNom(String nom);
}
