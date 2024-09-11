package com.example.gestion_de_stock.alert;

import static com.example.gestion_de_stock.AjouterModeleActivity.bindingAjout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gestion_de_stock.adapter.ClientCommandeTabAdapter;
import com.example.gestion_de_stock.database.interne.entity.Commande;
import com.example.gestion_de_stock.database.interne.entity.LigneCommande;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelLigneCommande;
import com.example.gestion_de_stock.database.shared.PreferencesManager;
import com.example.gestion_de_stock.databinding.FragmentAjouterCommandeBinding;

import java.util.ArrayList;
import java.util.List;

public class AjouterCommandeFragment extends DialogFragment {

    private static final String ARG_COMMANDE_ID = "commande_id";
    private FragmentAjouterCommandeBinding binding;
    private ClientCommandeTabAdapter adapter;
    private List<LigneCommande> ligneCommandes = new ArrayList<>();
    private OnDataPass dataPasser;
    private Integer commandeId;
    private ViewModelLigneCommande viewModel;

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

        if (commandeId != null && commandeId != 0) {

            viewModel.findAllLigneCommandeByCommandeId(commandeId).observe(getViewLifecycleOwner(), new Observer<List<LigneCommande>>() {
                @Override
                public void onChanged(List<LigneCommande> ligneCommandesFromDb) {
                    if (ligneCommandesFromDb != null) {
                        ligneCommandes.clear();
                        for (LigneCommande lc : ligneCommandesFromDb) {
                            lc.setIdLigneCommande(null);

                        }
                        ligneCommandes.addAll(ligneCommandesFromDb);
                    } else {
                        // Handle the case where no data is returned
                        ligneCommandes.clear();
                        ligneCommandes.add(new LigneCommande("jaune", 0, commandeId));
                        ligneCommandes.add(new LigneCommande("bleu", 0, commandeId));
                        ligneCommandes.add(new LigneCommande("rouge", 0, commandeId));
                    }
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

        adapter = new ClientCommandeTabAdapter(ligneCommandes, this::onQuantityChanged);
        binding.recyclerClient.setAdapter(adapter);
        binding.recyclerClient.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.btnAddRow.setOnClickListener(v -> addNewItem());

        binding.btnAnnuler.setOnClickListener(v -> {
            // Réinitialiser la liste des commandes
            adapter.notifyDataSetChanged(); // Informer l'adaptateur de la mise à jour
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
        PreferencesManager.saveItemCommandes(getContext(), ligneCommandes);
        float total = 0;
        for (LigneCommande item : ligneCommandes) {
            total += item.getQte();
        }

        if (dataPasser != null) {
            dataPasser.onDataPass(total);
            dataPasser.onSaveLigneCommande(ligneCommandes);
        }
        dismiss();
    }

    private void clearData() {
        PreferencesManager.clearData(getContext());
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

    private void onQuantityChanged() {
        int total = 0;
        for (LigneCommande item : ligneCommandes) {
            total += item.getQte();
        }
        if (binding != null) {
            binding.editTotal.setText(String.valueOf(total));
        }
    }
}
