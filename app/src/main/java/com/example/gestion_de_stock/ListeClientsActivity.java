package com.example.gestion_de_stock;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gestion_de_stock.adapter.CLientAdapter;
import com.example.gestion_de_stock.database.interne.entity.Client;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelClient;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelCommande;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelLigneCommande;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelPhoto;
import com.example.gestion_de_stock.databinding.ActivityListeClientsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListeClientsActivity extends AppCompatActivity implements CLientAdapter.onManipuleCLient {
    private static final int REQUEST_CALL_PHONE = 1;
    private ActivityListeClientsBinding binding;
    private CLientAdapter adapter;
    private List<Client> clients;
    private List<Client> filteredClients;
    private ViewModelClient modelClient;
    private ViewModelPhoto viewModelPhoto;
    private ViewModelCommande viewModelCommande;
    private ViewModelLigneCommande viewModelLigneCommande;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListeClientsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        modelClient = new ViewModelProvider(this).get(ViewModelClient.class);
        viewModelPhoto = new ViewModelProvider(this).get(ViewModelPhoto.class);
        viewModelCommande = new ViewModelProvider(this).get(ViewModelCommande.class);
        viewModelLigneCommande = new ViewModelProvider(this).get(ViewModelLigneCommande.class);


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
    public void onAppele(Client client) {
        String phoneNumber = client.getTelephone();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));

        // Check for permission
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
            return;
        }

        startActivity(callIntent);
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
            viewModelCommande.deleteCommandeByIdClient(id);
            modelClient.deleteClientById(id);
            Toast.makeText(this, getResources().getText(R.string.client_supprimer), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getText(R.string.error_supprimer_user), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, getResources().getText(R.string.client_non_trouve), Toast.LENGTH_SHORT).show();
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, can now make the call
            } else {
                // Permission denied
                Toast.makeText(this, getResources().getText(R.string.permission_denied_call), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
