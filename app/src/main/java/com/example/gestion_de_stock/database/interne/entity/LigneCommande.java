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
@Entity(tableName = "ligne_commande",
        foreignKeys = {
                @ForeignKey(entity = Commande.class, parentColumns = "commande_id", childColumns = "idCommande", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Article.class, parentColumns = "article_id", childColumns = "idArticle", onDelete = ForeignKey.CASCADE)
        })public class LigneCommande {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ligne_commande_id")
    private int idLigneCommande;
    private int quantite;
    private float prix;
//----------------------------------------------------------------------------------------foreignKey
    private Integer idCommande;

    private Integer idArticle;
//-----------------------------------------------------------------------------------------relations
    @Ignore
    private Commande commande;
    @Ignore
    private Article article;

    public int getIdLigneCommande() {
        return idLigneCommande;
    }

    public void setIdLigneCommande(int idLigneCommande) {
        this.idLigneCommande = idLigneCommande;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
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
