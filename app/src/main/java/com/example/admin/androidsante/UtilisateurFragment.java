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

public class UtilisateurFragment extends Fragment {

    final  static  String ARG_UTILISATEUR = "utilisateur";

    User utilisateur;
    TextView article;
    TextView age;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.utilisateur,container,false);
        article = (TextView) view.findViewById(R.id.article);
        age = (TextView) view.findViewById(R.id.age);


        if(savedInstanceState!= null){
            String flux = savedInstanceState.getString(ARG_UTILISATEUR);
            utilisateur = new Gson().fromJson(flux, User.class);
            updateArticleView(utilisateur);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if(args !=null){
            String flux = args.getString(ARG_UTILISATEUR); // Tester si pas null ;-)
            utilisateur = new Gson().fromJson(flux, User.class);
            updateArticleView(utilisateur);

        }
    }

    public void updateArticleView(User user){
        if(article != null){
            try {
                utilisateur = user;
                article.setText(user.getNom());
                if(user.getAge()==null)
                    age.setText(R.string.noAge);
                else
                    age.setText(user.getAge() + " an(s)");
            } catch (Exception e) {
                // Traiter le cas !
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        String flux = (new Gson()).toJson(utilisateur);
        outState.putString(ARG_UTILISATEUR,flux);
    }


}
