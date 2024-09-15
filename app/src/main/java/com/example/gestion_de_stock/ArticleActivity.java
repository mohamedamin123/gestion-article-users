package com.example.gestion_de_stock;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gestion_de_stock.adapter.ArticleAdapter;
import com.example.gestion_de_stock.adapter.GenericAdapter;
import com.example.gestion_de_stock.database.interne.entity.Article;
import com.example.gestion_de_stock.database.interne.entity.Commande;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelArticle;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelClient;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelCommande;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelLigneCommande;
import com.example.gestion_de_stock.databinding.ActivityArticleBinding;
import com.example.gestion_de_stock.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends AppCompatActivity implements ArticleAdapter.OnItemClickListener {
    private ActivityArticleBinding binding;
    private ViewModelArticle viewModelArticle;
    private ArticleAdapter adapter;
    private List<Article> articleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModelArticle = new ViewModelProvider(this).get(ViewModelArticle.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerClient.setLayoutManager(layoutManager);

        articleList = new ArrayList<>();
        adapter = new ArticleAdapter(articleList, this);
        binding.recyclerClient.setAdapter(adapter);


        getArticles();
    }

    private void getArticles() {

        articleList.clear();
        // TODO: récupérer les articles depuis la base de données et afficher dans la RecyclerView
        viewModelArticle.findAllArticle().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                for (Article art : articles) {
                    articleList.add(art);
                }
                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    binding.buttonAnnuler.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(ArticleActivity.this,MainActivity.class));
            finish();
        }
    });

    binding.buttonAjouter.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent1 = new Intent(ArticleActivity.this, AjouterArticleActivity.class);
            startActivity(intent1);
        }
    });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ArticleActivity.this,MainActivity.class));
        finish();
    }

    @Override
    public void onItemClick(Object item) {
        if (item instanceof Article) {
            Article article = (Article) item;
            creeAlert(article);

        }
    }

    private void creeAlert(Article article) {
        Log.d("commande",article.toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Action pour " + article.getNom());
        builder.setMessage("Que voulez-vous faire avec cette commande ?");

        // Ajouter image action
        builder.setPositiveButton("Ajouter image", (dialog, which) -> {
            Intent intent = new Intent(ArticleActivity.this, ImageCommandeActivity.class);
            intent.putExtra("idArticle",article.getIdArticle());
            startActivity(intent);
            // Implement logic for adding an image
        });

        // Modifier action
        builder.setNeutralButton("Modifier", (dialog, which) -> {
            Intent intent = new Intent(ArticleActivity.this, AjouterArticleActivity.class);
            intent.putExtra("idArticle",article.getIdArticle());
            startActivity(intent);

        });

        // Supprimer action
        builder.setNegativeButton("Supprimer", (dialog, which) -> {
            // Confirmation dialog for deletion
            new AlertDialog.Builder(ArticleActivity.this)
                    .setTitle("Confirmer suppression")
                    .setMessage("Voulez-vous vraiment supprimer cette article ?")
                    .setPositiveButton("Oui", (confirmDialog, confirmWhich) -> {
                        // Logic to delete the item
                        try {
                            viewModelArticle.deleteArticleById(article.getIdArticle(), new ViewModelLigneCommande.OnOperationCompleteListener() {
                                @Override
                                public void onOperationComplete(boolean success) {
                                    if (success) {
                                        // Perform insertion after deletion is complete
                                        viewModelArticle.deleteArticleById(article.getIdArticle());
                                        articleList.clear();
                                    } else {
                                        Toast.makeText(ArticleActivity.this, getResources().getText(R.string.probleme), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });



                            adapter.notifyDataSetChanged();
                            Toast.makeText(ArticleActivity.this, getResources().getText(R.string.article_supprimer), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(ArticleActivity.this, getResources().getText(R.string.error_suuprimer), Toast.LENGTH_SHORT).show();
                        }
                        // Implement logic for deletion
                    })
                    .setNegativeButton("Non", (confirmDialog, confirmWhich) -> {
                        // Logic for cancelling deletion
                        confirmDialog.dismiss();
                    })
                    .show();
        });

        // Show the dialog
        builder.create().show();
    }

}