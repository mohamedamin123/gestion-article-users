package com.example.gestion_de_stock.database.interne.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.gestion_de_stock.database.interne.DAO.LigneCommandeDAO;
import com.example.gestion_de_stock.database.interne.MyRoomDataBase;
import com.example.gestion_de_stock.database.interne.entity.Commande;
import com.example.gestion_de_stock.database.interne.entity.LigneCommande;

import java.util.List;

public class RepositoryLigneCommande {
    private final LigneCommandeDAO ligneCommandeDAO;

    public RepositoryLigneCommande(Application application) {
        MyRoomDataBase db = MyRoomDataBase.getDatabase(application);
        ligneCommandeDAO = db.ligneCommandeDAO();
    }

    public void insertLigneCommandes(LigneCommande... ligneCommandes) {
        MyRoomDataBase.databaseWriteExecutor.execute(() -> ligneCommandeDAO.insertLigneCommandes(ligneCommandes));
    }

    public void updateLigneCommande(LigneCommande... ligneCommandes) {
        MyRoomDataBase.databaseWriteExecutor.execute(() -> ligneCommandeDAO.updateLigneCommande(ligneCommandes));
    }

    public void deleteLigneCommande(LigneCommande... ligneCommandes) {
        MyRoomDataBase.databaseWriteExecutor.execute(() -> ligneCommandeDAO.deleteLigneCommande(ligneCommandes));
    }

    public void deleteLigneCommandeById(Integer id) {
        MyRoomDataBase.databaseWriteExecutor.execute(() -> ligneCommandeDAO.deleteLigneCommandeById(id));
    }
    public void deleteLigneCommandeByIdCommande(Integer id) {
        MyRoomDataBase.databaseWriteExecutor.execute(() -> ligneCommandeDAO.deleteLigneCommandeByIdCommande(id));
    }

    public LiveData<LigneCommande> findLigneCommandeById(Integer id) {
        return ligneCommandeDAO.findLigneCommandeById(id);
    }

    public LiveData<List<LigneCommande>> findAllLigneCommande()
    {
        return  ligneCommandeDAO.findAllLigneCommande();
    }


    public LiveData<List<LigneCommande>> findLigneCommandeByIdCommande(Integer id) {
        return ligneCommandeDAO.findLigneCommandeByIdCommande(id);
    }

    public LiveData<List<LigneCommande>> findAllLigneCommandeByCommandeId(int id) {
        return ligneCommandeDAO.findAllLigneCommandeByCommandeId(id);
    }
}
