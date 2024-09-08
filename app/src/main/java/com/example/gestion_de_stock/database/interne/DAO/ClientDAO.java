package com.example.gestion_de_stock.database.interne.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestion_de_stock.database.interne.entity.Client;

import java.util.List;

@Dao
public interface ClientDAO {

    @Insert
    void insertClient(Client... Clients);
    @Update
    void updateClient(Client... Clients);
    @Delete
    void deleteClient(Client... Clients);



    @Query("delete from client where idClient=:id")
    void deleteClientById(Integer id);

    @Query("select * from client where idClient=:id")
    LiveData<Client> findClientById(Integer id);

    @Query("select * from client")
    LiveData<List<Client>> findAllClient();

    @Query("select * from Client where nom like '%' || :nom || '%' or prenom like '%' || :prenom || '%'")
    LiveData<Client> findClientByNomOrPrenom(String nom, String prenom);
}
