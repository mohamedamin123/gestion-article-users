package com.example.gestion_de_stock.database.interne.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gestion_de_stock.database.interne.entity.Commande;
import com.example.gestion_de_stock.database.interne.repository.RepositoryCommande;

import java.util.List;

public class ViewModelCommande extends AndroidViewModel {
    RepositoryCommande repositiry;
    public ViewModelCommande(@NonNull Application application) {
        super(application);
        repositiry=new RepositoryCommande(application);
    }

    public LiveData<Long[]> insertCommande(Commande... commandes) {
        return repositiry.insertCommande(commandes);
    }
    public  void updateCommande(Commande... commandes)
    {
       repositiry.updateCommande(commandes);
    }
    public void deleteCommande(Commande... commandes)
    {
       repositiry.deleteCommande(commandes);
    }

    public void deleteCommandeByIdClient(Integer id)
    {
       repositiry.deleteCommandeByIdClient(id);
    }

    public void deleteCommandeById(Integer id)
    {
        repositiry.deleteCommandeById(id);
    }


    public LiveData<Commande> findCommandeById(Integer id)
    {
          return repositiry.findCommandeById(id);
    }

    public LiveData<List<Commande>> findCommandeByIdCLient(Integer id)
    {
        return repositiry.findCommandeByIdCLient(id);
    }

    public LiveData<List<Commande>> findAllCommande()
    {
        return  repositiry.findAllCommande();
    }

    public LiveData<Commande> findCommandeByNom(String nom)
    {
        return repositiry.findCommandeByNom(nom);
    }

}
