package com.example.gestion_de_stock.database.interne.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(tableName = "article",
        foreignKeys = @ForeignKey(
                entity = Categories.class,
                parentColumns = "idCategorie",
                childColumns = "idCategorie",
                onDelete = ForeignKey.CASCADE))
public class Article {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "article_id")
    private Integer idArticle;
    private String ref;
    private String nom;
    private float prixU;
    private int stock;
    private Integer idCategorie;
//------------------------------------------------------------------------------------------Relation
    @Ignore
    private Categories categories;
    @Ignore
    private List<LigneCommande> ligneCommandes;
    @Ignore
    private List<Photo> photos;

    public Integer getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Integer idArticle) {
        this.idArticle = idArticle;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPrixU() {
        return prixU;
    }

    public void setPrixU(float prixU) {
        this.prixU = prixU;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Integer getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(Integer idCategorie) {
        this.idCategorie = idCategorie;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }
}
