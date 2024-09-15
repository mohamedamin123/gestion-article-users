package com.example.gestion_de_stock.database.interne.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
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
@Builder
@Entity(
        tableName = "commande",
        foreignKeys = @ForeignKey(
                entity = Client.class,
                parentColumns = "idClient",
                childColumns = "idClient",
                onDelete = ForeignKey.CASCADE // Use CASCADE to delete dependent Commandes
        )
)
public class Commande implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "commande_id")
    private Integer idCommande;
    private String modele;
    private float prix;
    private float qte;
    private float total;
    private float avance;
    private float reste;
    private Boolean statut;

    @ColumnInfo(name = "idClient")
    private Integer clientId; // Foreign key to Client table

    // Relation with photos (not persisted in the database)
    @Ignore
    private List<Photo> photos;

    // Relation with LigneCommande
    @Ignore
    private List<LigneCommande> lignesCommande;

    public Commande() {}

    // Parcelable constructor (ignored by Room)
    @Ignore
    protected Commande(Parcel in) {
        if (in.readByte() == 0) {
            idCommande = null;
        } else {
            idCommande = in.readInt();
        }
        modele = in.readString();
        prix = in.readFloat();
        qte = in.readFloat();
        total = in.readFloat();
        avance = in.readFloat();
        reste = in.readFloat();
        statut = in.readByte() != 0;  // Convert byte to boolean
        if (in.readByte() == 0) {
            clientId = null;
        } else {
            clientId = in.readInt();
        }
    }

    @Ignore
    public static final Creator<Commande> CREATOR = new Creator<Commande>() {
        @Override
        public Commande createFromParcel(Parcel in) {
            return new Commande(in);
        }

        @Override
        public Commande[] newArray(int size) {
            return new Commande[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idCommande == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(idCommande);
        }
        dest.writeString(modele);
        dest.writeFloat(prix);
        dest.writeFloat(qte);
        dest.writeFloat(total);
        dest.writeFloat(avance);
        dest.writeFloat(reste);
        dest.writeByte((byte) (statut == null || !statut ? 0 : 1)); // Convert boolean to byte

        if (clientId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(clientId);
        }
    }

    // Getter and setter methods...

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

    public Boolean getStatut() {
        return statut;
    }

    public void setStatut(Boolean statut) {
        this.statut = statut;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
