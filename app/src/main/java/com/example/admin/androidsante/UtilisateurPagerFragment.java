package com.example.admin.androidsante;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.androidsante.database.modele.User;
import com.google.gson.Gson;

/**
 * Created by admin on 16/06/2017.
 */

public class UtilisateurPagerFragment extends Fragment {


    public static UtilisateurPagerFragment newInstance(User utilisateur) {
        UtilisateurPagerFragment f = new UtilisateurPagerFragment();
        Bundle b = new Bundle();
        String flux = (new Gson()).toJson(utilisateur);
        b.putString(UtilisateurFragment.ARG_UTILISATEUR,flux);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.utilisateurpager, container, false);
        TextView textNom = (TextView) v.findViewById(R.id.article);
        TextView textAge = (TextView) v.findViewById(R.id.age);
        User utilisateur = new Gson().fromJson(getArguments().getString(UtilisateurFragment.ARG_UTILISATEUR), User.class);
        textNom.setText(utilisateur.getNom());
        textAge.setText(utilisateur.getAge() + " an(s)");
        return v;
    }
}