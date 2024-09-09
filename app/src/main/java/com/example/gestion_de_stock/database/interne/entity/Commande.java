package com.example.gestion_de_stock.database.interne.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

import kotlin.BuilderInference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(
        tableName = "commande",
        foreignKeys = @ForeignKey(
                entity = Client.class,
                parentColumns = "idClient",
                childColumns = "idClient",
                onDelete = ForeignKey.CASCADE
        )
)public class Commande {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "commande_id")
    private Integer idCommande;
    private String modele;
    private float prix;
    private float qte;
    private float total;
    private float avance;
    private float reste;

    @ColumnInfo(name = "idClient")
    private Integer clientId; // Foreign key to Client table
//------------------------------------------------------------------------------------------Relation
    @Ignore
    private List<Photo> photos;

    public Integer getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Integer idCommande) {
        this.idCommande = idCommande;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getQte() {
        return qte;
    }

    public void setQte(float qte) {
        this.qte = qte;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getAvance() {
        return avance;
    }

    public void setAvance(float avance) {
        this.avance = avance;
    }

    public float getReste() {
        return reste;
    }

    public void setReste(float reste) {
        this.reste = reste;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
