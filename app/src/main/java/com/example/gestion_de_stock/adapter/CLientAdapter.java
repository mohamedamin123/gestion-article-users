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
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_de_stock.R;
import com.example.gestion_de_stock.database.interne.entity.Client;
import com.example.gestion_de_stock.databinding.CustomItemClientBinding;

import java.util.ArrayList;
import java.util.List;

public class CLientAdapter extends RecyclerView.Adapter<CLientAdapter.MyViewHolder> {

    private Context context;
    private List<Client> clients;
    private onManipuleCLient listener;

    public CLientAdapter(Context context, List<Client> clients, onManipuleCLient listener) {
        this.context = context;
        this.clients = clients;
        this.listener = listener;
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

        // Bind data
        holder.binding.tvName.setText(client.getFullName());
        holder.binding.tvTel.setText(client.getTelephone());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUserClick(client); // Appeler le listener pour gérer le clic
            }
        });

        // Handle click on phone icon
        holder.binding.imgPhone.setOnClickListener(v -> {
            String phoneNumber = client.getTelephone();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));

            // Check for permission
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // Request permission
                ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                return;
            }

            context.startActivity(callIntent);
        });

        // Handle click on the edit button
        holder.binding.btnEditer.setOnClickListener(v -> {
            if (listener != null) {
                listener.onChangeCLient(client.getIdClient());
            }
        });

        // Handle click on the delete button
        holder.binding.btnSupprimer.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteCLient(client.getIdClient(), position);
            }
        });

        holder.binding.imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAppele(client);
            }
        });
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
