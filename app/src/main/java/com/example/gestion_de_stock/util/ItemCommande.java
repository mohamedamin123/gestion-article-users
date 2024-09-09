package com.example.gestion_de_stock.util;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCommande implements Parcelable {

    private String color;
    private int qte;

    protected ItemCommande(Parcel in) {
        color = in.readString();
        qte = in.readInt();
    }

    public static final Creator<ItemCommande> CREATOR = new Creator<ItemCommande>() {
        @Override
        public ItemCommande createFromParcel(Parcel in) {
            return new ItemCommande(in);
        }

        @Override
        public ItemCommande[] newArray(int size) {
            return new ItemCommande[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(color);
        dest.writeInt(qte);
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
