package com.example.gestion_de_stock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.gestion_de_stock.database.interne.entity.Client;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelClient;
import com.example.gestion_de_stock.database.shared.AjouterClientShared;
import com.example.gestion_de_stock.databinding.ActivityAjouterClientBinding;
import com.example.gestion_de_stock.databinding.ActivityListeClientsBinding;

public class AjouterClientActivity extends AppCompatActivity {

    private ActivityAjouterClientBinding binding;
    private AjouterClientShared shared;
    int userId;
    private ViewModelClient modelClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAjouterClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        modelClient=new ViewModelProvider(this).get(ViewModelClient.class);

        Intent intent= getIntent();

        binding.editEmail.setText(intent.getStringExtra("email"));
        binding.editTel.setText(intent.getStringExtra("tel"));
        binding.editPrenom.setText(intent.getStringExtra("prenom"));
        binding.editNom.setText(intent.getStringExtra("nom"));
        userId=intent.getIntExtra("user_id",-1);
        shared = new AjouterClientShared(this);
        if(userId==-1)
        {
            shared.saveData("","","","");
        }
        saveData();


        binding.btnSave.setOnClickListener(v -> {
            String nom = binding.editNom.getText().toString().trim();
            String prenom = binding.editPrenom.getText().toString().trim();
            String email = binding.editEmail.getText().toString().trim();
            String tel = binding.editTel.getText().toString().trim();
            Client client=Client.builder()
                    .nom(nom)
                    .prenom(prenom)
                    .email(email)
                    .telephone(tel)
                    .build();

            if(userId==-1) {
                modelClient.insertClient(client);
            } else {
                client.setIdClient(userId);
                modelClient.updateClient(client);
            }

            saveData();
            Toast.makeText(this, "Modifications sauvegardées", Toast.LENGTH_SHORT).show();
            finish(); // Revenir à la liste des clients
            startActivity(new Intent(AjouterClientActivity.this, ListeClientsActivity.class));
        });

        binding.btnRetour.setOnClickListener(v -> {
            shared.removeData();
            finish(); // Revenir à la liste des clients
            startActivity(new Intent(AjouterClientActivity.this, ListeClientsActivity.class));
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shared.removeData(); // Efface les données lorsque l'activité est détruite


    }

    private void saveData() {
        String nom = binding.editNom.getText().toString().trim();
        String prenom = binding.editPrenom.getText().toString().trim();
        String email = binding.editEmail.getText().toString().trim();
        String tel = binding.editTel.getText().toString().trim();

        shared.saveData(email, nom, prenom, tel);
    }
}
