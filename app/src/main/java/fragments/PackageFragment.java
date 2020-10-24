/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import android.widget.ImageView;
import android.widget.Toast;

import com.example.olivine.cholodesh.R;

import adapters.PackageAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import callbacks.ProvideCallback;
import listeners.FragmentInteractionListener;
import model.Package;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PackageFragment extends Fragment {
    private FragmentInteractionListener mListener;
    int hitCount=0;
    long time=0;
    private ProvideCallback provideCallback;
    // Componets initialization
    @BindView(R.id.packages) GridView packages;
    @BindView(R.id.placeHolderImage) ImageView placeHolderImage;
    public PackageFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PackageFragment newInstance() {
        PackageFragment fragment = new PackageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void loadPackages() {
        provideCallback=new ProvideCallback(getActivity());
        provideCallback.getAllPackages().enqueue(new Callback<Package[]>() {
            @Override
            public void onResponse(Call<Package[]> call, Response<Package[]> response) {
                Log.e("Package Url",call.request().url().toString());
                if(response.body()==null){
                    placeHolderImage.setVisibility(View.VISIBLE);
                    return;
                }
                placeHolderImage.setVisibility(View.GONE);
                PackageAdapter packageAdapter=new PackageAdapter(getActivity(),response.body());
                packages.setAdapter(packageAdapter);
            }

            @Override
            public void onFailure(Call<Package[]> call, Throwable t) {
                placeHolderImage.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Offers");
        View view=inflater.inflate(R.layout.activity_package,container,false);
        ButterKnife.bind(this,view);
        loadPackages();
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
