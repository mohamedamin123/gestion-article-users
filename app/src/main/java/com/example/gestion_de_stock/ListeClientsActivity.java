package com.example.gestion_de_stock;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gestion_de_stock.adapter.CLientAdapter;
import com.example.gestion_de_stock.database.interne.entity.User;
import com.example.gestion_de_stock.databinding.ActivityListeClientsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListeClientsActivity extends AppCompatActivity implements CLientAdapter.onManipuleCLient {
    private ActivityListeClientsBinding binding;
    private CLientAdapter adapter;
    private List<User> users;
    private List<User> filteredUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListeClientsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialiser les données
        getUsers();
        filteredUsers = new ArrayList<>(users); // Initialiser filteredUsers avec tous les utilisateurs

        // Configurer le RecyclerView et l'adaptateur
        adapter = new CLientAdapter(ListeClientsActivity.this, filteredUsers, this);
        binding.recylerClient.setLayoutManager(new LinearLayoutManager(this));
        binding.recylerClient.setAdapter(adapter);

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
            finish();
            Intent intent = new Intent(ListeClientsActivity.this, AjouterClientActivity.class);
            intent.putExtra("user_id",-1);
            startActivity(intent);
        });

        binding.btnAnnuler.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(ListeClientsActivity.this, MainActivity.class));
        });
    }

    private void getUsers() {
        users = new ArrayList<>();
        users.add(new User(1, "amin", "gana", "95147455", "amin@amin.com"));
        users.add(new User(2, "sarah", "ben", "91234567", "sarah.ben@example.com"));
        users.add(new User(3, "mohamed", "ali", "98234567", "mohamed.ali@example.com"));
        users.add(new User(4, "ahmed", "kouka", "93214567", "ahmed.kouka@example.com"));
        users.add(new User(5, "yasmin", "rahal", "91235678", "yasmin.rahal@example.com"));
        users.add(new User(6, "houssem", "belhadj", "91123456", "houssem.belhadj@example.com"));

    }

    private void filterUsers(String query) {
        if (query.isEmpty()) {
            filteredUsers.clear();
            filteredUsers.addAll(users);
        } else {
            filteredUsers = users.stream()
                    .filter(user -> user.getFulName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        adapter.updateList(filteredUsers); // Informer l'adaptateur du changement
    }

    @Override
    public void onUserClick(User user) {
        Toast.makeText(this, "Utilisateur cliqué : " + user.getPrenom() + " " + user.getNom(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteCLient(Integer id, int position) {
        // Trouvez l'utilisateur en fonction de l'ID dans la liste users
        User userToRemove = null;
        for (User user : users) {
            if (user.getIdUser().equals(id)) {
                userToRemove = user;
                break;
            }
        }
        if (userToRemove != null) {
            // Supprimer l'utilisateur de la liste users
            users.remove(userToRemove);

            // Supprimer l'utilisateur de la liste filteredUsers
            filteredUsers.remove(userToRemove);

            // Informer l'adaptateur du changement
            adapter.updateList(filteredUsers); // Assurez-vous que la méthode updateList est bien implémentée

            // Afficher un message de confirmation
            Toast.makeText(this, "Utilisateur supprimé avec ID: " + id, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Utilisateur non trouvé pour la suppression", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onChangeCLient(Integer id) {
        User userToEdit = null;
        for (User user : users) {
            if (user.getIdUser().equals(id)) {
                userToEdit = user;
                break;
            }
        }

        if (userToEdit != null) {
            Intent intent = new Intent(ListeClientsActivity.this, AjouterClientActivity.class);
            intent.putExtra("user_id", id);
            intent.putExtra("prenom", userToEdit.getPrenom());
            intent.putExtra("nom", userToEdit.getNom());
            intent.putExtra("tel", userToEdit.getTelephone());
            intent.putExtra("email", userToEdit.getEmail());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Utilisateur non trouvé pour l'édition", Toast.LENGTH_SHORT).show();
        }
    }

}
