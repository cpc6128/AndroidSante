package com.example.admin.androidsante;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.example.admin.androidsante.database.datasource.DataSource;
import com.example.admin.androidsante.database.modele.User;
import com.google.gson.Gson;

import java.util.List;

public class PrincipaleActivity extends FragmentActivity {
    final  static  String ARG_UTILISATEUR = "utilisateur";
    static final Integer RESULTNOM = 2;
    ListeUtilisateurFragment fragment;
    private DataSource<User> dataSource;
    // Pour quitter l'application :
    private Toast toast;
    private long lastBackPressTime = 0;
    private int versionDB = 4; // Permet de detruire la base de données SQLite si on incrémente la version


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste);


        if(findViewById(R.id.fragment_container)!=null){
            if(savedInstanceState!= null){
                return;
            }
            fragment = new ListeUtilisateurFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
        else {
            fragment = (ListeUtilisateurFragment) getSupportFragmentManager().findFragmentById(R.id.liste_fragment);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PrincipaleActivity.this, AjouterUtilisateurActivity.class);
                startActivityForResult(i, RESULTNOM);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (dataSource == null) {
                dataSource = new DataSource<>(this, User.class, versionDB);
                dataSource.open();
            }
        } catch (Exception e) {
            // Traiter le cas !
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            dataSource.close();
        } catch (Exception e) {
            // Traiter le cas !
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        chargerUtilisateurs();
    }

    private void chargerUtilisateurs() {
        // On charge les données depuis la base.
        try {
            List<User> users = dataSource.readAll();
            fragment.afficherList(users);
        } catch (Exception e) {
            // Que faire ?
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULTNOM) {

            String flux = data.getStringExtra(ARG_UTILISATEUR); // Tester si pas null ;-)
            User utilisateur = new Gson().fromJson(flux, User.class);

            try {
                dataSource.insert(utilisateur);
            } catch (Exception e) {
                // Que faire :-(
                e.printStackTrace();
            }

            // Indiquer un changement au RecycleView
            chargerUtilisateurs();

        }

    }

  /*  @Override
    public void onBackPressed() {
        if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
            toast = Toast.makeText(this, "Encore !!", Toast.LENGTH_LONG);
            toast.show();
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(this, "Bye bye !", Toast.LENGTH_LONG);
            toast.show();
            super.onBackPressed();
            this.finish();
            System.exit(0);
        }
    }*/

}
