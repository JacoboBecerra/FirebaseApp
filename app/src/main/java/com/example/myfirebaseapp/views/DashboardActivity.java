package com.example.myfirebaseapp.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Aplicar el tema guardado
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("isDarkMode", true);
        AppCompatDelegate.setDefaultNightMode(
                isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        setContentView(R.layout.activity_dashboard);

        initializeViews();
        setupViewModel();
        setupRecyclerView();
        observeRecipes();
        setupButtons();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setupViewModel() {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        mAuth = FirebaseAuth.getInstance();
    }

    private void setupRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void observeRecipes() {
        dashboardViewModel.getRecipeList().observe(this, recipeList -> {
            if (recipeList != null && !recipeList.isEmpty()) {
                if (adapter == null) {
                    adapter = new RecipeAdapter(
                            recipeList,
                            this::onRecipeClick,
                            (recipe, isFavorite) -> dashboardViewModel.toggleFavorite(recipe, isFavorite)
                    );
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.updateRecipes(recipeList);
                }
            } else {
                Toast.makeText(DashboardActivity.this, "No se encontraron recetas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupButtons() {
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> performLogout());

        Button themeButton = findViewById(R.id.themeToggleButton);
        themeButton.setOnClickListener(v -> toggleTheme());
    }

    private void performLogout() {
        mAuth.signOut();
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(DashboardActivity.this, DetailActivity.class);
        intent.putExtra("title", recipe.getTitulo());
        intent.putExtra("description", recipe.getDescripcion());
        intent.putExtra("imageUrl", recipe.getImagen());
        startActivity(intent);
    }

    private void toggleTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("isDarkMode", true);

        AppCompatDelegate.setDefaultNightMode(
                isDarkMode ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES
        );

        sharedPreferences.edit().putBoolean("isDarkMode", !isDarkMode).apply();
        recreate();
    }
}