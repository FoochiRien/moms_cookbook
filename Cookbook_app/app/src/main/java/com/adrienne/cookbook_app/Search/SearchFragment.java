package com.adrienne.cookbook_app.Search;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrienne.cookbook_app.R;
import com.adrienne.cookbook_app.Search.EdamamAPI.EdamamResult.EdamamAPI;
import com.adrienne.cookbook_app.Search.EdamamAPI.EdamamResult.EdamamInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchFragment extends Fragment {

    public ApiRecipeRecyclerViewAdapter mApiRecipeRecyclerViewAdapter;
    // query for api term
    String query = "chocolate";
    //Information for Api
    public static final String EDAMAM_SEARCH_URL = "https://api.edamam.com/";
    public static final String EDAMAM_API_KEY = "c8f3d9dbc5a7c4cdd4c7ce39db3848a1";
    public static final String TAG = "edamamapi ------";
    public static final String APP_ID = "6b2b7746";



    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }


    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.api_searchresult_recyclerview);
        mApiRecipeRecyclerViewAdapter = new ApiRecipeRecyclerViewAdapter(new ArrayList<ApiRecipe>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(mApiRecipeRecyclerViewAdapter);

        Log.d(TAG, "onViewCreated: " + mApiRecipeRecyclerViewAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EDAMAM_SEARCH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EdamamInterface edamanService = retrofit.create(EdamamInterface.class);

        final Call<EdamamAPI> edamamAPICall = edamanService.getHits(query, APP_ID, EDAMAM_API_KEY);
        Log.d(TAG, "onViewCreated: " + edamamAPICall.request().toString());

        edamamAPICall.enqueue(new Callback<EdamamAPI>() {
            @Override
            public void onResponse(Call<EdamamAPI> call, Response<EdamamAPI> response) {
                EdamamAPI edamamList = response.body();
                List<ApiRecipe> list = new ArrayList<ApiRecipe>();
                if(edamamList == null){
                    Log.d(TAG, "onResponse: null");
                } else {
                    Log.d(TAG, "onResponse: " + edamamList);

                    for(int i = 0; i < edamamList.getHits().size()-1; i++){
                        list.add(new ApiRecipe(edamamList.getHits().get(i).getRecipe().getImage(),
                                edamamList.getHits().get(i).getRecipe().getLabel(),
                                edamamList.getHits().get(i).getRecipe().getSource(),
                                edamamList.getHits().get(i).getRecipe().getYield()));

                    }
                    mApiRecipeRecyclerViewAdapter.swapData(list);

                }
                Log.d(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<EdamamAPI> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
