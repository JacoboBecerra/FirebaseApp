package com.example.myfirebaseapp.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirebaseapp.R;
import com.example.myfirebaseapp.adapters.RecipeAdapter;
import com.example.myfirebaseapp.viewmodels.FavouritesViewModel;

public class FavouritesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private FavouritesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        initializeViews();
        setupViewModel();
        observeFavourites();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.favouritesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(FavouritesViewModel.class);
    }

    private void observeFavourites() {
        viewModel.getFavouriteRecipes().observe(this, recipes -> {
            if (recipes != null && !recipes.isEmpty()) {
                if (adapter == null) {
                    adapter = new RecipeAdapter(
                            recipes,
                            recipe -> {
                                // Implementar navegación al detalle si se desea
                            },
                            viewModel::toggleFavorite
                    );
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.updateRecipes(recipes);
                }
            } else {
                Toast.makeText(this, "No tienes recetas favoritas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}