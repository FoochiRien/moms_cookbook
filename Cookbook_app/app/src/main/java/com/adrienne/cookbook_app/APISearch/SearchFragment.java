package com.adrienne.cookbook_app.APISearch;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.adrienne.cookbook_app.My_cookbook.MyRecipe;
import com.adrienne.cookbook_app.R;
import com.adrienne.cookbook_app.APISearch.EdamamAPI.EdamamResult.EdamamAPI;
import com.adrienne.cookbook_app.APISearch.EdamamAPI.EdamamResult.EdamamInterface;

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
    private String query, categoryFilter;

    private EditText mApiQuery;
    private ImageView mSearch, mCategoryFilter;

    //Information for Api
    public static final String EDAMAM_SEARCH_URL = "https://api.edamam.com/";
    public static final String EDAMAM_API_KEY = "c8f3d9dbc5a7c4cdd4c7ce39db3848a1";
    public static final String TAG = "edamamapi ------";
    public static final String APP_ID = "6b2b7746";
    public static final String RESULT_AMOUNT = "30";

//    https://api.edamam.com/search?q=dessert,apples&app_id=6b2b7746&app_key=c8f3d9dbc5a7c4cdd4c7ce39db3848a1

    private SearchOnFragmentInteractionListener mListener;

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


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //switch statement for search "popular" default
        //query,breakfast, lunch, dinner
        mSearch = (ImageView) view.findViewById(R.id.api_submit_query);
        mApiQuery = (EditText) view.findViewById(R.id.api_search_query);
        mCategoryFilter = (ImageView) view.findViewById(R.id.api_search_category);

        //Searches the API and returns the results

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.api_searchresult_recyclerview);
        mApiRecipeRecyclerViewAdapter = new ApiRecipeRecyclerViewAdapter(new ArrayList<ApiRecipe>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(mApiRecipeRecyclerViewAdapter);

        Log.d(TAG, "onViewCreated: " + mApiRecipeRecyclerViewAdapter);


        //This is the default search results that appear when the search api search runs
        //therefore i decided to hard code in the term, so that it WILL NOT be changed
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EDAMAM_SEARCH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EdamamInterface edamanService = retrofit.create(EdamamInterface.class);

        final Call<EdamamAPI> edamamAPICall = edamanService.getHits("popular", APP_ID, EDAMAM_API_KEY, RESULT_AMOUNT);
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
                                edamamList.getHits().get(i).getRecipe().getUrl(),
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

        /*The following are search options for the api. Due to the limiations of the api I had to make
        some hard calls about what should be included in the search paramters. I decided to go with
        breakfast, lunch, and dinner not because they are options provided by the service but I figured
        the likely hood of the words being present in titles would be great.  */


        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apiSearchOptions();
                recyclerView.setAdapter(mApiRecipeRecyclerViewAdapter);
            }
        });

        mCategoryFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                String choiceArray[] = {"breakfast", "lunch", "dinner"};

                builder.setTitle("Sort by: meal category")
                        .setItems(choiceArray, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch(which){
                                    case 0:
                                        categoryFilter = "breakfast";
                                        break;
                                    case 1:
                                        categoryFilter = "lunch";
                                        break;
                                    case 2:
                                        categoryFilter = "dinner";
                                        break;
                                    default:
                                        break;
                                }
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                apiCategoryFilter(categoryFilter);
                recyclerView.setAdapter(mApiRecipeRecyclerViewAdapter);

                return ;
            }

        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchOnFragmentInteractionListener) {
            mListener = (SearchOnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SearchOnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface SearchOnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    //if there is no connection the user will refresh with this code
    public void getApiResults(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EDAMAM_SEARCH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EdamamInterface edamanService = retrofit.create(EdamamInterface.class);

        final Call<EdamamAPI> edamamAPICall = edamanService.getHits("popular", APP_ID, EDAMAM_API_KEY, RESULT_AMOUNT);
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
                                edamamList.getHits().get(i).getRecipe().getUrl(),
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

    public void apiSearchOptions(){
        query = mApiQuery.getText().toString().trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EDAMAM_SEARCH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EdamamInterface edamanService = retrofit.create(EdamamInterface.class);

        final Call<EdamamAPI> edamamAPICall = edamanService.getHits(query, APP_ID, EDAMAM_API_KEY, RESULT_AMOUNT);
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
                                edamamList.getHits().get(i).getRecipe().getUrl(),
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

    public void apiCategoryFilter(String categoryFilter){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EDAMAM_SEARCH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EdamamInterface edamanService = retrofit.create(EdamamInterface.class);

        final Call<EdamamAPI> edamamAPICall = edamanService.getHits(categoryFilter, APP_ID, EDAMAM_API_KEY, RESULT_AMOUNT);
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
                                edamamList.getHits().get(i).getRecipe().getUrl(),
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


}
