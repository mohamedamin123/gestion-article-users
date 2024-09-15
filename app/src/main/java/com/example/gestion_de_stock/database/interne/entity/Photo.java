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
        foreignKeys = {
                @ForeignKey(
                        entity = Commande.class,
                        parentColumns = "commande_id",
                        childColumns = "commande_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Article.class,
                        parentColumns = "idArticle",
                        childColumns = "article_id",
                        onDelete = ForeignKey.CASCADE
                )
        })
public class Photo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "photo_id")
    private int id;

    @ColumnInfo(name = "photo_byte")
    private byte[] photoBytes;

    @ColumnInfo(name = "commande_id")
    private Integer idCommande;

    @ColumnInfo(name = "article_id")
    private Integer idArticle;

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

    public Integer getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Integer idArticle) {
        this.idArticle = idArticle;
    }
}
