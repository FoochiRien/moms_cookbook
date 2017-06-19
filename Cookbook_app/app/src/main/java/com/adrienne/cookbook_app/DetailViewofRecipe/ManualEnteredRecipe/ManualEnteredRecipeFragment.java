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

    public static final String TAG = "ManualEnterRecFrag";

    //Keys for Cookbook Intent

    public static final String RECIPE_ID = "recipeId";

    private OnFragmentInteractionListener mManualEnterListener;
    private List<MyRecipe> mRecipeList;
    private List<String> recipeIngredients;

    TextView mTitleView, mNotesView, mCategoryView, mServingView, mCookTimeView;
    ImageView mBookmarked;

    DisplayRecyclerViewAdapter mDisplayRecyclerViewAdapter;


    private RecipeSQLiteOpenHelper mDBHelper;


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


        mDBHelper = RecipeSQLiteOpenHelper.getInstance(getContext());

        Bundle bundle = getArguments();
        final long recipeId = bundle.getLong(RECIPE_ID);
        //----retrieve data from the first table
        MyRecipe myRecipes = RecipeSQLiteOpenHelper.getInstance(getContext()).getRecipeDisplay(recipeId);
        Log.d(TAG, "onCreateView: " + myRecipes);

        String title = myRecipes.getTitle();
        String notes = myRecipes.getNotes();
        String category = myRecipes.getCategory();
        float serving2 = myRecipes.getServings();
        String serving = serving2 + " servings";
        float cooktime2 = myRecipes.getCookTime();
        String cooktime = cooktime2 + "";
        int bookmark = myRecipes.getBookmarked();

        mTitleView = (TextView) view.findViewById(R.id.recipe_title);
        mNotesView = (TextView) view.findViewById(R.id.recipe_notes);
        mCategoryView = (TextView) view.findViewById(R.id.recipe_category);
        mServingView = (TextView) view.findViewById(R.id.recipe_servings);
        mCookTimeView = (TextView) view.findViewById(R.id.recipe_cooktime);
        mBookmarked = (ImageView) view.findViewById(R.id.menu_bookmark);


        mTitleView.setText(title);
        mNotesView.setText(notes);
        mCategoryView.setText(category);
        mServingView.setText(serving);
        mCookTimeView.setText(cooktime);

        Log.d(TAG, "onCreateView: RECIPE " + title);

        if(bookmark == 0){

        } else if (bookmark == 1){
            mBookmarked.setImageResource(R.drawable.ic_bookmark_recipe);
        }


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

