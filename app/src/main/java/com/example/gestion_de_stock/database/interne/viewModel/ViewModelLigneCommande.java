package com.example.gestion_de_stock.database.interne.viewModel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gestion_de_stock.database.interne.MyRoomDataBase;
import com.example.gestion_de_stock.database.interne.entity.LigneCommande;
import com.example.gestion_de_stock.database.interne.repository.RepositoryLigneCommande;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ViewModelLigneCommande extends AndroidViewModel {
    private final RepositoryLigneCommande repository;

    public ViewModelLigneCommande(@NonNull Application application) {
        super(application);
        repository = new RepositoryLigneCommande(application);
    }

    public interface OnInsertCompleteListener {
        void onInsertComplete(boolean success);
    }

    public void insertOrUpdateLigneCommandes(OnInsertCompleteListener listener, List<LigneCommande> ligneCommandes) {
        new Thread(() -> {
            try {
                // Fetch all existing LigneCommandes
                List<LigneCommande> existingLigneCommandes = repository.findAllLigneCommande().getValue();

                if (existingLigneCommandes == null) {
                    existingLigneCommandes = new ArrayList<>();
                }

                // Process insertion or update
                for (LigneCommande ligneCommande : ligneCommandes) {
                    boolean found = false;

                    // Check if the record exists by ID
                    for (LigneCommande existingLigneCommande : existingLigneCommandes) {
                        if (Objects.equals(existingLigneCommande.getIdLigneCommande(), ligneCommande.getIdLigneCommande())) {
                            // Update the record if it exists by ID
                            repository.updateLigneCommande(ligneCommande);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        // If not found by ID, check by color and quantity
                        for (LigneCommande existingLigneCommande : existingLigneCommandes) {
                            if (existingLigneCommande.getColor().equals(ligneCommande.getColor()) &&
                                    existingLigneCommande.getQte() == ligneCommande.getQte() &&
                                    !Objects.equals(existingLigneCommande.getIdLigneCommande(), ligneCommande.getIdLigneCommande()) &&
                                    existingLigneCommande.getCommandeId().equals(ligneCommande.getCommandeId())) {

                                // Update the record if it exists by color, quantity, and CommandeId
                                repository.updateLigneCommande(existingLigneCommande);
                                found = true;
                                break;
                            }
                        }
                    }

                    if (!found) {
                        // If not found by both ID and criteria, insert the new record
                        repository.insertLigneCommandes(ligneCommande);

                        // Remove duplicates immediately after each insertion
                        removeDuplicatesAfterInsert(ligneCommande);
                    }
                }

                // Notify listener of successful operation
                new Handler(Looper.getMainLooper()).post(() -> listener.onInsertComplete(true));
            } catch (Exception e) {
                e.printStackTrace();
                // Notify listener of failure
                new Handler(Looper.getMainLooper()).post(() -> listener.onInsertComplete(false));
            }
        }).start();
    }

    public void removeDuplicatesAfterInsert(LigneCommande newLigneCommande) {
        // Fetch all records that match the color, quantity, and CommandeId of the newly inserted LigneCommande
        List<LigneCommande> matchingLigneCommandes = repository.findLigneCommandesByColorAndQteAndCommandeId(
                newLigneCommande.getColor(),
                newLigneCommande.getQte(),
                newLigneCommande.getCommandeId()
        );

        // If there are more than one matching record, keep the first one and delete the others
        if (matchingLigneCommandes != null && matchingLigneCommandes.size() > 1) {
            for (int i = 1; i < matchingLigneCommandes.size(); i++) {
                repository.deleteLigneCommande(matchingLigneCommandes.get(i));
            }
        }
    }



    public LiveData<List<LigneCommande>> findAllLigneCommande() {
        return repository.findAllLigneCommande();
    }

    public void updateLigneCommande(LigneCommande... ligneCommandes) {
        repository.updateLigneCommande(ligneCommandes);
    }

    public LiveData<LigneCommande> findLigneCommandeById(Integer id) {
        return repository.findLigneCommandeById(id);
    }

    public LiveData<List<LigneCommande>> findLigneCommandeByIdCommande(Integer id) {
        return repository.findLigneCommandeByIdCommande(id);
    }

    public LiveData<List<LigneCommande>> findAllLigneCommandeByCommandeId(int id) {
        return repository.findAllLigneCommandeByCommandeId(id);
    }
    public void deleteLigneCommandeByIdCommande(Integer id, OnOperationCompleteListener listener) {
        new Thread(() -> {
            try {
                repository.deleteLigneCommandeByIdCommande(id);
                // Notify the listener of success on the main thread
                new Handler(Looper.getMainLooper()).post(() -> listener.onOperationComplete(true));
            } catch (Exception e) {
                // Notify the listener of failure on the main thread
                new Handler(Looper.getMainLooper()).post(() -> listener.onOperationComplete(false));
            }
        }).start();
    }

    public void deleteLigneCommandeById(Integer id) {
        repository.deleteLigneCommandeById(id);
    }



    public interface OnOperationCompleteListener {
        void onOperationComplete(boolean success);
    }
}




