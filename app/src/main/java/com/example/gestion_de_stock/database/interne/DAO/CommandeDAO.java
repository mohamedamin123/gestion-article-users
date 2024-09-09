package com.example.gestion_de_stock.database.interne.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestion_de_stock.database.interne.entity.Commande;

import java.util.List;

@Dao
public interface CommandeDAO {

    @Insert
    void insertCommande(Commande... commandes);
    @Update
    void updateCommande(Commande... commandes);
    @Delete
    void deleteCommande(Commande... commandes);



    @Query("delete from Commande where Commande_id=:id")
    void deleteCommandeById(Integer id);

    @Query("select * from Commande where Commande_id=:id")
    LiveData<Commande> findCommandeById(Integer id);

    @Query("select * from Commande where idClient=:id")
    LiveData<List<Commande>>  findCommandeByIdCLient(Integer id);

    @Query("select * from Commande")
    LiveData<List<Commande>> findAllCommande();

    @Query("select * from Commande where modele like '%' || :nom || '%' ")
    LiveData<Commande> findCommandeByNom(String nom);
}
