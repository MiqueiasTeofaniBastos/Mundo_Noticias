package com.example.mundonoticias.Modelos;

import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConnFirebase {

    private static String URL_FIREBASE = "https://mundo-noticias-1a065-default-rtdb.firebaseio.com";
    public static FirebaseDatabase FBDB;
    public static DatabaseReference DBR;
    public static FirebaseAuth mAuth;
    public static FirebaseUser currentUser;

    public ConnFirebase() {
    }

    public static void OpenConnFirebase(Context context) {
        FirebaseApp.initializeApp(context);
        if(mAuth == null){
           mAuth = FirebaseAuth.getInstance();
        }
        if(FBDB == null){
            FBDB = FirebaseDatabase.getInstance(URL_FIREBASE);
            FBDB.setPersistenceEnabled(true);
        }
        if(DBR == null){
            DBR = FBDB.getReference();
        }
        currentUser = mAuth.getCurrentUser();
    }
    public static boolean Insert(String Tabela,String UID, Object o){
        try {
            DBR.child(Tabela).child(UID).setValue(o);
        }catch (Exception ex){
            return false;
        }
        return true;
    }
    public static boolean Update(String Tabela,String UID, Object o){
        try {
            DBR.child(Tabela).child(UID).setValue(o);
        }catch (Exception ex){
            return false;
        }
        return true;
    }
    public static boolean Delete(String Tabela,String UID) {
        try {
            DBR.child(Tabela).child(UID).removeValue();
        }catch (Exception ex){
            return false;
        }
        return true;
    }
}
