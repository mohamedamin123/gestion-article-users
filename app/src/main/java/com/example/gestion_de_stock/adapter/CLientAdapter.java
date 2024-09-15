package com.example.gestion_de_stock.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_de_stock.R;
import com.example.gestion_de_stock.database.interne.entity.Client;
import com.example.gestion_de_stock.database.interne.entity.Commande;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelClient;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelCommande;
import com.example.gestion_de_stock.databinding.CustomItemClientBinding;

import java.util.ArrayList;
import java.util.List;

public class CLientAdapter extends RecyclerView.Adapter<CLientAdapter.MyViewHolder> {

    private Context context;
    private List<Client> clients;
    private onManipuleCLient listener;
    private ViewModelCommande viewModelCommande;

    public CLientAdapter(Context context, List<Client> clients, onManipuleCLient listener) {
        this.context = context;
        this.clients = clients;
        this.listener = listener;
        if (context instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) context;
            viewModelCommande = new ViewModelProvider(activity).get(ViewModelCommande.class);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.custom_item_client, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Client client = clients.get(position);

        // Ensure context is an instance of LifecycleOwner (usually AppCompatActivity or Fragment)
        if (context instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) context;

            // Observe Commande data for the client
            viewModelCommande.findCommandeByIdCLient(client.getIdClient()).observe(activity, new Observer<List<Commande>>() {
                @Override
                public void onChanged(List<Commande> commandes) {
                    boolean hasOutstandingCommande = false;
                    boolean terminer=true;

                    for (Commande commande : commandes) {

                        if(!commande.getStatut()){
                            terminer=false;
                            break;
                        }
                    }
                    updateClientStatusColor2(holder,terminer);

                    for (Commande commande : commandes) {
                        if (commande.getReste() != 0) {
                            client.setStatut(false);
                            hasOutstandingCommande = true;
                            break;
                        }
                    }
                    // Update client status based on commandes
                    if (!hasOutstandingCommande) {
                        client.setStatut(true);
                    }
                    // Update the UI accordingly
                    updateClientStatusColor(holder, client.getStatut());
                }
            });
        }

        // Bind client data
        holder.binding.tvName.setText(client.getFullName());
        holder.binding.tvTel.setText(client.getTelephone());

        // Handle click events
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUserClick(client);
            }
        });

        holder.binding.imgPhone.setOnClickListener(v -> {
            String phoneNumber = client.getTelephone();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));

            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                return;
            }

            context.startActivity(callIntent);
        });



        holder.binding.btnEditer.setOnClickListener(v -> {
            if (listener != null) {
                listener.onChangeCLient(client.getIdClient());
            }
        });

        holder.binding.btnSupprimer.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteCLient(client.getIdClient(), position);
            }
        });

        holder.binding.imgPhone.setOnClickListener(v -> listener.onAppele(client));

        updateClientStatusColor(holder, client.getStatut());
    }

    private void updateClientStatusColor(MyViewHolder holder, boolean status) {
        if (status) {
            holder.binding.tvName.setTextColor(holder.itemView.getResources().getColor(R.color.accent_green));
            holder.binding.tvTel.setTextColor(holder.itemView.getResources().getColor(R.color.accent_green));

        } else {
            holder.binding.tvName.setTextColor(holder.itemView.getResources().getColor(R.color.accent_red));
            holder.binding.tvTel.setTextColor(holder.itemView.getResources().getColor(R.color.accent_red));

        }

    }
    private void updateClientStatusColor2(MyViewHolder holder, boolean status) {
        int color;
        if (status) {
            color = holder.itemView.getResources().getColor(R.color.green);
        } else {
            color = holder.itemView.getResources().getColor(R.color.red);
        }

        // Apply the color to the button
        holder.binding.circularButton.setBackgroundColor(color);

    }



    @Override
    public int getItemCount() {
        return clients.size();
    }

    public void updateList(List<Client> newClients) {
        this.clients = new ArrayList<>(newClients);
        notifyDataSetChanged();
    }

    public interface onManipuleCLient {
        void onDeleteCLient(Integer id, int position);
        void onChangeCLient(Integer id);
        void onUserClick(Client client);
        void onAppele(Client client);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomItemClientBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomItemClientBinding.bind(itemView);
        }
    }
}
