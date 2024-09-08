package com.example.gestion_de_stock.database.interne.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestion_de_stock.database.interne.entity.LigneCommande;

import java.util.List;

@Dao
public interface LigneCommandeDAO {

    @Insert
    void insertLigneCommande(LigneCommande... LigneCommandes);
    @Update
    void updateLigneCommande(LigneCommande... LigneCommandes);
    @Delete
    void deleteLigneCommande(LigneCommande... LigneCommandes);



    @Query("delete from ligne_commande where ligne_commande_id=:id")
    void deleteLigneCommandeById(Integer id);

    @Query("select * from ligne_commande where ligne_commande_id=:id")
    LiveData<LigneCommande> findLigneCommandeById(Integer id);

    @Query("select * from ligne_commande")
    LiveData<List<LigneCommande>> findAllLigneCommande();

    @Query("select * from ligne_commande where idArticle = :idArticle")
    LiveData<LigneCommande> findLigneCommandeByArticleId(Integer idArticle);

    @Query("select * from ligne_commande where idCommande = :idCommande")
    LiveData<LigneCommande> findLigneCommandeByCommandeId(Integer idCommande);

    @Query("select * from ligne_commande where idArticle = :idArticle and idCommande = :idCommande")
    LiveData<LigneCommande> findLigneCommandeByArticleIdAndCommandeId(Integer idArticle,Integer idCommande);
}
