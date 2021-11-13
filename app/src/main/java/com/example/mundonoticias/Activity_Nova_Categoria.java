package com.example.mundonoticias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mundonoticias.Modelos.Categoria;
import com.example.mundonoticias.Modelos.CategoriaAdapter;
import com.example.mundonoticias.Modelos.ConnFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class Activity_Nova_Categoria extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private String Tabela = "Categorias";
    private ArrayList<Categoria> Lista_Categorias = new ArrayList<>();
    private ArrayAdapter<Categoria> Lista_Categoria_Adapter;
    private Categoria categoria = new Categoria();


    private EditText txt_Categoria;
    private ListView list_Categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_categoria);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //Check if user is signed in (non-null) and update UI accordingly.
        ConnFirebase.OpenConnFirebase(Activity_Nova_Categoria.this);
        if(ConnFirebase.currentUser != null) {
            Iniciar_Elementos();
            Atualizar_Dados(Activity_Nova_Categoria.this);
        }else{
            Intent it = new Intent(Activity_Nova_Categoria.this,Activity_Login.class);
            startActivity(it);
        }
    }

    private void Iniciar_Elementos() {
        txt_Categoria = findViewById(R.id.txt_Categoria);
        list_Categoria = findViewById(R.id.list_Categoria);
        list_Categoria.setOnItemClickListener(this);
    }
    private void Atualizar_Dados(Context context) {
        ConnFirebase.DBR.child(Tabela).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Lista_Categorias.clear();
                for (DataSnapshot ObjSnap:snapshot.getChildren()) {
                    Categoria c = ObjSnap.getValue(Categoria.class);
                    Lista_Categorias.add(c);
                }
                Lista_Categoria_Adapter = new CategoriaAdapter(context, Lista_Categorias);
                list_Categoria.setAdapter(Lista_Categoria_Adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void Limpar_Campos(){
        txt_Categoria.setText("");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.list_Categoria:
                categoria = (Categoria) adapterView.getItemAtPosition(i);
                txt_Categoria.setText(categoria.getCategoria());
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crud,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Menu_New:
                categoria.setCategoria(txt_Categoria.getText().toString());
                if(categoria.getCategoria().equals(""))
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                else{
                    categoria.setCategoriaUID(UUID.randomUUID().toString());
                    boolean result = ConnFirebase.Insert(Tabela,categoria.getCategoriaUID(),categoria);
                    if(!result)
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    else{
                        Limpar_Campos();
                        Atualizar_Dados(Activity_Nova_Categoria.this);
                    }
                }
                break;
            case R.id.Menu_Save:
                categoria.setCategoria(txt_Categoria.getText().toString());
                if(categoria.getCategoria().equals("")){
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
                else{
                    boolean result = ConnFirebase.Update(Tabela,categoria.getCategoriaUID(),categoria);
                    if(!result)
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    else{
                        Limpar_Campos();
                        Atualizar_Dados(Activity_Nova_Categoria.this);
                    }
                }
                break;
            case R.id.Menu_Remove:
                categoria.setCategoria(txt_Categoria.getText().toString());
                if(categoria.getCategoria().equals("")) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
                else{
                    boolean result = ConnFirebase.Delete(Tabela,categoria.getCategoriaUID());
                    if(!result)
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    else{
                        Limpar_Campos();
                        Atualizar_Dados(Activity_Nova_Categoria.this);
                    }
                }
                break;
        }
        return true;
    }
}