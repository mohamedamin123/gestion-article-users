package com.example.gestion_de_stock.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_de_stock.databinding.CustomCommandeClientBinding;
import com.example.gestion_de_stock.util.ItemCommande;

import java.util.List;

public class ClientCommandeTabAdapter extends RecyclerView.Adapter<ClientCommandeTabAdapter.ClientViewHolder> {
    private final List<ItemCommande> items;

    public ClientCommandeTabAdapter(List<ItemCommande> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomCommandeClientBinding binding = CustomCommandeClientBinding.inflate(inflater, parent, false);
        return new ClientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        ItemCommande item = items.get(position);
        holder.binding.column1Data.setText(item.getColor());
        holder.binding.column2Data.setText(String.valueOf(item.getQte()));

        holder.binding.column2Data.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    item.setQte(Integer.parseInt(s.toString()));
                } catch (NumberFormatException e) {
                    item.setQte(0); // Handle empty input
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ClientViewHolder extends RecyclerView.ViewHolder {
        final CustomCommandeClientBinding binding;

        public ClientViewHolder(CustomCommandeClientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
