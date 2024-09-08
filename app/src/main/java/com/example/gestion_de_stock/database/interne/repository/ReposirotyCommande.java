package com.example.gestion_de_stock.database.interne.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.gestion_de_stock.database.interne.DAO.CommandeDAO;
import com.example.gestion_de_stock.database.interne.MyRoomDataBase;
import com.example.gestion_de_stock.database.interne.entity.Commande;

import java.util.List;

public class ReposirotyCommande {

   CommandeDAO CommandeDAO;


    public ReposirotyCommande(Application application) {
        MyRoomDataBase db=MyRoomDataBase.getDatabase(application);
        CommandeDAO= db.commandeDAO();

    }

    public void insertCommande(Commande... Commandes)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                CommandeDAO.insertCommande(Commandes);
            }
        });
    }
    public  void updateCommande(Commande... Commandes)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                CommandeDAO.updateCommande(Commandes);
            }
        });
    }
    public void deleteCommande(Commande... Commandes)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                CommandeDAO.deleteCommande(Commandes);
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

    public LiveData<List<Commande>> findAllCommande()
    {
              return  CommandeDAO.findAllCommande();
    }

    public LiveData<Commande> findCommandeByCLientId(Integer id)
    {
        return CommandeDAO.findCommandeByCLientId(id);
    }



}
