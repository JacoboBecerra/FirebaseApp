package com.example.myfirebaseapp.views;

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
import com.example.myfirebaseapp.viewmodels.FavouritesViewModel;

public class FavouritesFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private FavouritesViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        recyclerView = view.findViewById(R.id.favouritesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(this).get(FavouritesViewModel.class);

        observeFavourites();

        return view;
    }

    private void observeFavourites() {
        viewModel.getFavouriteRecipes().observe(getViewLifecycleOwner(), recipes -> {
            if (recipes != null && !recipes.isEmpty()) {
                if (adapter == null) {
                    adapter = new RecipeAdapter(
                            recipes,
                            recipe -> {
                                // Navigate to detail fragment
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
                            },
                            viewModel::toggleFavorite
                    );
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.updateRecipes(recipes);
                }
            } else {
                Toast.makeText(getContext(), "No tienes recetas favoritas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}