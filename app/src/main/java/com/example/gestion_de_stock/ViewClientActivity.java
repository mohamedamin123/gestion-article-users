package com.example.gestion_de_stock;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gestion_de_stock.adapter.GenericAdapter;
import com.example.gestion_de_stock.database.interne.entity.Client;
import com.example.gestion_de_stock.database.interne.entity.Commande;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelCommande;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelClient;
import com.example.gestion_de_stock.databinding.ActivityViewClientBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewClientActivity extends AppCompatActivity implements GenericAdapter.OnItemClickListener {

    private ActivityViewClientBinding binding;
    private List<Commande> data; // Changed to List<Object> to handle multiple types
    private GenericAdapter adapter;
    private ViewModelCommande modelCommande;
    private ViewModelClient modelClient;
    int clientId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Utilisation de ViewBinding
        binding = ActivityViewClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModels
        modelCommande = new ViewModelProvider(this).get(ViewModelCommande.class);
        modelClient = new ViewModelProvider(this).get(ViewModelClient.class);

        // Configure RecyclerView avec un LayoutManager vertical
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerClient.setLayoutManager(layoutManager);



        modelClient = new ViewModelProvider(this).get(ViewModelClient.class);
        Intent intent = getIntent();
        clientId = intent.getIntExtra("clientId", -1);

        if (clientId != -1) {
            // Récupération des données du client via l'API
            modelClient.findClientById(clientId).observe(this, new Observer<Client>() {
                @Override
                public void onChanged(Client client) {
                    if (client != null) {
                        binding.title.setText(client.getFullName());
                    } else {
                        Toast.makeText(ViewClientActivity.this, "Client non trouvé", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(ViewClientActivity.this, ListeClientsActivity.class));
                    }
                }
            });
        }

        // Création d'une liste de données
        data = new ArrayList<>();
        adapter = new GenericAdapter(data, this);
        binding.recyclerClient.setAdapter(adapter);

        // Charger les données
        getData(clientId);

        // Gestion du bouton Annuler
        binding.buttonAnnuler.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(ViewClientActivity.this, ListeClientsActivity.class));
        });

        binding.buttonAjouter.setOnClickListener(v -> {
            // Créez une nouvelle intent pour l'activité AjouterClientActivity
            Intent intent1 = new Intent(ViewClientActivity.this, AjouterModeleActivity.class);
            intent1.putExtra("clientId", clientId);
            // Démarrer l'activité
            startActivity(intent1);
        });
    }

    @Override
    public void onItemClick(Object item) {
        if (item instanceof Commande) {
            Commande commande = (Commande) item;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Action pour " + commande.getModele());
            builder.setMessage("Que voulez-vous faire avec ce client ?");

            // Ajouter les boutons à l'alerte
            builder.setPositiveButton("Ajouter image", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Action pour le bouton "Modifier"
                    Toast.makeText(ViewClientActivity.this, "ajouter " , Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Action pour le bouton "Supprimer"
                    Toast.makeText(ViewClientActivity.this, "Supprimer", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNeutralButton("Modifier", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Action pour le bouton "Supprimer"
                    Toast.makeText(ViewClientActivity.this, "Modifier ", Toast.LENGTH_SHORT).show();
                }
            });

            // Montrer la boîte de dialogue
            AlertDialog dialog = builder.create();
            dialog.show();

        }

}

private void getData(int id) {
    // Clear previous data
    data.clear();


    // Fetch Article data
    modelCommande.findCommandeByIdCLient(id).observe(this, new Observer<List<Commande>>() {
        @Override
        public void onChanged(List<Commande> commandes) {
            for (Commande commande : commandes) {
                data.add(commande);
            }
            // Notify the adapter that the data has changed
            adapter.notifyDataSetChanged();
        }
    });


}
}
