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

public class ClientCommandeTabAdapter extends RecyclerView.Adapter<ClientCommandeTabAdapter.ViewHolder> {

    private List<ItemCommande> items;
    private OnQuantityChangeListener onQuantityChangeListener;

    public ClientCommandeTabAdapter(List<ItemCommande> items, OnQuantityChangeListener listener) {
        this.items = items;
        this.onQuantityChangeListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomCommandeClientBinding binding = CustomCommandeClientBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemCommande item = items.get(position);
        holder.binding.column1Data.setText(item.getColor());
        holder.binding.column2Data.setText(String.valueOf(item.getQte()));

        // Add TextWatcher to update quantity
        holder.binding.column2Data.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    item.setQte(Integer.parseInt(s.toString()));
                } catch (NumberFormatException e) {
                    item.setQte(0);
                }
                if (onQuantityChangeListener != null) {
                    onQuantityChangeListener.onQuantityChanged();
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

    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CustomCommandeClientBinding binding;

        public ViewHolder(CustomCommandeClientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
