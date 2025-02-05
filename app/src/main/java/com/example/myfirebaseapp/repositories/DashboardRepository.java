package com.example.myfirebaseapp.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myfirebaseapp.models.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class DashboardRepository {
    private final DatabaseReference databaseReference;
    private final DatabaseReference userFavoritesReference;
    private final FirebaseAuth mAuth;

    public DashboardRepository() {
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("recetas");
        userFavoritesReference = FirebaseDatabase.getInstance().getReference("usuarios");
    }

    public LiveData<List<Recipe>> getFavouriteRecipes() {
        MutableLiveData<List<Recipe>> favouriteRecipesLiveData = new MutableLiveData<>();
        String userId = mAuth.getCurrentUser().getUid();

        userFavoritesReference.child(userId).child("favoritos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot favoritesSnapshot) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot recipesSnapshot) {
                        List<Recipe> favouriteRecipes = new ArrayList<>();

                        for (DataSnapshot recipeSnapshot : recipesSnapshot.getChildren()) {
                            if (favoritesSnapshot.hasChild(recipeSnapshot.getKey())) {
                                Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                                if (recipe != null) {
                                    recipe.setId(recipeSnapshot.getKey());
                                    recipe.setFavorite(true);
                                    favouriteRecipes.add(recipe);
                                }
                            }
                        }

                        favouriteRecipesLiveData.setValue(favouriteRecipes);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Manejar error
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar error
            }
        });

        return favouriteRecipesLiveData;
    }

    public void toggleFavorite(String recipeId, boolean isFavorite) {
        String userId = mAuth.getCurrentUser().getUid();
        userFavoritesReference
                .child(userId)
                .child("favoritos")
                .child(recipeId)
                .setValue(isFavorite ? true : null);  // Si es false, eliminamos la entrada
    }

    public LiveData<List<Recipe>> getRecipesFromDatabase() {
        MutableLiveData<List<Recipe>> recipeListLiveData = new MutableLiveData<>();
        String userId = mAuth.getCurrentUser().getUid();

        // Primero obtener los favoritos del usuario
        userFavoritesReference.child(userId).child("favoritos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot favoritesSnapshot) {
                // Luego obtener las recetas
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot recipesSnapshot) {
                        List<Recipe> recipeList = new ArrayList<>();

                        for (DataSnapshot snapshot : recipesSnapshot.getChildren()) {
                            Recipe recipe = snapshot.getValue(Recipe.class);
                            if (recipe != null) {
                                recipe.setId(snapshot.getKey());
                                // Verificar si la receta está en favoritos
                                recipe.setFavorite(favoritesSnapshot.hasChild(recipe.getId()));
                                recipeList.add(recipe);
                            }
                        }

                        recipeListLiveData.setValue(recipeList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Manejar error
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar error
            }
        });

        return recipeListLiveData;
    }
}
