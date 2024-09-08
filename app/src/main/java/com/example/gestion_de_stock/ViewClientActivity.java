package com.example.gestion_de_stock;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gestion_de_stock.adapter.ClientTabAdapter;
import com.example.gestion_de_stock.database.interne.entity.Client;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelClient;
import com.example.gestion_de_stock.databinding.ActivityViewClientBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewClientActivity extends AppCompatActivity  implements ClientTabAdapter.OnClientClickListener{

    private ActivityViewClientBinding binding;
    private List<Client> data;
    private ViewModelClient modelClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Utilisation de ViewBinding
        binding = ActivityViewClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        modelClient = new ViewModelProvider(this).get(ViewModelClient.class);
        Intent intent=getIntent();
        int clientId=intent.getIntExtra("clientId",-1);

        if(clientId!=-1) {
            // Récupération des données du client via l'API

            modelClient.findClientById(clientId).observe(this, new Observer<Client>() {
                @Override
                public void onChanged(Client client) {
                    if(client!=null) {
                            binding.title.setText(client.getFullName());
                    } else {
                        Toast.makeText(ViewClientActivity.this, "Client non trouvé", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(ViewClientActivity.this, ListeClientsActivity.class));
                    }
                }
            });
        }




        // Configure RecyclerView avec un LayoutManager horizontal
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerClient.setLayoutManager(layoutManager);

        // Création d'une liste de clients fictifs pour l'exemple
        data=new ArrayList<>();
        getData();

        // Initialisation de l'adaptateur et liaison avec RecyclerView
        ClientTabAdapter adapter = new ClientTabAdapter(data,this);
        binding.recyclerClient.setAdapter(adapter);

        // Gestion du bouton Annuler
        binding.buttonAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ViewClientActivity.this, ListeClientsActivity.class));
            }
        });

        binding.buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créez une nouvelle intent pour l'activité AjouterClientActivity
                Intent intent = new Intent(ViewClientActivity.this, AjouterModeleActivity.class);
                intent.putExtra("clientId",clientId);
                // Démarrer l'activité
                startActivity(intent);
            }
        });
    }



    @Override
    public void onClientClick(Client client) {
        // Créez un builder pour l'AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Action pour " + client.getFullName());
        builder.setMessage("Que voulez-vous faire avec ce client ?");

        // Ajouter les boutons à l'alerte
        builder.setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Action pour le bouton "Modifier"
                Toast.makeText(ViewClientActivity.this, "Modifier " + client.getFullName(), Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Ajouter image", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Action pour le bouton "Supprimer"
                Toast.makeText(ViewClientActivity.this, "Ajouter image " + client.getFullName(), Toast.LENGTH_SHORT).show();
            }
        });

        // Montrer la boîte de dialogue
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void getData() {
        data.add(new Client(1, "John", "Doe", "1234567890", "john.doe@example.com"));
        data.add(new Client(2, "Jane", "Doe", "0987654321", "jane.doe@example.com"));
        data.add(new Client(3, "Alice", "Smith", "1112233445", "alice.smith@example.com"));
        data.add(new Client(4, "Bob", "Brown", "5556677889", "bob.brown@example.com"));
        data.add(new Client(5, "Charlie", "Johnson", "9990001112", "charlie.johnson@example.com"));
    }
}
