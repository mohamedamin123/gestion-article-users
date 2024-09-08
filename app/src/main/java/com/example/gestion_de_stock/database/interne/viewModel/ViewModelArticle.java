package com.example.gestion_de_stock.database.interne.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gestion_de_stock.database.interne.entity.Article;
import com.example.gestion_de_stock.database.interne.repository.RepositoryArticle;

import java.util.List;

public class ViewModelArticle extends AndroidViewModel {
    RepositoryArticle repositiry;
    public ViewModelArticle(@NonNull Application application) {
        super(application);
        repositiry=new RepositoryArticle(application);
    }

    public void insertArticle(Article... Articles)
    {
       repositiry.insertArticle(Articles);
    }
    public  void updateArticle(Article... Articles)
    {
       repositiry.updateArticle(Articles);
    }
    public void deleteArticle(Article... Articles)
    {
       repositiry.deleteArticle(Articles);
    }

    public void deleteArticleById(Integer id)
    {
       repositiry.deleteArticleById(id);
    }

    public LiveData<Article> findArticleById(Integer id)
    {
          return repositiry.findArticleById(id);
    }

    public LiveData<List<Article>> findAllArticle()
    {
        return  repositiry.findAllArticle();
    }

    public LiveData<Article> findArticleByNom(String nom)
    {
        return repositiry.findArticleByNom(nom);
    }

}
