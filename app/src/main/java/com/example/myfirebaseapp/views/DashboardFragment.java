package com.example.myfirebaseapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirebaseapp.R;
import com.example.myfirebaseapp.adapters.RecipeAdapter;
import com.example.myfirebaseapp.models.Recipe;
import com.example.myfirebaseapp.viewmodels.DashboardViewModel;

public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private DashboardViewModel dashboardViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        observeRecipes();

        return view;
    }

    private void observeRecipes() {
        dashboardViewModel.getRecipeList().observe(getViewLifecycleOwner(), recipeList -> {
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
                Toast.makeText(getContext(), "No se encontraron recetas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onRecipeClick(Recipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putString("title", recipe.getTitulo());
        bundle.putString("description", recipe.getDescripcion());
        bundle.putString("imageUrl", recipe.getImagen());

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}