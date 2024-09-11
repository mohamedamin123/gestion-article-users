package com.example.gestion_de_stock.database.interne.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gestion_de_stock.database.interne.entity.Commande;
import com.example.gestion_de_stock.database.interne.entity.LigneCommande;
import com.example.gestion_de_stock.database.interne.repository.RepositoryLigneCommande;

import java.util.List;

public class ViewModelLigneCommande extends AndroidViewModel {
    private final RepositoryLigneCommande repository;

    public ViewModelLigneCommande(@NonNull Application application) {
        super(application);
        repository = new RepositoryLigneCommande(application);
    }

    public void insertLigneCommandes(List<LigneCommande> ligneCommandes, OnInsertCompleteListener listener) {
        new Thread(() -> {
            try {
                repository.insertLigneCommandes(ligneCommandes.toArray(new LigneCommande[0]));
                listener.onInsertComplete(true);
            } catch (Exception e) {
                listener.onInsertComplete(false);
            }
        }).start();
    }

    public interface OnInsertCompleteListener {
        void onInsertComplete(boolean success);
    }

    public LiveData<List<LigneCommande>> findAllLigneCommande()
    {
        return  repository.findAllLigneCommande();
    }


    public void updateLigneCommande(LigneCommande... ligneCommandes) {
        repository.updateLigneCommande(ligneCommandes);
    }

    public void deleteLigneCommande(LigneCommande... ligneCommandes) {
        repository.deleteLigneCommande(ligneCommandes);
    }

    public void deleteLigneCommandeById(Integer id) {
        repository.deleteLigneCommandeById(id);
    }

    public void deleteLigneCommandeByIdCommande(Integer id) {
        repository.deleteLigneCommandeByIdCommande(id);
    }


    public LiveData<LigneCommande> findLigneCommandeById(Integer id) {
        return repository.findLigneCommandeById(id);
    }

    public LiveData<List<LigneCommande>> findLigneCommandeByIdCommande(Integer id) {
        return repository.findLigneCommandeByIdCommande(id);
    }

    public LiveData<List<LigneCommande>> findAllLigneCommandeByCommandeId(int id) {
        return repository.findAllLigneCommandeByCommandeId(id);
    }
}
