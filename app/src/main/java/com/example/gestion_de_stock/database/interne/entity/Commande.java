package com.example.gestion_de_stock.database.interne.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.gestion_de_stock.util.DateConverteur;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TypeConverters({DateConverteur.class})
@Entity(tableName = "commande",
        foreignKeys = @ForeignKey(
                entity = Client.class,
                parentColumns = "idClient",
                childColumns = "idClient",
                onDelete = ForeignKey.CASCADE))
public class Commande {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "commande_id")
    private Integer idCommande;
    private LocalDate dateCommande;
    private float prixTotal;
//----------------------------------------------------------------------------------------ForeignKey
    private Integer idClient;
//------------------------------------------------------------------------------------------Relation
    @Ignore
    private Client client;
    @Ignore
    private List<LigneCommande> ligneCommandes;

    public Integer getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Integer idCommande) {
        this.idCommande = idCommande;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public float getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(float prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }
}
