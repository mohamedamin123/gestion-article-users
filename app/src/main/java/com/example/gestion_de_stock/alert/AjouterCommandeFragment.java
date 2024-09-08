package com.example.gestion_de_stock.alert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_de_stock.R;
import com.example.gestion_de_stock.adapter.ClientCommandeTabAdapter;
import com.example.gestion_de_stock.databinding.FragmentAjouterCommandeBinding;
import com.example.gestion_de_stock.util.ItemCommande;

import java.util.ArrayList;
import java.util.List;

public class AjouterCommandeFragment extends DialogFragment {

    private FragmentAjouterCommandeBinding binding;
    private ClientCommandeTabAdapter adapter;
    private List<ItemCommande> itemCommandes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAjouterCommandeBinding.inflate(inflater, container, false);

        // Initialize RecyclerView
        RecyclerView recyclerView = binding.recyclerClient;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Prepare data
        itemCommandes = new ArrayList<>();
        // Add some sample data
        itemCommandes.add(new ItemCommande("jaune", 0));
        itemCommandes.add(new ItemCommande("bleu", 0));
        itemCommandes.add(new ItemCommande("rouge", 0));

        // Set adapter
        adapter = new ClientCommandeTabAdapter(itemCommandes);
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }
}
