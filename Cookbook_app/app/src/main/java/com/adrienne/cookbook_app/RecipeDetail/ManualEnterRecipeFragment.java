package com.adrienne.cookbook_app.RecipeDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adrienne.cookbook_app.MainActivity;
import com.adrienne.cookbook_app.My_cookbook.CookbookRecyclerViewAdapter;
import com.adrienne.cookbook_app.My_cookbook.Directions;
import com.adrienne.cookbook_app.My_cookbook.DirectionsRecyclerViewAdapter;
import com.adrienne.cookbook_app.My_cookbook.Ingredients;
import com.adrienne.cookbook_app.My_cookbook.IngredientsRecyclerViewAdapter;
import com.adrienne.cookbook_app.My_cookbook.MyRecipe;
import com.adrienne.cookbook_app.My_cookbook.db_cookbook.RecipeSQLiteOpenHelper;
import com.adrienne.cookbook_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.adrienne.cookbook_app.MainActivity.KEY;
import static com.adrienne.cookbook_app.MainActivity.KEY1;
import static com.adrienne.cookbook_app.MainActivity.KEY2;
import static com.adrienne.cookbook_app.MainActivity.KEY3;
import static com.adrienne.cookbook_app.MainActivity.KEY4;


public class ManualEnterRecipeFragment extends Fragment {

    public static final String TAG = "ManualEnterRecFrag";

    //Keys for Cookbook Intent

    public static final String RECIPE_ID = "recipeId";

    private OnFragmentInteractionListener mManualEnterListener;
    private List<MyRecipe> mRecipeList;
    private List<Ingredients> recipeIngredients;
    private List<Directions> recipeDirections;

    TextView mTitleView, mNotesView, mCategoryView, mServingView, mCookTimeView;
    ImageView mBookmark, mDelete, mHome;

    IngredientsRecyclerViewAdapter mIngredientsAdapter;
    DirectionsRecyclerViewAdapter mDirectionsAdapter;

    private RecipeSQLiteOpenHelper mDBHelper;


    public ManualEnterRecipeFragment() {
        // Required empty public constructor
    }

    public static ManualEnterRecipeFragment newInstance(Bundle bundle) {
        ManualEnterRecipeFragment manualEnterRecipeFragment = new ManualEnterRecipeFragment();

        manualEnterRecipeFragment.setArguments(bundle);
        return manualEnterRecipeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

   // Bundle[{ApiRecipeKey-website=Serious Eats, ApiRecipeKey-serving=4.0, ApiRecipeKey-image=https://www.edamam.com/web-img/a07/a07d1a6832f5b1051578653ca8c8e12d.jpg, ApiRecipeKey-title=Rhubarb Juice, ApiRecipeKey-url=http://www.seriouseats.com/recipes/2013/05/tart-unsweetened-homemade-rhubarb-juice-recipe.html}]

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View recipeView = inflater.inflate(R.layout.fragment_recipe, container, false);

        // Inflate the layout for this fragment
        return recipeView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDBHelper = RecipeSQLiteOpenHelper.getInstance(getContext());

        Bundle bundle = getArguments();
        final long recipeId = bundle.getLong(RECIPE_ID);
        //----retrieve data from the first table
        List<MyRecipe> myRecipes = RecipeSQLiteOpenHelper.getInstance(getContext()).getRecipeDisplay(recipeId);
        Log.d(TAG, "onCreateView: " + myRecipes);
        mRecipeList = mDBHelper.getRecipeDisplay(recipeId);

        String title = myRecipes.get(0).getTitle();
        String notes = myRecipes.get(0).getNotes();
        String category = myRecipes.get(0).getCategory();
        float serving2 = myRecipes.get(0).getServings();
        String serving = serving2 + " servings";
        float cooktime2 = myRecipes.get(0).getCookTime();
        String cooktime = cooktime2 + "";

        mTitleView = (TextView) view.findViewById(R.id.recipe_title);
        mNotesView = (TextView) view.findViewById(R.id.recipe_notes);
        mCategoryView = (TextView) view.findViewById(R.id.recipe_category);
        mServingView = (TextView) view.findViewById(R.id.recipe_servings);
        mCookTimeView = (TextView) view.findViewById(R.id.recipe_cooktime);

        mBookmark = (ImageView) view.findViewById(R.id.add_to_bookmark);
        mDelete = (ImageView) view.findViewById(R.id.delete_recipe);
        mHome = (ImageView) view.findViewById(R.id.to_home_button);

        mTitleView.setText(title);
        mNotesView.setText(notes);
        mCategoryView.setText(category);
        mServingView.setText(serving);
        mCookTimeView.setText(cooktime);

        Log.d(TAG, "onCreateView: RECIPE " + title);

        List<Ingredients> ingredients = RecipeSQLiteOpenHelper.getInstance(getContext()).getAllIngredients(recipeId);

//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recipe_directions_list);
//        mDirectionsAdapter = new DirectionsRecyclerViewAdapter(new ArrayList<Directions>());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
//        recyclerView.setAdapter(mDirectionsAdapter);
//
//        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R.id.recipe_ingredients_list);
//        mIngredientsAdapter = new IngredientsRecyclerViewAdapter(new ArrayList<Ingredients>());
//        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
//        recyclerView2.setAdapter(mIngredientsAdapter);

        mBookmark.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int bookmarked = mDBHelper.searchBookmark(recipeId);
                if (bookmarked == 1) {
                    Toast.makeText(getContext(), "Removing from your Cookbook.",
                            Toast.LENGTH_LONG).show();

                } else {
                    mDBHelper.addBookmark(recipeId);
                    Toast.makeText(getContext(), "Added to your cookbook.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });



        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDBHelper.removeRecipeFromCookbook(recipeId);
                Toast.makeText(getContext(), "BYE, BYE, BYE. It's gone from you cookbook",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), MainActivity.class));

            }
        });


        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mManualEnterListener != null) {
            mManualEnterListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mManualEnterListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SearchOnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mManualEnterListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
