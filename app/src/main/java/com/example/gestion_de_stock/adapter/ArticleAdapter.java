package com.example.gestion_de_stock.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_de_stock.R;
import com.example.gestion_de_stock.database.interne.entity.Article;
import com.example.gestion_de_stock.database.interne.entity.Commande;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelClient;
import com.example.gestion_de_stock.databinding.CustomTableauArticleBinding;
import com.example.gestion_de_stock.databinding.CustomTableauClientBinding;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.GenericViewHolder> {

    private final List<Article> items;
    private final OnItemClickListener onItemClickListener;
    private ViewModelClient modelClient;


    public ArticleAdapter(List<Article> items, OnItemClickListener onItemClickListener) {
        this.items = items;
        this.onItemClickListener = onItemClickListener;

    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomTableauArticleBinding binding = CustomTableauArticleBinding.inflate(inflater, parent, false);
        return new GenericViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        Article item = items.get(position);
        Log.d("GenericAdapter", "Binding item: " + item.toString());
        float MasseTotal=item.getMasse()*item.getQte();
        float prix1PS=item.getMasse()*item.getPrixM();
        float prix1PS2 = prix1PS * 1.20f; // 20% more than prix1PS
        float cout=item.getCoutMand()+prix1PS;
        float cout2=cout*1.20f;

        holder.binding.column0Data.setText(item.getNom());
        holder.binding.column1Data.setText(String.valueOf(MasseTotal));
        holder.binding.column2Data.setText(item.getMasse() != 0 ? String.valueOf(item.getMasse()) : "");
        holder.binding.column3Data.setText(item.getPrixM() != 0 ? String.valueOf(item.getPrixM()) : "");
        holder.binding.column4Data.setText(prix1PS != 0 ? String.valueOf(prix1PS) : "");
        holder.binding.column5Data.setText(prix1PS2 != 0 ? String.valueOf(prix1PS2) : "");
        holder.binding.column6Data.setText(item.getQte() != 0 ? String.valueOf(item.getQte()) : "0");
        holder.binding.column7Data.setText(item.getCoutMand() != 0 ? String.valueOf(item.getCoutMand()) : "0");
        holder.binding.column8Data.setText(cout!= 0 ? String.valueOf(cout) : "0");
        holder.binding.column9Data.setText(cout2 != 0 ? String.valueOf(cout2) : "0");
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class GenericViewHolder extends RecyclerView.ViewHolder {
        final CustomTableauArticleBinding binding;

        public GenericViewHolder(CustomTableauArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Object item);
    }
}
