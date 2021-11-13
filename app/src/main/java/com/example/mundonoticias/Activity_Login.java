package com.example.mundonoticias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mundonoticias.Modelos.ConnFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_Login extends AppCompatActivity implements View.OnClickListener {

    private EditText txt_Email,txt_Senha;
    private Button btn_Entrar;
    private TextView txt_Criar_Usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Iniciar_Elementos();

    }

    private void Iniciar_Elementos() {
        txt_Email = findViewById(R.id.txt_Email);
        txt_Senha = findViewById(R.id.txt_Senha);
        btn_Entrar = findViewById(R.id.btn_Entrar);
        btn_Entrar.setOnClickListener(this);
        txt_Criar_Usuario = findViewById(R.id.txt_Criar_Usuario);
        txt_Criar_Usuario.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if(ConnFirebase.currentUser != null){
            onBackPressed();
        }
    }


    public void Logar(String email, String password){
        ConnFirebase.OpenConnFirebase(Activity_Login.this);
        ConnFirebase.mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            ConnFirebase.currentUser = ConnFirebase.mAuth.getCurrentUser();
                            onBackPressed();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Activity_Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            ConnFirebase.currentUser = null;
                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_Entrar:
                if(txt_Email.getText().toString() != "" && txt_Senha.getText().toString()!= ""){
                    Logar(txt_Email.getText().toString(), txt_Senha.getText().toString());
                }
                break;
            case R.id.txt_Criar_Usuario:
                Intent it = new Intent(Activity_Login.this,Activity_Registrar.class);
                startActivity(it);
                break;
        }

    }
}