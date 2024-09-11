package com.example.gestion_de_stock.database.interne.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(
        tableName = "ligne_commande",
        foreignKeys = @ForeignKey(
                entity = Commande.class,
                parentColumns = "commande_id",
                childColumns = "commande_id",
                onDelete = ForeignKey.CASCADE
        )
)
public class LigneCommande implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ligne_commande_id")
    private Integer idLigneCommande;

    private String color;
    private int qte;

    @ColumnInfo(name = "commande_id")
    private Integer commandeId; // Foreign key to Commande table

    public LigneCommande(String color, int qte, Integer commandeId) {
        this.color = color;
        this.qte = qte;
        this.commandeId = commandeId;
    }


    @Ignore
    protected LigneCommande(Parcel in) {
        if (in.readByte() == 0) {
            idLigneCommande = null;
        } else {
            idLigneCommande = in.readInt();
        }
        color = in.readString();
        qte = in.readInt();
        if (in.readByte() == 0) {
            commandeId = null;
        } else {
            commandeId = in.readInt();
        }
    }

    @Ignore
    public static final Creator<LigneCommande> CREATOR = new Creator<LigneCommande>() {
        @Override
        public LigneCommande createFromParcel(Parcel in) {
            return new LigneCommande(in);
        }

        @Override
        public LigneCommande[] newArray(int size) {
            return new LigneCommande[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idLigneCommande == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(idLigneCommande);
        }
        dest.writeString(color);
        dest.writeInt(qte);
        if (commandeId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(commandeId);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Integer getIdLigneCommande() {
        return idLigneCommande;
    }

    public void setIdLigneCommande(Integer idLigneCommande) {
        this.idLigneCommande = idLigneCommande;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Integer getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Integer commandeId) {
        this.commandeId = commandeId;
    }
}
