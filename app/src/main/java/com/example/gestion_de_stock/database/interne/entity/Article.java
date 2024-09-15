package com.example.gestion_de_stock.database.interne.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
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
@Entity(tableName = "article")
public class Article implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idArticle")
    private Integer idArticle;
    private String nom;
    private float masse;
    private float prixM;
    private int qte;
    private float coutMand;


    @Ignore
    private List<Photo> photos;

    @Ignore
    protected Article(Parcel in) {
        if (in.readByte() == 0) {
            idArticle = null;
        } else {
            idArticle = in.readInt();
        }
        nom = in.readString();
        masse = in.readFloat();
        prixM = in.readFloat();
        qte = in.readInt();
        coutMand = in.readFloat();
    }

    @Ignore
    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public Article(Integer idArticle, String nom, float masse, float prixM, int qte, float mand) {
        this.idArticle = idArticle;
        this.nom = nom;
        this.masse = masse;
        this.prixM = prixM;
        this.qte = qte;
        this.coutMand = mand;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idArticle == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(idArticle);
        }
        dest.writeString(nom);
        dest.writeFloat(masse);
        dest.writeFloat(prixM);
        dest.writeInt(qte);
        dest.writeFloat(coutMand);
    }


    public Article() {
    }

    @Ignore
    public Article(String nom, float masse, float prixM, int qte, float coutMand) {
        this.nom = nom;
        this.masse = masse;
        this.prixM = prixM;
        this.qte = qte;
        this.coutMand = coutMand;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Integer idArticle) {
        this.idArticle = idArticle;
    }

    public float getMasse() {
        return masse;
    }

    public void setMasse(float masse) {
        this.masse = masse;
    }

    public float getPrixM() {
        return prixM;
    }

    public void setPrixM(float prixM) {
        this.prixM = prixM;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }


    public float getCoutMand() {
        return coutMand;
    }

    public void setCoutMand(float coutMand) {
        this.coutMand = coutMand;
    }
}
