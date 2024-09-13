//package com.example.gestion_de_stock;
//package com.example.gestion_de_stock;
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.example.gestion_de_stock.database.interne.viewModel.ViewModelLigneCommande;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static org.junit.Assert.*;
//
//import android.app.Application;
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
//    public void setUp() {
//        // You would typically use a test-specific application context or mock repository here.
//        viewModelLigneCommande = new ViewModelProvider.AndroidViewModelFactory(
//                new Application()).create(ViewModelLigneCommande.class);
//    }
//
//    @Test
//    public void addition_isCorrect() {
//        // Your test code here
//        System.out.println("hello");
//        assertTrue(true); // Replace this with actual assertions
//    }
//}
