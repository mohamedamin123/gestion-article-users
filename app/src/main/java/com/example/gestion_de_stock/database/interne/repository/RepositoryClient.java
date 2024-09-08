package com.example.gestion_de_stock.database.interne.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.gestion_de_stock.database.interne.DAO.ClientDAO;
import com.example.gestion_de_stock.database.interne.MyRoomDataBase;
import com.example.gestion_de_stock.database.interne.entity.Client;


import java.util.List;

public class RepositoryClient {

   ClientDAO clientDAO;


    public RepositoryClient(Application application) {
        MyRoomDataBase db=MyRoomDataBase.getDatabase(application);
        clientDAO= db.clientDAO();

    }

    public void insertClient(Client... Clients)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                clientDAO.insertClient(Clients);
            }
        });
    }
    public  void updateClient(Client... Clients)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                clientDAO.updateClient(Clients);
            }
        });
    }
    public void deleteClient(Client... Clients)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                clientDAO.deleteClient(Clients);
            }
        });
    }

    public void deleteClientById(Integer id)
    {
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                clientDAO.deleteClientById(id);
            }
        });
    }

    public LiveData<Client> findClientById(Integer id)
    {

              return  clientDAO.findClientById(id);

    }

    public LiveData<List<Client>> findAllClient()
    {
              return  clientDAO.findAllClient();
    }

    public LiveData<Client> findClientByNomOrPrenom(String nom,String prenom)
    {
        return clientDAO.findClientByNomOrPrenom(nom, prenom);
    }



}
