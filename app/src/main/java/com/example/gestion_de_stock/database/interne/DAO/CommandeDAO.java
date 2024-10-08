package com.example.gestion_de_stock.database.interne.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestion_de_stock.database.interne.entity.Client;
import com.example.gestion_de_stock.database.interne.entity.Commande;

import java.util.List;

@Dao
public interface CommandeDAO {

    @Insert
    Long[] insertCommande(Commande... commandes);  //// Returns the row ID(s) of inserted commandes
    @Update
    void updateCommande(Commande... commandes);
    @Delete
    void deleteCommande(Commande... commandes);



    @Query("delete from Commande where Commande_id=:id")
    void deleteCommandeById(Integer id);

    @Query("delete from Commande where idClient=:id")
    void deleteCommandeByIdClient(Integer id);

    @Query("select * from Commande where Commande_id=:id")
    LiveData<Commande> findCommandeById(Integer id);

    @Query("select * from Commande where idClient=:id")
    LiveData<List<Commande>>  findCommandeByIdCLient(Integer id);

    @Query("select * from Commande")
    LiveData<List<Commande>> findAllCommande();

    @Query("select * from Commande where modele like '%' || :nom || '%' ")
    LiveData<Commande> findCommandeByNom(String nom);

    @Query("SELECT idClient FROM COMMANDE  WHERE statut = 0")
    LiveData<List<Integer>> findAllClientNonFinished();
}
