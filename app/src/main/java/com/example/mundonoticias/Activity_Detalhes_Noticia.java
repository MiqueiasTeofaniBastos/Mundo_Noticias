package com.example.mundonoticias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mundonoticias.Modelos.DbBitmapUtility;
import com.example.mundonoticias.Modelos.Noticia;

public class Activity_Detalhes_Noticia extends AppCompatActivity {

    private TextView txt_Categoria,txt_Titulo
            ,txt_Conteudo,txt_Autor,txt_Publicacao;
    private ImageView img_Noticia;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_noticia);

        Inicar_Componentes();

        Carregar_Dados();
    }

    private void Carregar_Dados() {
        Intent it  = getIntent();



        txt_Categoria.setText(it.getStringExtra("getcategoria"));
        txt_Autor.setText(it.getStringExtra("getautor"));
        txt_Conteudo.setText(it.getStringExtra("getconteudo"));
        txt_Publicacao.setText(it.getStringExtra("getpublicacao"));
        txt_Titulo.setText(it.getStringExtra("gettitulo"));
        String stringImg = it.getStringExtra("getnoticiaimg");
        Bitmap Imagem = DbBitmapUtility.convert(stringImg);
        img_Noticia.setImageBitmap(Imagem);
    }

    private void Inicar_Componentes() {
        txt_Categoria = findViewById(R.id.txt_Categoria);
        txt_Titulo = findViewById(R.id.txt_Titulo);
        txt_Conteudo = findViewById(R.id.txt_Conteudo);
        txt_Autor = findViewById(R.id.txt_Autor);
        txt_Publicacao = findViewById(R.id.txt_Publicacao);
        img_Noticia = findViewById(R.id.img_Noticia);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}