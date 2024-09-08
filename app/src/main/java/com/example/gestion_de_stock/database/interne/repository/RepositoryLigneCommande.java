package com.example.gestion_de_stock.database.interne.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.gestion_de_stock.database.interne.DAO.LigneCommandeDAO;
import com.example.gestion_de_stock.database.interne.MyRoomDataBase;
import com.example.gestion_de_stock.database.interne.entity.LigneCommande;

import java.util.List;

public class RepositoryLigneCommande {

   LigneCommandeDAO LigneCommandeDAO;


    public RepositoryLigneCommande(Application application) {
        MyRoomDataBase db=MyRoomDataBase.getDatabase(application);
        LigneCommandeDAO= db.ligneCommandeDAO();

    }

    public void insertLigneCommande(LigneCommande... LigneCommandes)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LigneCommandeDAO.insertLigneCommande(LigneCommandes);
            }
        });
    }
    public  void updateLigneCommande(LigneCommande... LigneCommandes)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LigneCommandeDAO.updateLigneCommande(LigneCommandes);
            }
        });
    }
    public void deleteLigneCommande(LigneCommande... LigneCommandes)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LigneCommandeDAO.deleteLigneCommande(LigneCommandes);
            }
        });
    }

    public void deleteLigneCommandeById(Integer id)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LigneCommandeDAO.deleteLigneCommandeById(id);
            }
        });
    }

    public LiveData<LigneCommande> findLigneCommandeById(Integer id)
    {

              return  LigneCommandeDAO.findLigneCommandeById(id);

    }

    public LiveData<List<LigneCommande>> findAllLigneCommande()
    {
              return  LigneCommandeDAO.findAllLigneCommande();
    }

    public LiveData<LigneCommande> findLigneCommandeByArticleId(Integer id)
    {
        return LigneCommandeDAO.findLigneCommandeByArticleId(id);
    }

    public LiveData<LigneCommande> findLigneCommandeByCommandeId(Integer id)
    {
        return LigneCommandeDAO.findLigneCommandeByCommandeId(id);
    }

    public LiveData<LigneCommande> findLigneCommandeByArticleIdAndCommandeId(Integer idA,Integer idC)
    {
        return LigneCommandeDAO.findLigneCommandeByArticleIdAndCommandeId(idA, idC);
    }



}
