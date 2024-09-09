package com.example.gestion_de_stock;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.gestion_de_stock.alert.AjouterCommandeFragment;
import com.example.gestion_de_stock.database.interne.entity.Commande;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelCommande;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelClient;
import com.example.gestion_de_stock.databinding.ActivityAjouterModeleBinding;

public class AjouterModeleActivity extends AppCompatActivity implements AjouterCommandeFragment.OnDataPass {

    private ActivityAjouterModeleBinding binding;
    private ViewModelCommande viewModelCommande;
    private ViewModelClient viewModelClient;
    private int clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAjouterModeleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModels
        viewModelCommande = new ViewModelProvider(this).get(ViewModelCommande.class);
        viewModelClient = new ViewModelProvider(this).get(ViewModelClient.class);

        Intent intent = getIntent();
        clientId = intent.getIntExtra("clientId", -1);

        // Setup click listener for EditText to open dialog
        binding.editCommande.setFocusable(false);
        binding.editCommande.setOnClickListener(v -> {
            AjouterCommandeFragment dialog = new AjouterCommandeFragment();
            dialog.show(getSupportFragmentManager(), "AjouterCommandeFragment");
        });

        // Setup click listener for "Retour" button
        binding.btnRetour.setOnClickListener(v -> {
            Intent backIntent = new Intent(AjouterModeleActivity.this, ViewClientActivity.class);
            backIntent.putExtra("clientId", clientId);
            startActivity(backIntent);
        });

        // Setup click listener for "Enregistrer" button
        binding.btnSave.setOnClickListener(v -> {
            saveData();
        });

        // Setup TextWatchers for price and quantity fields
        binding.editPrix.addTextChangedListener(new PriceTextWatcher());
        binding.editCommande.addTextChangedListener(new QuantityTextWatcher());
        binding.editAvance.addTextChangedListener(new AvanceTextWatcher());
    }

    private void saveData() {
        // Validate clientId
        if (clientId == -1) {
            Toast.makeText(this, "Invalid client ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve data from fields
        String articleName = binding.editModele.getText().toString();
        if (articleName.isEmpty()) {
            Toast.makeText(this, "Please enter a model name", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float price = Float.parseFloat(binding.editPrix.getText().toString());
            float quantity = Float.parseFloat(binding.editCommande.getText().toString());
            float advance = Float.parseFloat(binding.editAvance.getText().toString());
            float reste = Float.parseFloat(binding.editReste.getText().toString());
            float total = Float.parseFloat(binding.editTotal.getText().toString());

            // Create a new Commande
            Commande commande = new Commande();
            commande.setModele(articleName);
            commande.setPrix(price);
            commande.setAvance(advance);
            commande.setQte(quantity);
            commande.setReste(reste);
            commande.setTotal(total);
            commande.setClientId(clientId);

            // Insert Commande into the database
            viewModelCommande.insertCommande(commande);

            // Clear fields after saving
            clearFields();

            // Notify user and navigate back
            Toast.makeText(this, "Commande added successfully", Toast.LENGTH_SHORT).show();
            Intent backIntent = new Intent(AjouterModeleActivity.this, ViewClientActivity.class);
            backIntent.putExtra("clientId", clientId);
            startActivity(backIntent);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input. Please check your data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        binding.editModele.setText("");
        binding.editPrix.setText("");
        binding.editCommande.setText("");
        binding.editAvance.setText("");
        binding.editReste.setText("");
        binding.editTotal.setText("");
    }

    @Override
    public void onDataPass(float data) {
        binding.editCommande.setText(String.valueOf(data));
    }

    private class PriceTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateTotal();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }

    private class QuantityTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateTotal();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }

    private class AvanceTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateReste();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }

    private void updateTotal() {
        try {
            // Retrieve quantity and price per unit
            float quantity = Float.parseFloat(binding.editCommande.getText().toString());
            float pricePerUnit = Float.parseFloat(binding.editPrix.getText().toString());
            // Calculate total
            float total = quantity * pricePerUnit;
            // Update total field
            binding.editTotal.setText(String.valueOf(total));
            // Update remaining value
            updateReste();
        } catch (NumberFormatException e) {
            binding.editTotal.setText("0");
            updateReste();
        }
    }

    private void updateReste() {
        try {
            // Retrieve total and advance
            float total = Float.parseFloat(binding.editTotal.getText().toString());
            float advance = Float.parseFloat(binding.editAvance.getText().toString());
            // Calculate remaining
            float reste = total - advance;
            // Update remaining field
            binding.editReste.setText(String.valueOf(reste));
        } catch (NumberFormatException e) {
            binding.editReste.setText("0");
        }
    }
}
