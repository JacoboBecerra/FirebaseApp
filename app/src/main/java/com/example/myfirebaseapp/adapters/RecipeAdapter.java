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
    private OnRecipeClickListener onRecipeClickListener;

    // Constructor para recibir el listado de recetas y el listener
    public RecipeAdapter(List<Recipe> recipeList, OnRecipeClickListener onRecipeClickListener) {
        this.recipeList = recipeList;
        this.onRecipeClickListener = onRecipeClickListener;
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

        // Manejar el clic
        holder.itemView.setOnClickListener(v -> {
            if (onRecipeClickListener != null) {
                onRecipeClickListener.onRecipeClick(recipe); // Llamar al método del listener
            }
        });
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

    // Interfaz para manejar clics
    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe); // Método que se llama cuando se hace clic en una receta
    }
}
