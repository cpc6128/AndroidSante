package com.example.admin.androidsante;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.admin.androidsante.database.modele.User;
import com.google.gson.Gson;

public class AjouterUtilisateurActivity extends Activity {


    private EditText nom;
    private EditText age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        nom = (EditText) findViewById(R.id.nom);
        age = (EditText) findViewById(R.id.age);
    }

    public void sauvegarder(View v) {

        User utilisateur = new User();
        utilisateur.setNom(nom.getText().toString());
        utilisateur.setAge(age.getText().toString());
        // Transformation en JSON :
        String flux = (new Gson()).toJson(utilisateur);

        // On dépose notre utilisateur jsonné dans l'intent
        Intent resultIntent = new Intent();
        resultIntent.putExtra(PrincipaleActivity.ARG_UTILISATEUR, flux);
        setResult(2, resultIntent);

        // Bye l'activité
        finish();

    }
}

