package com.example.myfirebaseapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myfirebaseapp.models.Recipe;
import com.example.myfirebaseapp.repositories.DashboardRepository;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {
    private final DashboardRepository dashboardRepository;
    private final MutableLiveData<List<Recipe>> recipeList;

    public DashboardViewModel(Application application) {
        super(application);
        dashboardRepository = new DashboardRepository();
        recipeList = new MutableLiveData<>();
        loadRecipes();
    }

    private void loadRecipes() {
        dashboardRepository.getRecipesFromDatabase().observeForever(recipes ->
                recipeList.setValue(recipes)
        );
    }

    public void toggleFavorite(Recipe recipe, boolean isFavorite) {
        dashboardRepository.toggleFavorite(recipe.getId(), isFavorite);
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return recipeList;
    }
}