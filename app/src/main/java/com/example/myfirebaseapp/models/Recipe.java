package com.example.myfirebaseapp.models;

import java.util.Objects;

public class Recipe {
    private String titulo;
    private String imagen;
    private String descripcion;
    private String id;
    private boolean isFavorite;

    public Recipe() {
        // Constructor vacío necesario para Firebase
    }

    public Recipe(String id, String titulo, String imagen, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.isFavorite = false;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(id, recipe.id) &&
                Objects.equals(titulo, recipe.titulo) &&
                Objects.equals(descripcion, recipe.descripcion) &&
                Objects.equals(imagen, recipe.imagen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, descripcion, imagen);
    }
}