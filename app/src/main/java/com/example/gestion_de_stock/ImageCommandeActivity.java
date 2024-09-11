package com.example.gestion_de_stock;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.gestion_de_stock.adapter.PhotoCommandeAdapter;
import com.example.gestion_de_stock.database.interne.entity.Photo;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelPhoto;
import com.example.gestion_de_stock.databinding.ActivityImageCommandeBinding;
import com.example.gestion_de_stock.zoom.ZoomImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageCommandeActivity extends AppCompatActivity implements PhotoCommandeAdapter.OnManipulePhoto {

    private ActivityImageCommandeBinding binding;
    private List<Photo> photos;
    private PhotoCommandeAdapter adapter;
    private ViewModelPhoto viewModelPhoto;
    private Integer idCommande, idClient;
    ActivityResultLauncher<String> arl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageCommandeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Récupérer les paramètres de l'intent
        Intent intent = getIntent();
        idCommande = intent.getIntExtra("idCommande", 0);
        idClient = intent.getIntExtra("clientId", 0);

        photos = new ArrayList<>();
        adapter = new PhotoCommandeAdapter(photos, this,this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3); // 3 colonnes
        binding.recycleImageCommande.setLayoutManager(layoutManager);
        binding.recycleImageCommande.setAdapter(adapter);

        // Initialisation du ViewModel
        viewModelPhoto = new ViewModelProvider(this).get(ViewModelPhoto.class);

        // Charger les photos de la commande
        getData();

        // Lancer l'ActivityResultLauncher pour sélectionner plusieurs images
        arl = registerForActivityResult(
                new ActivityResultContracts.GetMultipleContents(),
                new ActivityResultCallback<List<Uri>>() {
                    @Override
                    public void onActivityResult(List<Uri> results) {
                        for (Uri uri : results) {
                            Glide.with(ImageCommandeActivity.this)
                                    .asBitmap()
                                    .load(uri)
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                            resource.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                                            byte[] byteArray = stream.toByteArray();

                                            // Créer un nouvel objet Photo avec l'image
                                            Photo photo = new Photo(byteArray);
                                            photo.setIdCommande(idCommande);

                                            // Insérer la photo dans la base de données
                                            viewModelPhoto.insertPhoto(photo);
                                            photos.add(photo);
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                        }
                    }
                });

        // Bouton pour ajouter plusieurs photos
        binding.ajouterPhoto.setOnClickListener(view -> {
            // Vérification si le clic est bien enregistré
            Toast.makeText(this, "Ajouter photo cliqué", Toast.LENGTH_SHORT).show();

            // Demande directe de permission (on force la demande)
            requestStoragePermission();
        });

        // Bouton pour annuler et revenir en arrière
        binding.buttonAnnuler.setOnClickListener(view -> {
            Intent intent1 = new Intent(ImageCommandeActivity.this, ViewClientActivity.class);
            intent1.putExtra("clientId", idClient);
            startActivity(intent1);
        });
    }

    // Méthode pour demander la permission sans vérifier l'état actuel
    private void requestStoragePermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.READ_EXTERNAL_STORAGE},3);
        }
        arl.launch("image/**");

    }

    // Méthode pour récupérer les photos liées à la commande depuis la base de données
    private void getData() {
        viewModelPhoto.findLigneCommandeByIdCommande(idCommande).observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> newPhotos) {
                photos.clear();
                photos.addAll(newPhotos);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void manipule(Photo photo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Supprimer photo" );
        builder.setMessage("Voulez vous supprimer cette image?");
         builder.setPositiveButton("Oui", (confirmDialog, confirmWhich) -> {

             viewModelPhoto.deletePhotoByID(photo.getId());
             adapter.notifyDataSetChanged();
        })
                 .setNegativeButton("Non", (confirmDialog, confirmWhich) -> {
                    // Logic for cancelling deletion
                    confirmDialog.dismiss();
                })
                .show();
    }

    @Override
    public void manipuleDouble(Photo photo) {
        // Find the ZoomImageView in your layout
        Toast.makeText(this, "double click", Toast.LENGTH_SHORT).show();
    }


    // Gérer la réponse de la permission


}
