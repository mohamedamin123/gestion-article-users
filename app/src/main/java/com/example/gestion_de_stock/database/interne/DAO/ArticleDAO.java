package com.example.gestion_de_stock.database.interne.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestion_de_stock.database.interne.entity.Article;

import java.util.List;

@Dao
public interface ArticleDAO {

    @Insert
    void insertArticle(Article... Articles);
    @Update
    void updateArticle(Article... Articles);
    @Delete
    void deleteArticle(Article... Articles);



    @Query("delete from Article where Article_id=:id")
    void deleteArticleById(Integer id);

    @Query("select * from Article where Article_id=:id")
    LiveData<Article> findArticleById(Integer id);

    @Query("select * from Article")
    LiveData<List<Article>> findAllArticle();

    @Query("select * from Article where nom like '%' || :nom || '%' ")
    LiveData<Article> findArticleByNom(String nom);
}
