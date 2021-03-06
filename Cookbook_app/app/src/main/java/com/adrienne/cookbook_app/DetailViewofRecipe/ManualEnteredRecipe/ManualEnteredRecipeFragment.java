package com.adrienne.cookbook_app.DetailViewofRecipe.ManualEnteredRecipe;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrienne.cookbook_app.My_cookbook.MyRecipe;
import com.adrienne.cookbook_app.My_cookbook.db_cookbook.RecipeSQLiteOpenHelper;
import com.adrienne.cookbook_app.R;

import java.util.List;



public class ManualEnteredRecipeFragment extends Fragment {

    //Display a recipe that was manually entered by the user

    public static final String TAG = "ManualEnterRecFrag";

    //Keys for Cookbook Intent

    public static final String RECIPE_ID = "recipeId";

    private OnFragmentInteractionListener mManualEnterListener;

    TextView mTitleView, mNotesView, mCategoryView, mServingView, mCookTimeView, mDirections;
    ImageView mBookmarked;

    DisplayRecyclerViewAdapter mDisplayRecyclerViewAdapter;



    public ManualEnteredRecipeFragment() {
        // Required empty public constructor
    }

    public static ManualEnteredRecipeFragment newInstance(Bundle bundle) {
        ManualEnteredRecipeFragment manualEnteredRecipeFragment = new ManualEnteredRecipeFragment();

        manualEnteredRecipeFragment.setArguments(bundle);
        return manualEnteredRecipeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


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

       /*Data sent from the Recipe Detail Activity with the recipe id from the database*/

        Bundle bundle = getArguments();
        final long recipeId = bundle.getLong(RECIPE_ID);
        //----retrieve data from the first table
        MyRecipe myRecipes = RecipeSQLiteOpenHelper.getInstance(getContext()).getRecipeDisplay(recipeId);
        Log.d(TAG, "onCreateView: " + myRecipes);
        // the details of the recipe
        String title = myRecipes.getTitle();
        String notes = myRecipes.getNotes();
        String category = myRecipes.getCategory();
        float serving2 = myRecipes.getServings();
        String serving = serving2 + " servings";
        float cooktime2 = myRecipes.getCookTime();
        String cooktime = cooktime2 + "";
        int bookmark = myRecipes.getBookmarked();
        String directions = myRecipes.getDirections();
        //set the view for the recipe
        mTitleView = (TextView) view.findViewById(R.id.recipe_title);
        mNotesView = (TextView) view.findViewById(R.id.recipe_display_notes);
        mCategoryView = (TextView) view.findViewById(R.id.recipe_display_category);
        mServingView = (TextView) view.findViewById(R.id.recipe_servings);
        mCookTimeView = (TextView) view.findViewById(R.id.recipe_cooktime);
        mBookmarked = (ImageView) view.findViewById(R.id.menu_bookmark);
        mDirections = (TextView) view.findViewById(R.id.recipe_directions);

        //setting the text for the recipe views.
        mTitleView.setText(title);
        mNotesView.setText(notes);
        mCategoryView.setText(category);
        mServingView.setText(serving);
        mCookTimeView.setText(cooktime);
        mDirections.setText(directions);

        Log.d(TAG, "onCreateView: RECIPE " + title);
        //if an item is not bookmarked - 0 it will display the default image
        //if the item has been bookmarked it will display the colored bookmark.
        if(bookmark == 0){

        } else if (bookmark == 1){
            mBookmarked.setImageResource(R.drawable.ic_bookmark_recipe);
        }
        // view for the ingredients

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.display_ingredients_list);
        mDisplayRecyclerViewAdapter = new DisplayRecyclerViewAdapter(myRecipes.getIngredients());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mDisplayRecyclerViewAdapter);
    }

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

        void onFragmentInteraction();
    }

}


