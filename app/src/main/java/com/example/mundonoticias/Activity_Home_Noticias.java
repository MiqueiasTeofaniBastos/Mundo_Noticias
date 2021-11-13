package com.example.mundonoticias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.mundonoticias.Modelos.ConnFirebase;
import com.example.mundonoticias.Modelos.Noticia;
import com.example.mundonoticias.Modelos.NoticiaCardAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activity_Home_Noticias extends AppCompatActivity implements View.OnClickListener {

    private String Tabela = "Noticias";
    private ArrayList<Noticia> Lista_Noticias = new ArrayList<>();
    private ArrayAdapter<Noticia> Lista_Noticias_Adapter;

    private Noticia noticia;


    private ListView list_Noticias;
    private Button btn_Nova_Noticia;

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

    }


    private void Iniciar_Elementos() {
        list_Noticias = findViewById(R.id.list_Noticias);
        btn_Nova_Noticia = findViewById(R.id.btn_Nova_Noticia);
        btn_Nova_Noticia.setOnClickListener(this);
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
}