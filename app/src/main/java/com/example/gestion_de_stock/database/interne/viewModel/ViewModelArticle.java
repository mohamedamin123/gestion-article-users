package com.example.gestion_de_stock.database.interne.viewModel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

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

    public void deleteArticleById(Integer id, ViewModelLigneCommande.OnOperationCompleteListener listener) {
        new Thread(() -> {
            try {
                repositiry.deleteArticleById(id);
                // Notify the listener of success on the main thread
                new Handler(Looper.getMainLooper()).post(() -> listener.onOperationComplete(true));
            } catch (Exception e) {
                // Notify the listener of failure on the main thread
                new Handler(Looper.getMainLooper()).post(() -> listener.onOperationComplete(false));
            }
        }).start();
    }

}
