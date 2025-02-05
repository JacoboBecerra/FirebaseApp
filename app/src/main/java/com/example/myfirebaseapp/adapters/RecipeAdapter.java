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
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void updateRecipes(List<Recipe> newRecipes) {
        for (Recipe newRecipe : newRecipes) {
            for (int i = 0; i < recipeList.size(); i++) {
                Recipe oldRecipe = recipeList.get(i);
                if (oldRecipe.getId().equals(newRecipe.getId())) {
                    oldRecipe.setFavorite(newRecipe.isFavorite());
                    notifyItemChanged(i);
                    break;
                }
            }
        }
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

        public void bind(Recipe recipe) {
            titleTextView.setText(recipe.getTitulo());
            descriptionTextView.setText(recipe.getDescripcion());

            // Cargar imagen con Picasso
            Picasso.get().load(recipe.getImagen()).into(recipeImageView);

            // Asignar contentDescription dinámico para la imagen
            recipeImageView.setContentDescription("Imagen de la receta: " + recipe.getTitulo());

            // Cambiar el ícono de favorito y su descripción
            if (recipe.isFavorite()) {
                favoriteImageView.setImageResource(R.drawable.ic_favorite);
                favoriteImageView.setContentDescription("Quitar " + recipe.getTitulo() + " de favoritos");
            } else {
                favoriteImageView.setImageResource(R.drawable.ic_favorite_border);
                favoriteImageView.setContentDescription("Agregar " + recipe.getTitulo() + " a favoritos");
            }

            favoriteImageView.setOnClickListener(v -> {
                if (onFavoriteClickListener != null) {
                    onFavoriteClickListener.onFavoriteClick(recipe, !recipe.isFavorite());
                }
            });

            itemView.setOnClickListener(v -> {
                if (onRecipeClickListener != null) {
                    onRecipeClickListener.onRecipeClick(recipe);
                }
            });
        }
    }
}
