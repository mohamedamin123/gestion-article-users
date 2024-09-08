package com.example.gestion_de_stock.database.interne.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gestion_de_stock.database.interne.entity.Commande;
import com.example.gestion_de_stock.database.interne.repository.ReposirotyCommande;

import java.util.List;

public class ViewModelCommande extends AndroidViewModel {
    ReposirotyCommande repositiry;
    public ViewModelCommande(@NonNull Application application) {
        super(application);
        repositiry=new ReposirotyCommande(application);
    }

    public void insertCommande(Commande... Commandes)
    {
       repositiry.insertCommande(Commandes);
    }
    public  void updateCommande(Commande... Commandes)
    {
       repositiry.updateCommande(Commandes);
    }
    public void deleteCommande(Commande... Commandes)
    {
       repositiry.deleteCommande(Commandes);
    }

    public void deleteCommandeById(Integer id)
    {
       repositiry.deleteCommandeById(id);
    }

    public LiveData<Commande> findCommandeById(Integer id)
    {
          return repositiry.findCommandeById(id);
    }

    public LiveData<List<Commande>> findAllCommande()
    {
        return  repositiry.findAllCommande();
    }

    public LiveData<Commande> findCommandeByCLientId(Integer id)
    {
        return repositiry.findCommandeByCLientId(id);
    }

}
