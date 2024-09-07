package com.example.gestion_de_stock.database.interne.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Integer idUser;
    private String prenom;
    private String nom;
    private String telephone;
    private String email;




    public String getFulName() {
        return this.prenom + " "+this.nom;
    }
}
