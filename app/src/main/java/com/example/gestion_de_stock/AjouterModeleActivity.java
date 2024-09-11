package com.example.gestion_de_stock;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.gestion_de_stock.alert.AjouterCommandeFragment;
import com.example.gestion_de_stock.database.interne.entity.Commande;
import com.example.gestion_de_stock.database.interne.entity.LigneCommande;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelCommande;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelClient;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelLigneCommande;
import com.example.gestion_de_stock.databinding.ActivityAjouterModeleBinding;

import java.util.ArrayList;
import java.util.List;

public class AjouterModeleActivity extends AppCompatActivity implements AjouterCommandeFragment.OnDataPass {

    public static ActivityAjouterModeleBinding bindingAjout;
    private ViewModelCommande viewModelCommande;
    private ViewModelLigneCommande viewModelLigneCommande;
    private ViewModelClient viewModelClient;
    private int clientId;
    private Commande commandeData;
    private List<LigneCommande> ligneCommandes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        bindingAjout = ActivityAjouterModeleBinding.inflate(getLayoutInflater());
        setContentView(bindingAjout.getRoot());

        // Initialize ViewModels
        viewModelCommande = new ViewModelProvider(this).get(ViewModelCommande.class);
        viewModelClient = new ViewModelProvider(this).get(ViewModelClient.class);
        viewModelLigneCommande = new ViewModelProvider(this).get(ViewModelLigneCommande.class);

        // Retrieve clientId and Commande data from Intent
        Intent intent = getIntent();
        clientId = intent.getIntExtra("clientId", -1);
        commandeData = getIntent().getParcelableExtra("commande");

        // Populate fields if commandeData is not null
        if (commandeData != null) {
            bindingAjout.editModele.setText(commandeData.getModele());
            bindingAjout.editPrix.setText(String.valueOf(commandeData.getPrix()));
            bindingAjout.editCommande.setText(String.valueOf(commandeData.getQte()));
            bindingAjout.editAvance.setText(String.valueOf(commandeData.getAvance()));
            bindingAjout.editTotal.setText(String.valueOf(commandeData.getTotal()));
            bindingAjout.editReste.setText(String.valueOf(commandeData.getReste()));

            viewModelLigneCommande.findLigneCommandeByIdCommande(commandeData.getIdCommande()).observe(this, new Observer<List<LigneCommande>>() {
                @Override
                public void onChanged(List<LigneCommande> ls) {

                    ligneCommandes.clear();
                    for (LigneCommande l:ls) {
                        l.setIdLigneCommande(null);
                    }
                    ligneCommandes.addAll(ls);
                    AjouterModeleActivity.this.ligneCommandes = ls;
                    updateTotal();
                }
            });

        }

        // Setup click listener for EditText to open dialog
        bindingAjout.editCommande.setFocusable(false);
        bindingAjout.editCommande.setOnClickListener(v -> {
            AjouterCommandeFragment dialog;
            if (commandeData != null) {
                Log.d("idCommande", "" + commandeData.getIdCommande());
                dialog = new AjouterCommandeFragment(commandeData.getIdCommande());
            } else {
                dialog = new AjouterCommandeFragment();
            }
            dialog.show(getSupportFragmentManager(), "AjouterCommandeFragment");
        });

        // Setup click listener for "Retour" button
        bindingAjout.btnRetour.setOnClickListener(v -> {
            Intent backIntent = new Intent(AjouterModeleActivity.this, ViewClientActivity.class);
            backIntent.putExtra("clientId", clientId);
            startActivity(backIntent);
        });

        // Setup click listener for "Enregistrer" button
        bindingAjout.btnSave.setOnClickListener(v -> saveData());

