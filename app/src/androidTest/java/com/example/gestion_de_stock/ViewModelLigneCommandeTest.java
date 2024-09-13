//package com.example.gestion_de_stock;
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.lifecycle.ViewModelStore;
//import androidx.lifecycle.ViewModelStoreOwner;
//import androidx.test.core.app.ApplicationProvider;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//
//import com.example.gestion_de_stock.database.interne.entity.LigneCommande;
//import com.example.gestion_de_stock.database.interne.viewModel.ViewModelCommande;
//import com.example.gestion_de_stock.database.interne.viewModel.ViewModelLigneCommande;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static org.junit.Assert.*;
//
//import java.util.List;
//
//@RunWith(AndroidJUnit4.class)
//public class ViewModelLigneCommandeTest {
//
//    @Rule
//    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
//
//    private ViewModelLigneCommande viewModelLigneCommande;
//
//    @Before
//    public void setUp() throws IllegalAccessException, InstantiationException {
//        // Use ApplicationProvider to get a valid application context
//        viewModelLigneCommande =  new ViewModelProvider(ViewClientActivity.class.newInstance()).get(ViewModelLigneCommande.class);
//    }
//
//    @Test
//    public void testFindAllLigneCommande() {
//        // Observe LiveData
//        final List<LigneCommande>[] result = new List[]{null};
//
//        Observer<List<LigneCommande>> observer = new Observer<List<LigneCommande>>() {
//            @Override
//            public void onChanged(List<LigneCommande> commandes) {
//                // Store the result
//                result[0] = commandes;
//            }
//        };
//
//        viewModelLigneCommande.findAllLigneCommande().observeForever(observer);
//
//        // Simulate data fetch or update
//        // Here you might want to use a mock repository or other means to set up the data
//
//        // Perform your assertions
//        assertNotNull("Result should not be null", result[0]);
//        // Add more assertions based on expected behavior
//
//        // Clean up
//        viewModelLigneCommande.findAllLigneCommande().removeObserver(observer);
//    }
//}
