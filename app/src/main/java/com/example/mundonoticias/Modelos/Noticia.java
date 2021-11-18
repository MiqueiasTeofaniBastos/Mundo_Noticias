package com.example.mundonoticias.Modelos;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Noticia {
    private String NoticiaUID;
    private String NoticiaImg;
    private String Titulo;
    private String Conteudo;
    private String Autor;
    private String Categoria;
    private String Publicacao;

    public String getNoticiaUID() {
        return NoticiaUID;
    }

    public void setNoticiaUID(String noticiaUID) {
        NoticiaUID = noticiaUID;
    }

    public String getNoticiaImg() {
        return NoticiaImg;
    }

    public void setNoticiaImg(String noticiaImg) {
        NoticiaImg = noticiaImg;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getConteudo() {
        return Conteudo;
    }

    public void setConteudo(String conteudo) {
        Conteudo = conteudo;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String autor) {
        Autor = autor;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getPublicacao() {
        return Publicacao;
    }

    public void setPublicacao(String publicacao) {
        Publicacao = publicacao;
    }

    @Override
    public String toString() {
        return "Noticia{" +
                "Titulo='" + Titulo + '\'' +
                ", Categoria='" + Categoria + '\'' +
                '}';
    }

    public String Resumo_Noticia(){
        try{
            String result = getConteudo().substring(0,150)+"...";
            return result;
        }catch (Exception ex){
            return getConteudo();
        }
    }

    public String Date_String(Date date){
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dt = formato.format(date);
        return dt;
    }
    public Date String_Date(String date){
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try{
            Date  dt =  formato.parse(date);
            return dt;
        }catch(Exception exception){
            Date  dt = new Date();
            dt.setTime(0);
            return dt;
        }
    }
}
