package com.example.gestion_de_stock.database.interne.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gestion_de_stock.database.interne.entity.LigneCommande;
import com.example.gestion_de_stock.database.interne.repository.RepositoryLigneCommande;

import java.util.List;

public class ViewModelLigneCommande extends AndroidViewModel {
    RepositoryLigneCommande repositiry;
    public ViewModelLigneCommande(@NonNull Application application) {
        super(application);
        repositiry=new RepositoryLigneCommande(application);
    }

    public void insertLigneCommande(LigneCommande... LigneCommandes)
    {
       repositiry.insertLigneCommande(LigneCommandes);
    }
    public  void updateLigneCommande(LigneCommande... LigneCommandes)
    {
       repositiry.updateLigneCommande(LigneCommandes);
    }
    public void deleteLigneCommande(LigneCommande... LigneCommandes)
    {
       repositiry.deleteLigneCommande(LigneCommandes);
    }

    public void deleteLigneCommandeById(Integer id)
    {
       repositiry.deleteLigneCommandeById(id);
    }

    public LiveData<LigneCommande> findLigneCommandeById(Integer id)
    {
          return repositiry.findLigneCommandeById(id);
    }

    public LiveData<List<LigneCommande>> findAllLigneCommande()
    {
        return  repositiry.findAllLigneCommande();
    }

    public LiveData<LigneCommande> findLigneCommandeByArticleIdAndCommandeId(Integer idA,Integer idC)
    {
        return repositiry.findLigneCommandeByArticleIdAndCommandeId(idA,idC);
    }

}
