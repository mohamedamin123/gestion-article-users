package com.example.gestion_de_stock.database.interne.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gestion_de_stock.database.interne.DAO.CommandeDAO;
import com.example.gestion_de_stock.database.interne.MyRoomDataBase;
import com.example.gestion_de_stock.database.interne.entity.Client;
import com.example.gestion_de_stock.database.interne.entity.Commande;

import java.util.List;

public class RepositoryCommande {

   CommandeDAO CommandeDAO;


    public RepositoryCommande(Application application) {
        MyRoomDataBase db=MyRoomDataBase.getDatabase(application);
        CommandeDAO= db.commandeDAO();

    }

    public LiveData<Long[]> insertCommande(Commande... commandes) {
        MutableLiveData<Long[]> liveData = new MutableLiveData<>();
        MyRoomDataBase.databaseWriteExecutor.execute(() -> {
            Long[] insertedIds = CommandeDAO.insertCommande(commandes);
            liveData.postValue(insertedIds); // Post the IDs to LiveData
        });
        return liveData;
    }
    public  void updateCommande(Commande... commandes)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                CommandeDAO.updateCommande(commandes);
            }
        });
    }
    public void deleteCommande(Commande... commandes)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                CommandeDAO.deleteCommande(commandes);
            }
        });
    }

    public void deleteCommandeByIdClient(Integer id)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                CommandeDAO.deleteCommandeByIdClient(id);
            }
        });
    }

    public void deleteCommandeById(Integer id)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                CommandeDAO.deleteCommandeById(id);
            }
        });
    }

    public LiveData<Commande> findCommandeById(Integer id)
    {

              return  CommandeDAO.findCommandeById(id);

    }

    public LiveData<List<Commande>>  findCommandeByIdCLient(Integer id)
    {

        return  CommandeDAO.findCommandeByIdCLient(id);

    }

    public LiveData<List<Commande>> findAllCommande()
    {
              return  CommandeDAO.findAllCommande();
    }

    public LiveData<Commande> findCommandeByNom(String nom)
    {
        return CommandeDAO.findCommandeByNom(nom);
    }


    public LiveData<List<Integer>> findAllClientNonFinished()
    {
        return  CommandeDAO.findAllClientNonFinished();
    }


}
