package com.example.gestion_de_stock.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_de_stock.database.interne.entity.Client;
import com.example.gestion_de_stock.databinding.CustomTableauClientBinding;

import java.util.List;
public class ClientTabAdapter extends RecyclerView.Adapter<ClientTabAdapter.ClientViewHolder> {
    private final List<Client> clients;
    private final OnClientClickListener onClientClickListener;

    // Interface pour gérer les clics sur les éléments

    public ClientTabAdapter(List<Client> clients, OnClientClickListener onClientClickListener) {
        this.clients = clients;
        this.onClientClickListener = onClientClickListener;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomTableauClientBinding binding = CustomTableauClientBinding.inflate(inflater, parent, false);
        return new ClientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Client client = clients.get(position);

        holder.binding.column1Data.setText(String.valueOf(client.getIdClient()));
        holder.binding.column2Data.setText(client.getNom());
        holder.binding.column3Data.setText(client.getPrenom());
        holder.binding.column4Data.setText(client.getEmail());
        holder.binding.column5Data.setText(client.getTelephone());
        holder.binding.column6Data.setText(client.getFullName());

        // Gestion du clic sur l'élément
        holder.itemView.setOnClickListener(v -> onClientClickListener.onClientClick(client));
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public static class ClientViewHolder extends RecyclerView.ViewHolder {
        final CustomTableauClientBinding binding;

        public ClientViewHolder(CustomTableauClientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnClientClickListener {
        void onClientClick(Client client);
    }

}
