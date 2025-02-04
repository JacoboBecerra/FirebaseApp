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
                adapter = new RecipeAdapter(
                        recipeList,
                        this::onRecipeClick,
                        (recipe, isFavorite) -> dashboardViewModel.toggleFavorite(recipe, isFavorite)
                );
                recyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(DashboardActivity.this, "No se encontraron recetas", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el botón de cerrar sesión
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(DashboardActivity.this, DetailActivity.class);
        intent.putExtra("title", recipe.getTitulo());
        intent.putExtra("description", recipe.getDescripcion());
        intent.putExtra("imageUrl", recipe.getImagen());
        startActivity(intent);
    }
}