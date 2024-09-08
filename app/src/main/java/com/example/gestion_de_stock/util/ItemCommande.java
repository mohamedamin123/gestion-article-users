package com.example.gestion_de_stock.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCommande {

    private String color;
    private int qte;
}
