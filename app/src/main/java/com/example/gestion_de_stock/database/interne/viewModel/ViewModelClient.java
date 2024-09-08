package com.example.gestion_de_stock.database.interne.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gestion_de_stock.database.interne.entity.Client;
import com.example.gestion_de_stock.database.interne.repository.RepositoryClient;

import java.util.List;

public class ViewModelClient extends AndroidViewModel {
    RepositoryClient repositiry;
    public ViewModelClient(@NonNull Application application) {
        super(application);
        repositiry=new RepositoryClient(application);
    }

    public void insertClient(Client... Clients)
    {
       repositiry.insertClient(Clients);
    }
    public  void updateClient(Client... Clients)
    {
       repositiry.updateClient(Clients);
    }
    public void deleteClient(Client... Clients)
    {
       repositiry.deleteClient(Clients);
    }

    public void deleteClientById(Integer id)
    {
       repositiry.deleteClientById(id);
    }

    public LiveData<Client> findClientById(Integer id)
    {
          return repositiry.findClientById(id);
    }

    public LiveData<List<Client>> findAllClient()
    {
        return  repositiry.findAllClient();
    }

    public LiveData<Client> findClientByNomOrPrenom(String nom,String prenom)
    {
        return repositiry.findClientByNomOrPrenom(nom,prenom);
    }

}
