package com.example.myfirebaseapp.models;

public class Recipe {

    private String titulo;
    private String imagen;
    private String descripcion;

    // Constructor vacío necesario para Firebase
    public Recipe() {}

    public Recipe(String titulo, String imagen, String descripcion) {
        this.titulo = titulo;
        this.imagen = imagen;
        this.descripcion = descripcion;
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
}
