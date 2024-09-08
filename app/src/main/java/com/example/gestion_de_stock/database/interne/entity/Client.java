package com.example.gestion_de_stock.database.interne.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(tableName = "client")
public class Client {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idClient")
    private Integer idClient;
    private String prenom;
    private String nom;
    private String telephone;
    private String email;
    private Boolean statut;

    @Ignore
    private List<Commande> commandes;

    // Constructor with only necessary fields, marked as @Ignore for Room
    @Ignore
    public Client(String prenom, String nom, String telephone, String email) {
        this.prenom = prenom;
        this.nom = nom;
        this.telephone = telephone;
        this.email = email;
    }

    // Constructor with idClient, also marked as @Ignore for Room
    public Client(Integer idClient, String prenom, String nom, String telephone, String email) {
        this(prenom, nom, telephone, email);
        this.idClient = idClient;
    }

    // Optional custom method for full name
    public String getFullName() {

        return this.prenom + " " + this.nom;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatut() {
        return statut;
    }

    public void setStatut(Boolean statut) {
        this.statut = statut;
    }
}
