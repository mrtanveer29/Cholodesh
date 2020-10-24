/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import android.widget.ImageView;
import android.widget.Toast;

import com.example.olivine.cholodesh.R;

import java.util.List;

import adapters.PackageAdapter;
import adapters.TailormadeListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import listeners.FragmentInteractionListener;
import userDefinder.TailorMade;

public class TailormadeListFragment extends Fragment {
    private FragmentInteractionListener mListener;
    // Componets initialization
    @BindView(R.id.tailormadeList) RecyclerView tailormadeList;
    // Adapters
    TailormadeListAdapter tailormadeListAdapter;
    // data storage
    SharedPreferences sharedPreferences;
    Realm realm;
    public TailormadeListFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TailormadeListFragment newInstance() {
        TailormadeListFragment fragment = new TailormadeListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Tailormade List");
        View view=inflater.inflate(R.layout.activity_tailormade_list,container,false);
        ButterKnife.bind(this,view);
        // Data storage init
        sharedPreferences=getActivity().getSharedPreferences(getString(R.string.preference_file_key),getActivity().MODE_PRIVATE);
        Realm.init(getActivity());
        realm=Realm.getDefaultInstance();
        // Get Data from database
        RealmResults<TailorMade> realm_tailormade=realm.where(TailorMade.class).findAll();

        List<TailorMade> tailormadesToShow=realm.copyFromRealm(realm_tailormade);
        // Adapter init
        tailormadeListAdapter=new TailormadeListAdapter(getActivity(),tailormadesToShow);
        tailormadeList.setAdapter(tailormadeListAdapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            mListener = (FragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
