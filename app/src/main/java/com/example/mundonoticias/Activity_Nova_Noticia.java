package com.example.mundonoticias;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mundonoticias.Modelos.Categoria;
import com.example.mundonoticias.Modelos.ConnFirebase;
import com.example.mundonoticias.Modelos.DbBitmapUtility;
import com.example.mundonoticias.Modelos.Noticia;
import com.example.mundonoticias.Modelos.NoticiaListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class Activity_Nova_Noticia extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {




    private Noticia noticia = new Noticia();
    private String Tabela = "Noticias";
    private ArrayList<Noticia> Lista_Noticias = new ArrayList<>();
    private ArrayAdapter<Noticia> Lista_Noticias_Adapter;
    private ArrayAdapter<String> CatAdapterName;

    private static final int PICK_FROM_GALLARY = 1;
    private ImageView img_Noticia;
    private Button btn_NoticiaImg,btn_Nova_Categoria;
    private Spinner spn_Categoria;
    private EditText txt_Titulo,txt_Corpo,txt_Autor;
    private ListView list_Noticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_noticia);

    }

    private void Iniciar_Elementos() {
        img_Noticia = findViewById(R.id.img_Noticia);
        btn_NoticiaImg = findViewById(R.id.btn_NoticiaImg);
        btn_NoticiaImg.setOnClickListener(this);
        btn_Nova_Categoria = findViewById(R.id.btn_Nova_Categoria);
        btn_Nova_Categoria.setOnClickListener(this);
        spn_Categoria = findViewById(R.id.spn_Categoria);
        txt_Titulo = findViewById(R.id.txt_Titulo);
        txt_Corpo = findViewById(R.id.txt_Corpo);
        txt_Autor = findViewById(R.id.txt_Autor);
        txt_Autor.setText(ConnFirebase.currentUser.getEmail());
        list_Noticias = findViewById(R.id.list_Noticias);
        list_Noticias.setOnItemClickListener(this);
    }
    private void Limpar_Campos(){
        img_Noticia.setImageBitmap(null);
        txt_Titulo.setText("");
        txt_Corpo.setText("");
        txt_Autor.setText(ConnFirebase.currentUser.getEmail());
    }
    private void Popular_Obj_Noticia(){
        if(noticia.getNoticiaUID()=="" || noticia.getNoticiaUID() == null){
            noticia.setNoticiaUID(UUID.randomUUID().toString());
        }
        noticia.setCategoria(spn_Categoria.getSelectedItem().toString());
        noticia.setAutor(ConnFirebase.currentUser.getEmail());
        noticia.setConteudo(txt_Corpo.getText().toString());
        noticia.setTitulo(txt_Titulo.getText().toString());
        noticia.setPublicacao(noticia.Date_String(Calendar.getInstance().getTime()));
    }
    private boolean Validar_Noticia(){
        if(noticia.getCategoria() == null || noticia.getAutor() == ""
                || noticia.getConteudo() == "" || noticia.getTitulo() == ""){
             return false;
        }
        return true;
    }
    private void Atualizar_Dados(Context context) {
        ConnFirebase.DBR.child(Tabela).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Lista_Noticias.clear();
                for (DataSnapshot ObjSnap:snapshot.getChildren()) {
                    Noticia x = ObjSnap.getValue(Noticia.class);
                    Lista_Noticias.add(x);
                }
                Lista_Noticias_Adapter = new NoticiaListAdapter(context, Lista_Noticias);
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
                ArrayAdapter<String> CategoriaAdapterName = new ArrayAdapter<String>(Activity_Nova_Noticia.this,
                        android.R.layout.simple_spinner_item,
                        Lista_Categorias);
                CategoriaAdapterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_Categoria.setAdapter(CategoriaAdapterName);
                CatAdapterName = CategoriaAdapterName;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        ConnFirebase.OpenConnFirebase(Activity_Nova_Noticia.this);
        if(ConnFirebase.currentUser != null) {
            Iniciar_Elementos();
            Atualizar_Dados(Activity_Nova_Noticia.this);
        }else{
            Intent it = new Intent(Activity_Nova_Noticia.this,Activity_Login.class);
            startActivity(it);
        }

    }
    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch(view.getId()){
            case R.id.btn_NoticiaImg:
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_FROM_GALLARY);
                break;
            case R.id.btn_Nova_Categoria:
                intent = new Intent(this,Activity_Nova_Categoria.class);
                startActivity(intent);
                break;
        }

    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.list_Noticias:
                noticia = (Noticia) adapterView.getItemAtPosition(i);
                txt_Titulo.setText(noticia.getTitulo());
                txt_Corpo.setText(noticia.getConteudo());
                txt_Autor.setText(noticia.getAutor());
                if (noticia.getCategoria() != null) {
                    int spinnerPosition = CatAdapterName.getPosition(noticia.getCategoria());
                    spn_Categoria.setSelection(spinnerPosition);
                }
                Bitmap img = DbBitmapUtility.convert(noticia.getNoticiaImg());
                img_Noticia.setImageBitmap(img);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_FROM_GALLARY &&
                resultCode == RESULT_OK &&
                data != null && data.getData() != null){
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                img_Noticia.setImageBitmap(bitmap);
                noticia.setNoticiaImg(DbBitmapUtility.convert(bitmap));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

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
                Popular_Obj_Noticia();
                if(Validar_Noticia()){
                    boolean result = ConnFirebase.Insert(Tabela,noticia.getNoticiaUID(),noticia);
                    if(!result)
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    else{
                        Limpar_Campos();
                        Atualizar_Dados(Activity_Nova_Noticia.this);
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.Menu_Save:
                Popular_Obj_Noticia();
                if(Validar_Noticia()){
                    boolean result = ConnFirebase.Update(Tabela,noticia.getNoticiaUID(),noticia);
                    if(!result)
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    else{
                        Limpar_Campos();
                        Atualizar_Dados(Activity_Nova_Noticia.this);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
              break;
            case R.id.Menu_Remove:
                if(Validar_Noticia()){
                    boolean result = ConnFirebase.Delete(Tabela,noticia.getNoticiaUID());
                    if(!result)
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    else{
                        Limpar_Campos();
                        Atualizar_Dados(Activity_Nova_Noticia.this);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return true;
    }

}