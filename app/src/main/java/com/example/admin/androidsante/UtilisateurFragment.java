package com.example.admin.androidsante;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.androidsante.database.modele.User;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by admin on 16/06/2017.
 */

public class UtilisateurFragment extends Fragment {

    final  static  String ARG_UTILISATEUR = "utilisateur";
    final  static  String ARG_POSITION = "position";
    private ViewPager pager;
    private ListeUtilisateurFragment liste = new ListeUtilisateurFragment();
    private int mCurrentPosition = -1;
    private List<User> utilisateurs;

    User utilisateur;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.utilisateur,container,false);

        if(savedInstanceState!= null){
            String flux = savedInstanceState.getString(ARG_UTILISATEUR);
            utilisateur = new Gson().fromJson(flux, User.class);
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
            updateArticleView(mCurrentPosition);
        }

        pager = (ViewPager) view.findViewById(R.id.viewPager);
        pager.setAdapter(new UtilisateurPagerAdapter(getChildFragmentManager()));
        pager.setCurrentItem(mCurrentPosition);

        liste = (ListeUtilisateurFragment) getFragmentManager().findFragmentById(R.id.liste_fragment);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("PAGER", "page : " + position);
                //View v = (View) liste.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("PAGER", "page : " + position);
                //liste.getListView().setItemChecked(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        pager.getAdapter().notifyDataSetChanged();
    }
    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();

        if(args !=null){

            updateArticleView(args.getInt(ARG_POSITION));
        }
        else if (mCurrentPosition != -1) {
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position){
        if(pager != null){
            try {
                utilisateur = utilisateurs.get(position);

                pager.setCurrentItem(position);
                mCurrentPosition = position;
            } catch (Exception e) {
                // Traiter le cas !
                e.printStackTrace();
            }
        }
    }


    ViewPager getPager() {
        return pager;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        String flux = (new Gson()).toJson(utilisateur);
        outState.putString(ARG_UTILISATEUR,flux);
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    public List<User> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<User> utilisateurs) {
        this.utilisateurs = utilisateurs;
        pager.getAdapter().notifyDataSetChanged();
    }

    private class UtilisateurPagerAdapter extends FragmentPagerAdapter {

        public UtilisateurPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            return UtilisateurPagerFragment.newInstance(
                    utilisateurs.get(pos)
            );
        }

        @Override
        public int getCount() {
            if(utilisateurs==null)
                return 0;
            else
            return utilisateurs.size();
        }
    }

}
