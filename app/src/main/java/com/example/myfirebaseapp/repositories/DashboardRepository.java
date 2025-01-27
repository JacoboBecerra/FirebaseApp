package com.example.myfirebaseapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myfirebaseapp.models.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardRepository {

    private final DatabaseReference databaseReference;

    public DashboardRepository() {
        databaseReference = FirebaseDatabase.getInstance().getReference("recetas");
    }

    public LiveData<List<Recipe>> getRecipesFromDatabase() {
        MutableLiveData<List<Recipe>> recipeListLiveData = new MutableLiveData<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Recipe> recipeList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = snapshot.getValue(Recipe.class);
                    recipeList.add(recipe);
                }

                recipeListLiveData.setValue(recipeList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejo de errores
            }
        });

        return recipeListLiveData;
    }
}

