package com.example.myfirebaseapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirebaseapp.R;
import com.example.myfirebaseapp.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;

    public RecipeAdapter(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.titleTextView.setText(recipe.getTitulo());
        holder.descriptionTextView.setText(recipe.getDescripcion());

        // Cargar la imagen usando Picasso
        Picasso.get().load(recipe.getImagen()).into(holder.recipeImageView);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, descriptionTextView;
        ImageView recipeImageView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.recipeTitleTextView);
            descriptionTextView = itemView.findViewById(R.id.recipeDescriptionTextView);
            recipeImageView = itemView.findViewById(R.id.recipeImageView);
        }
    }
}

