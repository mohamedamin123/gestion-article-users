package com.example.gestion_de_stock.database.interne.entity;

import android.net.Uri;
import android.util.Base64;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity(tableName = "photo",
        foreignKeys = @ForeignKey(
                entity = Commande.class,
                parentColumns = "commande_id",
                childColumns = "commande_id",
                onDelete = ForeignKey.CASCADE ))// Use CASCADE to delete related Commandes when a Client is deleted
public class Photo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "photo_id")
    private int id;

    @ColumnInfo(name = "photo_byte")
    private byte[] photoBytes;

    @ColumnInfo(name = "commande_id")
    private Integer idCommande;

    // Constructors
    public Photo() {
    }
    @Ignore
    public Photo(byte[] photoBytes) {
        this.photoBytes = photoBytes;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getPhotoBytes() {
        return photoBytes;
    }

    public void setPhotoBytes(byte[] photoBytes) {
        this.photoBytes = photoBytes;
    }

    public Integer getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Integer idCommande) {
        this.idCommande = idCommande;
    }
}
