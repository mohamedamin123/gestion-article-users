package com.example.gestion_de_stock.database.interne.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestion_de_stock.database.interne.entity.Commande;
import com.example.gestion_de_stock.database.interne.entity.LigneCommande;

import java.util.List;

@Dao
public interface LigneCommandeDAO {

    @Insert
    void insertLigneCommandes(LigneCommande... ligneCommandes);

    @Update
    void updateLigneCommande(LigneCommande... ligneCommandes);

    @Delete
    void deleteLigneCommande(LigneCommande... ligneCommandes);

    @Query("select * from ligne_commande")
    LiveData<List<LigneCommande>> findAllLigneCommande(); // Correct return type

    @Query("DELETE FROM ligne_commande WHERE ligne_commande_id = :id")
    void deleteLigneCommandeById(Integer id);

    @Query("DELETE FROM ligne_commande WHERE commande_id = :id")
    void deleteLigneCommandeByIdCommande(Integer id);

    @Query("SELECT * FROM ligne_commande WHERE ligne_commande_id = :id")
    LiveData<LigneCommande> findLigneCommandeById(Integer id);

    @Query("SELECT * FROM ligne_commande WHERE commande_id = :id")
    LiveData<List<LigneCommande>> findLigneCommandeByIdCommande(Integer id);

    @Query("SELECT * FROM ligne_commande WHERE commande_id = :commande_id")
    LiveData<List<LigneCommande>> findAllLigneCommandeByCommandeId(int commande_id);
}
