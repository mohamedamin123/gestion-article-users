package com.example.gestion_de_stock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_de_stock.R;
import com.example.gestion_de_stock.database.interne.entity.User;
import com.example.gestion_de_stock.databinding.CustomItemClientBinding;

import java.util.ArrayList;
import java.util.List;

public class CLientAdapter extends RecyclerView.Adapter<CLientAdapter.MyViewHolder> {

    private Context context;
    private List<User> users;
    private onManipuleCLient listener;


    public CLientAdapter(Context context, List<User> evaluations, onManipuleCLient listener) {
        this.context = context;
        this.users = evaluations;
        this.listener=listener;
    }

    public CLientAdapter(Context context, List<User> evaluations) {
        this.context = context;
        this.users = evaluations;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v= inflater.inflate(R.layout.custom_item_client,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CLientAdapter.MyViewHolder holder, int position) {
        // Récupérer l'utilisateur actuel
        User user = users.get(position);

        // Lier les données de l'utilisateur avec les éléments de la vue
        holder.binding.tvName.setText(user.getFulName());
        holder.binding.tvTel.setText(user.getTelephone());

        // Gérer l'événement de clic sur l'élément entier
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUserClick(user); // Appeler le listener pour gérer le clic
            }
        });

        // Gérer l'événement de clic sur le bouton Supprimer
        holder.binding.btnSupprimer.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteCLient(user.getIdUser(), position); // Appeler le listener pour supprimer l'utilisateur
            }
        });

        // Gérer l'événement de clic sur le bouton Éditer
        holder.binding.btnEditer.setOnClickListener(v -> {
            if (listener != null) {
                listener.onChangeCLient(user.getIdUser()); // Appeler le listener pour éditer l'utilisateur
            }
        });
    }



    @Override
    public int getItemCount() {
        return users.size();
    }

    public void updateList(List<User> newUsers) {
        this.users = new ArrayList<>(newUsers);
        notifyDataSetChanged();
    }

    public interface onManipuleCLient {
        void onDeleteCLient(Integer id, int position);
        void onChangeCLient(Integer id);
        void onUserClick(User user); // Ajouter cette méthode
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder{

        CustomItemClientBinding binding;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=CustomItemClientBinding.bind(itemView);

        }
    }
}
