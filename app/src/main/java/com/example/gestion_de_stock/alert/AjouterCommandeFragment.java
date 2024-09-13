package com.example.gestion_de_stock.alert;

import static com.example.gestion_de_stock.AjouterModeleActivity.bindingAjout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gestion_de_stock.AjouterModeleActivity;
import com.example.gestion_de_stock.R;
import com.example.gestion_de_stock.adapter.ClientCommandeTabAdapter;
import com.example.gestion_de_stock.database.interne.entity.Commande;
import com.example.gestion_de_stock.database.interne.entity.LigneCommande;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelLigneCommande;
import com.example.gestion_de_stock.database.shared.PreferencesManager;
import com.example.gestion_de_stock.databinding.FragmentAjouterCommandeBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AjouterCommandeFragment extends DialogFragment implements ClientCommandeTabAdapter.OnQuantityChangeListener {

    private static final String ARG_COMMANDE_ID = "commande_id";
    private FragmentAjouterCommandeBinding binding;
    private ClientCommandeTabAdapter adapter;
    private List<LigneCommande> ligneCommandes = new ArrayList<>();
    private OnDataPass dataPasser;
    private Integer commandeId;
    private ViewModelLigneCommande viewModel;
    public static Boolean saveAndClose =false;

    public AjouterCommandeFragment() {
        // Required empty public constructor
    }

    // Parameterized constructor
    public AjouterCommandeFragment(Integer idCommande) {
        this.commandeId = idCommande != null ? idCommande : 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            commandeId = getArguments().getInt(ARG_COMMANDE_ID, 0); // Default to 0 if not found
        }
        viewModel = new ViewModelProvider(this).get(ViewModelLigneCommande.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAjouterCommandeBinding.inflate(inflater, container, false);

        PreferencesManager.init(getContext());
        adapter = new ClientCommandeTabAdapter(ligneCommandes,this);
        binding.recyclerClient.setAdapter(adapter);
        binding.recyclerClient.setLayoutManager(new LinearLayoutManager(getContext()));


        binding.editTotal.setFocusable(false);


        if(saveAndClose) {
            List<LigneCommande> savedLigneCommandes = PreferencesManager.getItemCommandes();

            // Check if there are any saved LigneCommandes
            if (savedLigneCommandes != null && !savedLigneCommandes.isEmpty()) {

                ligneCommandes.clear();
                ligneCommandes.addAll(savedLigneCommandes);
                onQuantityChanged();
            } else {
                // Handle case where no data is available in SharedPreferences
                // You can show a message or handle it as needed
                Toast.makeText(getContext(), getResources().getText(R.string.no_commande), Toast.LENGTH_SHORT).show();
            }
        } else {
            if (commandeId != null && commandeId != 0) {

                viewModel.findAllLigneCommandeByCommandeId(commandeId).observe(getViewLifecycleOwner(), new Observer<List<LigneCommande>>() {
                    @Override
                    public void onChanged(List<LigneCommande> ligneCommandesFromDb) {
                        Log.d("fedet",ligneCommandesFromDb.toString());

                        if (ligneCommandesFromDb != null) {
                            ligneCommandes.clear();
                            ligneCommandes.addAll(ligneCommandesFromDb);
                        }
                        removeDuplicatesByColor(ligneCommandes);

                        adapter.notifyDataSetChanged();
                        onQuantityChanged();
                    }
                });
            } else {
                // Initialize with default values if commandeId is 0
                ligneCommandes.add(new LigneCommande("jaune", 0, commandeId));
                ligneCommandes.add(new LigneCommande("bleu", 0, commandeId));
                ligneCommandes.add(new LigneCommande("rouge", 0, commandeId));
            }
        }





        binding.btnAddRow.setOnClickListener(v -> addNewItem());

        binding.btnAnnuler.setOnClickListener(v -> {
            // Réinitialiser la liste des commandes
            dismiss();
        });

        binding.btnAjouter.setOnClickListener(v -> saveAndClose());

        return binding.getRoot();
    }

    private void addNewItem() {
        ligneCommandes.add(new LigneCommande("new color", 0, commandeId));
        adapter.notifyItemInserted(ligneCommandes.size() - 1);
        binding.recyclerClient.scrollToPosition(ligneCommandes.size() - 1);
    }

    private void saveAndClose() {
        // Check for duplicates based on color
        Set<String> colorSet = new HashSet<>();
        boolean hasDuplicateColors = false;

        for (LigneCommande item : ligneCommandes) {
            if (!colorSet.add(item.getColor())) {
                hasDuplicateColors = true;
                break;
            }
        }

        // If there are duplicate colors, show a Toast and do not close
        if (hasDuplicateColors) {
            Toast.makeText(getContext(), getResources().getText(R.string.meme_commande), Toast.LENGTH_LONG).show();
            return; // Exit the method to prevent closing
        }

        // Save the LigneCommandes if there are no duplicates
        PreferencesManager.saveItemCommandes(ligneCommandes);
        float total = 0;
        for (LigneCommande item : ligneCommandes) {
            total += item.getQte();
        }

        // Pass the data back
        if (dataPasser != null) {
            dataPasser.onDataPass(total);
            dataPasser.onSaveLigneCommande(ligneCommandes);
        }

        // Close the dialog and set the save flag to true
        dismiss();
        saveAndClose = true;
    }

    private void clearData() {
        PreferencesManager.clearData();
    }

    public interface OnDataPass {
        void onDataPass(float data);
        void onSaveLigneCommande(List<LigneCommande> ligneCommandes);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dataPasser = (OnDataPass) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDataPass");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onQuantityChanged() {
        int total = 0;
        for (LigneCommande item : ligneCommandes) {
            total += item.getQte();
        }
        if (binding != null) {
            binding.editTotal.setText(String.valueOf(total));
        }
    }
    @Override
    public void supprimer(LigneCommande item) {
        ligneCommandes.remove(item);
        //viewModel.deleteLigneCommandeById(item.getIdLigneCommande());
        adapter.notifyDataSetChanged();
        onQuantityChanged();

    }
    public void removeDuplicatesByColor(List<LigneCommande> ligneCommandes) {
        // Use a Map to store unique LigneCommandes based on color
        Map<String, LigneCommande> uniqueLigneCommandes = new HashMap<>();

        // Loop through the list and add unique colors to the map
        for (LigneCommande item : ligneCommandes) {
            String color = item.getColor();
            if (!uniqueLigneCommandes.containsKey(color)) {
                uniqueLigneCommandes.put(color, item);
            }
        }

        // Clear the original list and add only the unique items back
        ligneCommandes.clear();
        ligneCommandes.addAll(uniqueLigneCommandes.values());
        adapter.notifyDataSetChanged();
    }

}