        // Setup TextWatchers for price, quantity, and advance fields
        bindingAjout.editPrix.addTextChangedListener(new PriceTextWatcher());
        bindingAjout.editCommande.addTextChangedListener(new QuantityTextWatcher());
        bindingAjout.editAvance.addTextChangedListener(new AvanceTextWatcher());


    }

    private void saveData() {
        // Validate clientId
        if (clientId == -1) {
            Toast.makeText(this, "Invalid client ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve data from fields
        String articleName = bindingAjout.editModele.getText().toString();
        if (articleName.isEmpty()) {
            Toast.makeText(this, "Please enter a model name", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float price = Float.parseFloat(bindingAjout.editPrix.getText().toString());
            float quantity = Float.parseFloat(bindingAjout.editCommande.getText().toString());
            float advance = Float.parseFloat(bindingAjout.editAvance.getText().toString());
            float reste = Float.parseFloat(bindingAjout.editReste.getText().toString());
            float total = Float.parseFloat(bindingAjout.editTotal.getText().toString());

            // Create a new Commande
            Commande commande = new Commande();
            commande.setModele(articleName);
            commande.setPrix(price);
            commande.setAvance(advance);
            commande.setQte(quantity);
            commande.setReste(reste);
            commande.setTotal(total);
            commande.setClientId(clientId);

            // Insert or update Commande
            if (commandeData == null) {
                viewModelCommande.insertCommande(commande).observe(this, new Observer<Long[]>() {
                    @Override
                    public void onChanged(Long[] insertedCommandeIds) {
                        if (insertedCommandeIds != null && insertedCommandeIds.length > 0) {
                            Long insertedCommandeId = insertedCommandeIds[0];
                            saveLigneCommande(insertedCommandeId.intValue());
                        } else {
                            Toast.makeText(AjouterModeleActivity.this, "Failed to insert Commande", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                commande.setIdCommande(commandeData.getIdCommande());
                viewModelCommande.updateCommande(commande);

                viewModelLigneCommande.deleteLigneCommandeByIdCommande(commandeData.getIdCommande());
                saveLigneCommande(commande.getIdCommande());
            }

            // Clear fields after saving
            //clearFields();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input. Please check your data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveLigneCommande(int id) {
        if (ligneCommandes.isEmpty()) {
            Toast.makeText(this, "No LigneCommandes to save", Toast.LENGTH_SHORT).show();
            return;
        }
        for (LigneCommande ligne : ligneCommandes) {
            ligne.setCommandeId(id);

        }

        viewModelLigneCommande.insertLigneCommandes(ligneCommandes, success -> {
            runOnUiThread(() -> {
                if (success) {
                    Toast.makeText(AjouterModeleActivity.this, "LigneCommandes saved successfully", Toast.LENGTH_SHORT).show();
                    Intent backIntent = new Intent(AjouterModeleActivity.this, ViewClientActivity.class);
                    backIntent.putExtra("clientId", clientId);
                    startActivity(backIntent);
                } else {
                    Toast.makeText(AjouterModeleActivity.this, "Failed to save LigneCommandes", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void clearFields() {
        bindingAjout.editModele.setText("");
        bindingAjout.editPrix.setText("");
        bindingAjout.editCommande.setText("");
        bindingAjout.editAvance.setText("");
        bindingAjout.editReste.setText("");
        bindingAjout.editTotal.setText("");
    }

    @Override
    public void onDataPass(float data) {
        bindingAjout.editCommande.setText(String.valueOf(data));
    }

    @Override
    public void onSaveLigneCommande(List<LigneCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
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
            float quantity = Float.parseFloat(bindingAjout.editCommande.getText().toString());
            float pricePerUnit = Float.parseFloat(bindingAjout.editPrix.getText().toString());
            float total = quantity * pricePerUnit;
            bindingAjout.editTotal.setText(String.valueOf(total));
            updateReste();
        } catch (NumberFormatException e) {
            bindingAjout.editTotal.setText("");
        }
    }

    private void updateReste() {
        try {
            float total = Float.parseFloat(bindingAjout.editTotal.getText().toString());
            float advance = Float.parseFloat(bindingAjout.editAvance.getText().toString());
            float reste = total - advance;
            bindingAjout.editReste.setText(String.valueOf(reste));
        } catch (NumberFormatException e) {
            bindingAjout.editReste.setText("");
        }
    }
}
