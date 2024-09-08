package com.example.gestion_de_stock;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gestion_de_stock.adapter.CLientAdapter;
import com.example.gestion_de_stock.database.interne.entity.Client;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelClient;
import com.example.gestion_de_stock.databinding.ActivityListeClientsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListeClientsActivity extends AppCompatActivity implements CLientAdapter.onManipuleCLient {
    private ActivityListeClientsBinding binding;
    private CLientAdapter adapter;
    private List<Client> clients;
    private List<Client> filteredClients;
    private ViewModelClient modelClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListeClientsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        modelClient = new ViewModelProvider(this).get(ViewModelClient.class);

        // Initialiser les données
        clients = new ArrayList<>();
        filteredClients = new ArrayList<>();
        adapter = new CLientAdapter(ListeClientsActivity.this, filteredClients, this);

        binding.recylerClient.setLayoutManager(new LinearLayoutManager(this));
        binding.recylerClient.setAdapter(adapter);

        // Obtenir les clients depuis la base de données
        getUsers();

        // Configurer la barre de recherche
        binding.searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterUsers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // Configurer les boutons
        binding.btnAjouter.setOnClickListener(v -> {
            Intent intent = new Intent(ListeClientsActivity.this, AjouterClientActivity.class);
            intent.putExtra("user_id", -1);
            startActivity(intent);
        });

        binding.btnAnnuler.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(ListeClientsActivity.this, MainActivity.class));
        });
    }

    @Override
    public void onUserClick(Client client) {
            Intent intent = new Intent(ListeClientsActivity.this, ViewClientActivity.class);
            intent.putExtra("clientId", client.getIdClient());
            startActivity(intent);
    }

    @Override
    public void onDeleteCLient(Integer id, int position) {
        Client clientToRemove = null;
        for (Client client : clients) {
            if (client.getIdClient().equals(id)) {
                clientToRemove = client;
                break;
            }
        }
        if (clientToRemove != null) {
            clients.remove(clientToRemove);
            filteredClients.remove(clientToRemove);
            adapter.updateList(filteredClients);
            modelClient.deleteClientById(id);
            Toast.makeText(this, "Utilisateur supprimé avec ID: " + id, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Utilisateur non trouvé pour la suppression", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onChangeCLient(Integer id) {
        Client clientToEdit = null;
        for (Client client : clients) {
            if (client.getIdClient().equals(id)) {
                clientToEdit = client;
                break;
            }
        }

        if (clientToEdit != null) {
            Intent intent = new Intent(ListeClientsActivity.this, AjouterClientActivity.class);
            intent.putExtra("user_id", id);
            intent.putExtra("prenom", clientToEdit.getPrenom());
            intent.putExtra("nom", clientToEdit.getNom());
            intent.putExtra("tel", clientToEdit.getTelephone());
            intent.putExtra("email", clientToEdit.getEmail());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Utilisateur non trouvé pour l'édition", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUsers() {
        modelClient.findAllClient().observe(this, new Observer<List<Client>>() {
            @Override
            public void onChanged(List<Client> cls) {
                Log.i("ClientObserver", "Clients updated: " + cls.size());
                clients.clear();
                clients.addAll(cls);
                filterUsers(binding.searchBar.getText().toString()); // Apply filter after data is loaded
            }
        });
    }

    private void filterUsers(String query) {
        if (query.isEmpty()) {
            filteredClients.clear();
            filteredClients.addAll(clients);
        } else {
            filteredClients = clients.stream()
                    .filter(client -> client.getFullName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        adapter.updateList(filteredClients);
    }
}
