package com.example.mundonoticias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.mundonoticias.Modelos.Categoria;
import com.example.mundonoticias.Modelos.ConnFirebase;
import com.example.mundonoticias.Modelos.Noticia;
import com.example.mundonoticias.Modelos.NoticiaCardAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activity_Home_Noticias extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener {

    private String Tabela = "Noticias";
    private ArrayList<Noticia> Lista_Noticias = new ArrayList<>();
    private ArrayAdapter<Noticia> Lista_Noticias_Adapter;

    private Noticia noticia;


    private ListView list_Noticias;
    private Button btn_Nova_Noticia;
    private Spinner spn_Categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_noticias);

        Iniciar_Elementos();
        Atualizar_Dados(Activity_Home_Noticias.this);


    }

    private void Atualizar_Dados(Context context) {
        ConnFirebase.OpenConnFirebase(context);
        ConnFirebase.DBR.child(Tabela).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Lista_Noticias.clear();
                for (DataSnapshot ObjSnap:snapshot.getChildren()) {
                    Noticia x = ObjSnap.getValue(Noticia.class);
                    Lista_Noticias.add(x);
                }
                Lista_Noticias_Adapter = new NoticiaCardAdapter(context, Lista_Noticias);
                list_Noticias.setAdapter(Lista_Noticias_Adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ArrayList<String> Lista_Categorias = new ArrayList<>();
        ConnFirebase.DBR.child("Categorias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Lista_Categorias.clear();
                for (DataSnapshot ObjSnap:snapshot.getChildren()) {
                    Categoria x = ObjSnap.getValue(Categoria.class);
                    Lista_Categorias.add(x.getCategoria());
                }
                ArrayAdapter<String> CategoriaAdapterName = new ArrayAdapter<String>(Activity_Home_Noticias.this,
                        android.R.layout.simple_spinner_item,
                        Lista_Categorias);
                CategoriaAdapterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_Categoria.setAdapter(CategoriaAdapterName);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void Atualizar_Dados(Context context,String FiltroCategoria) {
        ConnFirebase.OpenConnFirebase(context);
        ConnFirebase.DBR.child(Tabela).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Lista_Noticias.clear();
                for (DataSnapshot ObjSnap:snapshot.getChildren()) {
                    Noticia x = ObjSnap.getValue(Noticia.class);
                    String Cat = x.getCategoria().toString();

                    if(FiltroCategoria.equals("#Todas#")){
                        Lista_Noticias.add(x);
                    }else if(Cat.equals(FiltroCategoria)){
                        Lista_Noticias.add(x);
                    }
                }
                Lista_Noticias_Adapter = new NoticiaCardAdapter(context, Lista_Noticias);
                list_Noticias.setAdapter(Lista_Noticias_Adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void Iniciar_Elementos() {
        list_Noticias = findViewById(R.id.list_Noticias);
        btn_Nova_Noticia = findViewById(R.id.btn_Nova_Noticia);
        btn_Nova_Noticia.setOnClickListener(this);
        spn_Categoria = findViewById(R.id.spn_Categoria);
        spn_Categoria.setOnItemSelectedListener(this);
        list_Noticias.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_Nova_Noticia:
                Intent it = new Intent(this,Activity_Nova_Noticia.class);
                startActivity(it);
                break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Atualizar_Dados(Activity_Home_Noticias.this,spn_Categoria.getSelectedItem().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Noticia a = (Noticia) adapterView.getItemAtPosition(i);
        Intent intent = new Intent(Activity_Home_Noticias.this,
                Activity_Detalhes_Noticia.class);

        intent.putExtra("getcategoria",a.getCategoria());
        intent.putExtra("getautor",a.getAutor());
        intent.putExtra("getnoticiaimg",a.getNoticiaImg());
        intent.putExtra("getconteudo",a.getConteudo());
        intent.putExtra("getpublicacao",a.getPublicacao());
        intent.putExtra("gettitulo",a.getTitulo());
        startActivity(intent);
    }
}