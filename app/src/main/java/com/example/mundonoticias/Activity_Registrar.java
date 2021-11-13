package com.example.mundonoticias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_Registrar extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private EditText txt_Email,txt_Senha;
    private Button btn_Criar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        mAuth = FirebaseAuth.getInstance();

        Iniciar_Elementos();
    }

    private void Iniciar_Elementos() {
        txt_Email = findViewById(R.id.txt_Email);
        txt_Senha = findViewById(R.id.txt_Senha);
        btn_Criar = findViewById(R.id.btn_Criar);
        btn_Criar.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
    }

    public void Novo_Usuario(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            currentUser = mAuth.getCurrentUser();
                            Toast.makeText(Activity_Registrar.this, "Usuario Cadastrado!",
                                    Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Activity_Registrar.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            currentUser = null;
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_Criar:
                Novo_Usuario(txt_Email.getText().toString(), txt_Senha.getText().toString());
                break;
        }
    }
}