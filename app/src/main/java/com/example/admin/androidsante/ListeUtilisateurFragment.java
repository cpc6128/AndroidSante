package com.example.admin.androidsante;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.androidsante.database.modele.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 16/06/2017.
 */

public class ListeUtilisateurFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<User> utilisateurs = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.list_utilisateur,container,false);
        recyclerView = (RecyclerView) view;
        adapter = new RecyclerViewAdapter(utilisateurs,this);
        recyclerView.setAdapter(adapter);
        return  view;
    }

    public void afficherList(List<User> list){
        utilisateurs.clear();
        utilisateurs.addAll(list);
        adapter.notifyDataSetChanged();

    }
}
