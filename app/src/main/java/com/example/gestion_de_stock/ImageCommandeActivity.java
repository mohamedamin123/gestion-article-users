package com.example.gestion_de_stock;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.gestion_de_stock.alert.AjouterCommandeFragment;
import com.example.gestion_de_stock.alert.VisualitationImageFragment;
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
    private Integer idCommande, idClient,idArticle;
    ActivityResultLauncher<String> arl;
    private VisualitationImageFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageCommandeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Récupérer les paramètres de l'intent
        Intent intent = getIntent();
        idCommande = intent.getIntExtra("idCommande", 0);
        idClient = intent.getIntExtra("clientId", 0);
        idArticle = intent.getIntExtra("idArticle", 0);

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
                                            if(idCommande!=null && idCommande!=0) {
                                                photo.setIdCommande(idCommande);
                                            }else if(idArticle!=null && idArticle!=0) {
                                                photo.setIdArticle(idArticle);
                                            }

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
            // Demande directe de permission (on force la demande)
            requestStoragePermission();
        });

        // Bouton pour annuler et revenir en arrière
        binding.buttonAnnuler.setOnClickListener(view -> {
            Intent intent1;
            if(idCommande!=null && idCommande!=0) {
                 intent1 = new Intent(ImageCommandeActivity.this, ViewClientActivity.class);
                intent1.putExtra("clientId", idClient);

            }else if(idArticle!=null && idArticle!=0){
                 intent1 = new Intent(ImageCommandeActivity.this, ArticleActivity.class);
            } else {
                Toast.makeText(this,"Il y'a une probleme", Toast.LENGTH_SHORT).show();
                intent1 = new Intent(ImageCommandeActivity.this, MainActivity.class);
            }
            startActivity(intent1);
            finish();

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
        if(idCommande!=null && idCommande!=0) {
            viewModelPhoto.findAllPhotosByCommande(idCommande).observe(this, new Observer<List<Photo>>() {
                @Override
                public void onChanged(List<Photo> newPhotos) {
                    photos.clear();
                    photos.addAll(newPhotos);
                    adapter.notifyDataSetChanged();
                }
            });
        } else if(idArticle!=null && idArticle!=0) {
            viewModelPhoto.findAllPhotosByArticle(idArticle).observe(this, new Observer<List<Photo>>() {
                @Override
                public void onChanged(List<Photo> newPhotos) {
                    photos.clear();
                    photos.addAll(newPhotos);
                    adapter.notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    public void manipule(Photo photo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options de la photo");
        builder.setMessage("Que voulez-vous faire avec cette image?");

        // Set up the "Supprimer" button
        builder.setPositiveButton("Supprimer", (dialog, which) -> {
            AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
            confirmDialog.setTitle("Supprimer photo");
            confirmDialog.setMessage("Voulez-vous supprimer cette image?");

            confirmDialog.setPositiveButton("Oui", (confirmDialog1, confirmWhich) -> {
                        viewModelPhoto.deletePhotoByID(photo.getId());
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Non", (confirmDialog1, confirmWhich) -> {
                        confirmDialog1.dismiss();
                    }).show();
        });

        // Set up the "Afficher" button
        builder.setNegativeButton("Afficher", (dialog, which) -> {
            Toast.makeText(this, "Image affichée", Toast.LENGTH_SHORT).show();
            fragment =  VisualitationImageFragment.newInstance(photo.getId());
            fragment.show(getSupportFragmentManager(), "VisualitationImageFragment");
        });

        // Show the dialog
        builder.show();
    }


    @Override
    public void manipuleDouble(Photo photo) {
        // Find the ZoomImageView in your layout
    }


    // Gérer la réponse de la permission


}
