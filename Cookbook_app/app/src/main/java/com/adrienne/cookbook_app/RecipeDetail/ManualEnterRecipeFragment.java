package com.adrienne.cookbook_app.RecipeDetail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.adrienne.cookbook_app.My_cookbook.CookbookRecyclerViewAdapter;
import com.adrienne.cookbook_app.My_cookbook.Directions;
import com.adrienne.cookbook_app.My_cookbook.DirectionsRecyclerViewAdapter;
import com.adrienne.cookbook_app.My_cookbook.Ingredients;
import com.adrienne.cookbook_app.My_cookbook.IngredientsRecyclerViewAdapter;
import com.adrienne.cookbook_app.My_cookbook.MyRecipe;
import com.adrienne.cookbook_app.My_cookbook.db_cookbook.RecipeSQLiteOpenHelper;
import com.adrienne.cookbook_app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.adrienne.cookbook_app.MainActivity.KEY;
import static com.adrienne.cookbook_app.MainActivity.KEY1;
import static com.adrienne.cookbook_app.MainActivity.KEY2;
import static com.adrienne.cookbook_app.MainActivity.KEY3;
import static com.adrienne.cookbook_app.MainActivity.KEY4;


public class ManualEnterRecipeFragment extends Fragment {

    public static final String TAG = "ManualEnterRecFrag";

    //Keys for Cookbook Intent
    public static final String View_To_Show = "view-to-show";
    public static final String RECIPE_ID = "recipeId";

    private OnFragmentInteractionListener mManualEnterListener;

    private List<Ingredients> recipeIngredients;
    private List<Directions> recipeDirections;

    TextView mTitleView, mNotesView, mCategoryView, mServingView, mCookTimeView;

    CookbookRecyclerViewAdapter mCookbookAdapter;
    DirectionsRecyclerViewAdapter mDirectionsAdapter;
    IngredientsRecyclerViewAdapter mIngredientsAdapter;


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
        View recipeView = inflater.inflate(R.layout.recipe_layout, container, false);

        Bundle bundle = getArguments();
        long mRecipeId = bundle.getInt(RECIPE_ID);
        //----retrieve data from the first table
        List<MyRecipe> myRecipes = RecipeSQLiteOpenHelper.getInstance(getContext()).getRecipe(mRecipeId);
        Log.d(TAG, "onCreateView: " + myRecipes);

        String title = myRecipes.get(0).getTitle();
        String notes = myRecipes.get(0).getNotes();
        String category = myRecipes.get(0).getCategory();
        float serving2 = myRecipes.get(0).getServings();
        String serving = serving2 + "servings";
        float cooktime2 = myRecipes.get(0).getCookTime();
        String cooktime = cooktime2 + "";

        mTitleView = (TextView) recipeView.findViewById(R.id.recipe_title);
        mNotesView = (TextView) recipeView.findViewById(R.id.recipe_notes);
        mCategoryView = (TextView) recipeView.findViewById(R.id.recipe_category);
        mServingView = (TextView) recipeView.findViewById(R.id.recipe_servings);
        mCookTimeView = (TextView) recipeView.findViewById(R.id.recipe_cooktime);

        mTitleView.setText(title);
        mNotesView.setText(notes);
        mCategoryView.setText(category);
        mServingView.setText(serving);
        mCookTimeView.setText(cooktime);

        Log.d(TAG, "onCreateView: RECIPE " + title);

        List<Ingredients> ingredients = RecipeSQLiteOpenHelper.getInstance(getContext()).getAllIngredients(mRecipeId);



        // Inflate the layout for this fragment
        return recipeView;
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
