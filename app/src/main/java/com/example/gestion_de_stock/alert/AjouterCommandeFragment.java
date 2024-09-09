package com.example.gestion_de_stock.alert;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

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
    private OnDataPass dataPasser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAjouterCommandeBinding.inflate(inflater, container, false);

        if (savedInstanceState != null) {
            itemCommandes = savedInstanceState.getParcelableArrayList("itemCommandes");
            if (itemCommandes == null) {
                itemCommandes = new ArrayList<>();
            }
        } else {
            itemCommandes = new ArrayList<>();
            itemCommandes.add(new ItemCommande("jaune", 0));
            itemCommandes.add(new ItemCommande("bleu", 0));
            itemCommandes.add(new ItemCommande("rouge", 0));
        }

        adapter = new ClientCommandeTabAdapter(itemCommandes, this::onQuantityChanged);
        binding.recyclerClient.setAdapter(adapter);
        binding.recyclerClient.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.btnAddRow.setOnClickListener(v -> addNewItem());
        binding.btnAnnuler.setOnClickListener(v -> dismiss());
        binding.btnAjouter.setOnClickListener(v -> saveAndClose());

        return binding.getRoot();
    }

    private void addNewItem() {
        itemCommandes.add(new ItemCommande("new color", 0));
        adapter.notifyItemInserted(itemCommandes.size() - 1);
        binding.recyclerClient.scrollToPosition(itemCommandes.size() - 1);
    }

    private void saveAndClose() {
        float total = 0;
        for (ItemCommande item : itemCommandes) {
            total += item.getQte();
        }

        if (dataPasser != null) {
            dataPasser.onDataPass(total);
        }
        dismiss();
    }

    public interface OnDataPass {
        void onDataPass(float data);
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        List<ItemCommande> itemCommandesArrayList = new ArrayList<>(itemCommandes);
        outState.putParcelableArrayList("itemCommandes", (ArrayList<? extends Parcelable>) itemCommandesArrayList);
    }

    private void onQuantityChanged() {
        int total = 0;
        for (ItemCommande item : itemCommandes) {
            total += item.getQte();
        }
        binding.editTotal.setText(String.valueOf(total));
    }
}
