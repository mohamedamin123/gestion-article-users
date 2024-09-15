package com.example.gestion_de_stock.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_de_stock.R;
import com.example.gestion_de_stock.database.interne.entity.Commande;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelClient;
import com.example.gestion_de_stock.databinding.CustomTableauClientBinding;

import java.util.List;

public class GenericAdapter extends RecyclerView.Adapter<GenericAdapter.GenericViewHolder> {

    private final List<Commande> items;
    private final OnItemClickListener onItemClickListener;
    private ViewModelClient modelClient;


    public GenericAdapter(List<Commande> items, OnItemClickListener onItemClickListener) {
        this.items = items;
        this.onItemClickListener = onItemClickListener;

    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomTableauClientBinding binding = CustomTableauClientBinding.inflate(inflater, parent, false);
        return new GenericViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        Commande item = items.get(position);
        Log.d("GenericAdapter", "Binding item: " + item.toString());

        holder.binding.column1Data.setText(item.getModele() != null ? item.getModele() : "");
        holder.binding.column2Data.setText(item.getPrix() != 0 ? String.valueOf(item.getPrix()) : "");
        holder.binding.column3Data.setText(item.getQte() != 0 ? String.valueOf(item.getQte()) : "");
        holder.binding.column4Data.setText(item.getTotal() != 0 ? String.valueOf(item.getTotal()) : "");
        holder.binding.column5Data.setText(item.getAvance() != 0 ? String.valueOf(item.getAvance()) : "");
        holder.binding.column6Data.setText(item.getReste() != 0 ? String.valueOf(item.getReste()) : "0");
        if (position % 2 == 0) {
            // For even rows, set one color
            holder.itemView.setBackgroundColor(Color.parseColor("#f5f5f5"));
        } else {
            // For odd rows, set another color
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        if(item.getStatut()){
            holder.binding.terminer.setTextColor(holder.itemView.getResources().getColor(R.color.accent_green));
            holder.binding.terminer.setText("Oui");
        }else {
            holder.binding.terminer.setTextColor(holder.itemView.getResources().getColor(R.color.accent_red));
            holder.binding.terminer.setText("Non");
        }


        if(item.getReste() == 0) {
            holder.binding.column1Data.setTextColor(holder.itemView.getResources().getColor(R.color.accent_green));
            holder.binding.column2Data.setTextColor(holder.itemView.getResources().getColor(R.color.accent_green));
            holder.binding.column3Data.setTextColor(holder.itemView.getResources().getColor(R.color.accent_green));
            holder.binding.column4Data.setTextColor(holder.itemView.getResources().getColor(R.color.accent_green));
            holder.binding.column5Data.setTextColor(holder.itemView.getResources().getColor(R.color.accent_green));
            holder.binding.column6Data.setTextColor(holder.itemView.getResources().getColor(R.color.accent_green));

        } else {
            holder.binding.column1Data.setTextColor(holder.itemView.getResources().getColor(R.color.accent_red));
            holder.binding.column2Data.setTextColor(holder.itemView.getResources().getColor(R.color.accent_red));
            holder.binding.column3Data.setTextColor(holder.itemView.getResources().getColor(R.color.accent_red));
            holder.binding.column4Data.setTextColor(holder.itemView.getResources().getColor(R.color.accent_red));
            holder.binding.column5Data.setTextColor(holder.itemView.getResources().getColor(R.color.accent_red));
            holder.binding.column6Data.setTextColor(holder.itemView.getResources().getColor(R.color.accent_red));
        }



        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class GenericViewHolder extends RecyclerView.ViewHolder {
        final CustomTableauClientBinding binding;

        public GenericViewHolder(CustomTableauClientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Object item);
    }
}
