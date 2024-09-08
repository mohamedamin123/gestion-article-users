package com.example.gestion_de_stock.database.interne.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.gestion_de_stock.database.interne.DAO.ArticleDAO;
import com.example.gestion_de_stock.database.interne.MyRoomDataBase;
import com.example.gestion_de_stock.database.interne.entity.Article;

import java.util.List;

public class RepositoryArticle {

   ArticleDAO ArticleDAO;


    public RepositoryArticle(Application application) {
        MyRoomDataBase db=MyRoomDataBase.getDatabase(application);
        ArticleDAO= db.articleDAO();

    }

    public void insertArticle(Article... Articles)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                ArticleDAO.insertArticle(Articles);
            }
        });
    }
    public  void updateArticle(Article... Articles)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                ArticleDAO.updateArticle(Articles);
            }
        });
    }
    public void deleteArticle(Article... Articles)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                ArticleDAO.deleteArticle(Articles);
            }
        });
    }

    public void deleteArticleById(Integer id)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                ArticleDAO.deleteArticleById(id);
            }
        });
    }

    public LiveData<Article> findArticleById(Integer id)
    {

              return  ArticleDAO.findArticleById(id);

    }

    public LiveData<List<Article>> findAllArticle()
    {
              return  ArticleDAO.findAllArticle();
    }

    public LiveData<Article> findArticleByNom(String nom)
    {
        return ArticleDAO.findArticleByNom(nom);
    }



}
