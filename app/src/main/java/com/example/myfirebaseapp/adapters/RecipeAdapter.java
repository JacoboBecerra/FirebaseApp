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
    private OnFavoriteClickListener onFavoriteClickListener;

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClick(Recipe recipe, boolean isFavorite);
    }

    public RecipeAdapter(List<Recipe> recipeList, OnRecipeClickListener onRecipeClickListener,
                         OnFavoriteClickListener onFavoriteClickListener) {
        this.recipeList = recipeList;
        this.onRecipeClickListener = onRecipeClickListener;
        this.onFavoriteClickListener = onFavoriteClickListener;
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

        // Cargar la imagen de la receta
        Picasso.get().load(recipe.getImagen()).into(holder.recipeImageView);

        // Configurar el icono de favorito
        holder.favoriteImageView.setImageResource(
                recipe.isFavorite() ? R.drawable.ic_favorite : R.drawable.ic_favorite_border
        );

        // Click listener para el icono de favorito
        holder.favoriteImageView.setOnClickListener(v -> {
            boolean newFavoriteStatus = !recipe.isFavorite();
            recipe.setFavorite(newFavoriteStatus);
            holder.favoriteImageView.setImageResource(
                    newFavoriteStatus ? R.drawable.ic_favorite : R.drawable.ic_favorite_border
            );
            if (onFavoriteClickListener != null) {
                onFavoriteClickListener.onFavoriteClick(recipe, newFavoriteStatus);
            }
        });

        // Click listener para el item completo
        holder.itemView.setOnClickListener(v -> {
            if (onRecipeClickListener != null) {
                onRecipeClickListener.onRecipeClick(recipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView;
        ImageView recipeImageView, favoriteImageView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.recipeTitleTextView);
            descriptionTextView = itemView.findViewById(R.id.recipeDescriptionTextView);
            recipeImageView = itemView.findViewById(R.id.recipeImageView);
            favoriteImageView = itemView.findViewById(R.id.favoriteImageView);
        }
    }
}
