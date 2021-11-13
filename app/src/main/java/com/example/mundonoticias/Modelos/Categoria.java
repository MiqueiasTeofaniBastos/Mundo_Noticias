package com.example.mundonoticias.Modelos;

public class Categoria {
    private String CategoriaUID;
    private String Categoria;

    public String getCategoriaUID() {
        return CategoriaUID;
    }

    public void setCategoriaUID(String categoriaUID) {
        CategoriaUID = categoriaUID;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "CategoriaUID='" + CategoriaUID + '\'' +
                ", Categoria='" + Categoria + '\'' +
                '}';
    }
}
