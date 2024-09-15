package com.example.gestion_de_stock;

import static com.example.gestion_de_stock.alert.AjouterCommandeFragment.saveAndClose;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.gestion_de_stock.database.interne.entity.Article;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelArticle;
import com.example.gestion_de_stock.database.shared.PreferencesManager;
import com.example.gestion_de_stock.databinding.ActivityAjouterArticleBinding;
import com.example.gestion_de_stock.databinding.ActivityArticleBinding;

public class AjouterArticleActivity extends AppCompatActivity {

    private ActivityAjouterArticleBinding binding;
    private Integer idArticle;
    private ViewModelArticle viewModelArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAjouterArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModelArticle = new ViewModelProvider(this).get(ViewModelArticle.class);

        Intent intent=getIntent();
        idArticle=intent.getIntExtra("idArticle", -1);
        if(idArticle!=-1){
            viewModelArticle.findArticleById(idArticle).observe(this, new Observer<Article>() {
                @Override
                public void onChanged(Article article) {
                    if(article!=null) {
                        binding.editModele.setText(article.getNom());
                        binding.editMasse.setText(String.valueOf(article.getMasse()));
                        binding.editPrixM.setText(String.valueOf(article.getPrixM()));
                        binding.editQte.setText(String.valueOf(article.getQte()));
                        binding.editMand.setText(String.valueOf(article.getCoutMand()));
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(AjouterArticleActivity.this, ArticleActivity.class);
                startActivity(backIntent);
                finish();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent backIntent = new Intent(AjouterArticleActivity.this, ArticleActivity.class);
                startActivity(backIntent);
                finish();
            }
        });


    }

    private void saveData() {
        // Retrieve input values
        String nom = binding.editModele.getText().toString().trim();
        String masseStr = binding.editMasse.getText().toString().trim();
        String prixMStr = binding.editPrixM.getText().toString().trim();
        String qteStr = binding.editQte.getText().toString().trim();
        String mandStr = binding.editMand.getText().toString().trim();

        // Validate inputs
        if (nom.isEmpty() || masseStr.isEmpty() || prixMStr.isEmpty() || qteStr.isEmpty() || mandStr.isEmpty()) {
            // Show a toast message if any field is empty
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert string inputs to appropriate types
        float masse = Float.parseFloat(masseStr);
        float prixM = Float.parseFloat(prixMStr);
        int qte = Integer.parseInt(qteStr);
        float mand = Float.parseFloat(mandStr);

        // Create or update the Article object
        Article article;
        if (idArticle != -1) {
            // Update existing article
            article = new Article(idArticle, nom, masse, prixM, qte, mand);
        } else {
            // Create new article
            article = new Article(nom, masse, prixM, qte, mand);
        }

        // Save the article using ViewModel
        if (idArticle != -1) {
            viewModelArticle.updateArticle(article);
        } else {
            viewModelArticle.insertArticle(article);
        }

        // Return to the previous activity
        Intent backIntent = new Intent(AjouterArticleActivity.this, ArticleActivity.class);
        startActivity(backIntent);
        finish();
    }

}