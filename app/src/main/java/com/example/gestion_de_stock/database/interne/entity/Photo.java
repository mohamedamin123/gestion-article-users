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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(tableName = "photo",
        foreignKeys = @ForeignKey(
                entity = Commande.class,
                parentColumns = "commande_id",
                childColumns = "commande_id",
                onDelete = ForeignKey.CASCADE))
public class Photo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "photo_id")
    private int id;

    @ColumnInfo(name = "photo_uris")
    private Uri photoUris;

    @ColumnInfo(name = "photo_url")
    private String photoUrl;

//----------------------------------------------------------------------------------------foreignKey
    @ColumnInfo(name = "commande_id")
    private Integer idCommande;

    @Ignore
    private Commande commande;


    public byte[] getImageByte() {
        return Base64.decode(photoUrl, Base64.DEFAULT);
    }

    public void setImageByte(byte[] photoBytes) {
        photoUrl = Base64.encodeToString(photoBytes, Base64.DEFAULT);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Uri getPhotoUris() {
        return photoUris;
    }

    public void setPhotoUris(Uri photoUris) {
        this.photoUris = photoUris;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Integer idCommande) {
        this.idCommande = idCommande;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }
}
