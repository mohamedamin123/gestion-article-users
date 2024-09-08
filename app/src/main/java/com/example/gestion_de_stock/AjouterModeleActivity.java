package com.example.gestion_de_stock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.gestion_de_stock.alert.AjouterCommandeFragment;
import com.example.gestion_de_stock.databinding.ActivityAjouterModeleBinding;
import com.example.gestion_de_stock.databinding.ActivityViewClientBinding;

public class AjouterModeleActivity extends AppCompatActivity {

    private ActivityAjouterModeleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAjouterModeleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent=getIntent();
        int clientId=intent.getIntExtra("clientId",-1);


        binding.editCommande.setFocusable(false);
        binding.editCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.editCommande.setFocusable(true);
                binding.editCommande.setFocusableInTouchMode(true);
                FragmentManager fragmentManager = getSupportFragmentManager();
                AjouterCommandeFragment dialog = new AjouterCommandeFragment();
                dialog.show(fragmentManager, "AjouterCommandeFragment");
                binding.editCommande.setFocusable(true);

            }
        });

        binding.btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AjouterModeleActivity.this, ViewClientActivity.class);
                intent.putExtra("clientId",clientId);
                // Démarrer l'activité
                startActivity(intent);
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AjouterModeleActivity.this, ViewClientActivity.class);
                intent.putExtra("clientId",clientId);
                // Démarrer l'activité
                startActivity(intent);
            }
        });
    }


}