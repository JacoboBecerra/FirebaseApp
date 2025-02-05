package com.example.myfirebaseapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myfirebaseapp.models.Recipe;
import com.example.myfirebaseapp.repositories.DashboardRepository;

import java.util.List;

public class FavouritesViewModel extends AndroidViewModel {
    private final DashboardRepository repository;
    private final MutableLiveData<List<Recipe>> favouriteRecipes;

    public FavouritesViewModel(Application application) {
        super(application);
        repository = new DashboardRepository();
        favouriteRecipes = new MutableLiveData<>();
        loadFavourites();
    }

    private void loadFavourites() {
        repository.getFavouriteRecipes().observeForever(recipes ->
                favouriteRecipes.setValue(recipes)
        );
    }

    public void toggleFavorite(Recipe recipe, boolean isFavorite) {
        repository.toggleFavorite(recipe.getId(), isFavorite);
    }

    public LiveData<List<Recipe>> getFavouriteRecipes() {
        return favouriteRecipes;
    }
}