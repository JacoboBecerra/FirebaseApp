package com.example.myfirebaseapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirebaseapp.R;
import com.example.myfirebaseapp.adapters.RecipeAdapter;
import com.example.myfirebaseapp.models.Recipe;
import com.example.myfirebaseapp.viewmodels.DashboardViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private DashboardViewModel dashboardViewModel;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar ViewModel
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        // Inicializar FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Observar los cambios en la lista de recetas
        dashboardViewModel.getRecipeList().observe(this, recipeList -> {
            if (recipeList != null && !recipeList.isEmpty()) {
                // Si hay recetas, actualiza el RecyclerView
                adapter = new RecipeAdapter(recipeList);
                recyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(DashboardActivity.this, "No se encontraron recetas", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el botón de cerrar sesión
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            // Cerrar sesión en Firebase
            mAuth.signOut();

            // Redirigir a la LoginActivity
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Evitar que el usuario regrese al Dashboard
            startActivity(intent);
            finish(); // Finalizar la actividad actual para que no quede en la pila de actividades
        });
    }
}
