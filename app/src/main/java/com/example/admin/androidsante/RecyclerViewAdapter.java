package com.example.admin.androidsante;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.androidsante.database.modele.User;

import java.util.List;

/**
 * Cette classe se charge d’associer un contenu (Utilisateur) au contenant (les View).
 * <p>
 * UtilisateurViewHolder : établit un lien entre la vue et ses éléments
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> utilisateurs;
    private ListeUtilisateurFragment mainActivity;

    public RecyclerViewAdapter(List<User> utilisateurs,ListeUtilisateurFragment mainActivity) {
        this.utilisateurs = utilisateurs;
        this.mainActivity = mainActivity;
    }

    /**
     * Méthode appellée lors du premier affichage
     * onCreateViewHolder sera appelée lorsque le RecyclerView aura besoin d’un nouveau UtilisateurViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.utilisateur_list_content, parent, false);
        return new UtilisateurViewHolder(view);

    }

    /**
     * onBindViewHolder est appelée par le RecyclerView lorsqu’il doit afficher un élément à une position donnée
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((UtilisateurViewHolder) holder).mContentView.setText(utilisateurs.get(position).getNom());

        //Couleur alternée

        if(position % 2 == 0){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mainActivity.getContext(), R.color.colorRecyclerView1));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UtilisateurFragment articleFrag = (UtilisateurFragment) mainActivity.getFragmentManager().findFragmentById(R.id.article_fragment);
                if(articleFrag != null){
                    articleFrag.setUtilisateurs(utilisateurs);
                    articleFrag.updateArticleView(position);
                }
                else {
                    UtilisateurFragment newFragment = new UtilisateurFragment();
                    Bundle args = new Bundle();
                    newFragment.setUtilisateurs(utilisateurs);
                    args.putInt(UtilisateurFragment.ARG_POSITION,position);
                    newFragment.setArguments(args);
                    FragmentTransaction transaction = mainActivity.getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container,newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return utilisateurs.size();
    }

    public class UtilisateurViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mContentView;

        public UtilisateurViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
        }

    }

}



