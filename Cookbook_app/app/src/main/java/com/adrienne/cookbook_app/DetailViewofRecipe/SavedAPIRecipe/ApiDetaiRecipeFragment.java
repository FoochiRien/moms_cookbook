package com.adrienne.cookbook_app.DetailViewofRecipe.SavedAPIRecipe;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrienne.cookbook_app.DetailViewofRecipe.ManualEnteredRecipe.ManualEnteredRecipeFragment;
import com.adrienne.cookbook_app.My_cookbook.MyRecipe;
import com.adrienne.cookbook_app.My_cookbook.db_cookbook.RecipeSQLiteOpenHelper;
import com.adrienne.cookbook_app.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ApiDetaiRecipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ApiDetaiRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApiDetaiRecipeFragment extends Fragment {

    //Display a recipe that was saved from the API search

    public static final String TAG = "API COOKBOOK FRAG";

    public static final String RECIPE_ID = "recipeId";

    TextView mCBApiTitle, mCBApiWebsiteSource, mCBApiUrl, mCBApiCategory;
    ImageView mCBApiImage, mEdamamSavedRecipe;

    long mRecipeId;

    private OnFragmentInteractionListener mApiCookbookListener;

    public ApiDetaiRecipeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ApiDetaiRecipeFragment newInstance(Bundle bundle) {
        ApiDetaiRecipeFragment fragment = new ApiDetaiRecipeFragment();
        Bundle args = new Bundle();
        args.putLong(RECIPE_ID, bundle.getLong(ManualEnteredRecipeFragment.RECIPE_ID));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipeId = getArguments().getLong(RECIPE_ID);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_api_detai_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Log.d(TAG, "onViewCreated:  recipeId" + mRecipeId);
        MyRecipe myRecipes = RecipeSQLiteOpenHelper.getInstance(getContext()).getRecipeDisplay(mRecipeId);
        //sets the variables from the database
        String title = myRecipes.getTitle();
        String source = myRecipes.getSourceTitle();
        final String url = myRecipes.getSourceUrl();
        String image = myRecipes.getImage();
        String category = myRecipes.getCategory();
        //view from the xml
        mCBApiTitle = (TextView) view.findViewById(R.id.recipefromapi_detail_title);
        mCBApiUrl = (TextView) view.findViewById(R.id.recipefromapi_detail_url);
        mCBApiWebsiteSource = (TextView) view.findViewById(R.id.recipefromapi_detail_source);
        mCBApiImage = (ImageView) view.findViewById(R.id.recipefromapi_detail_image);
        mEdamamSavedRecipe = (ImageView) view.findViewById(R.id.edamam_saved_recipe);
        mCBApiCategory = (TextView) view.findViewById(R.id.recipefromapi_categories);

        //user is able to click on the hyperlink which takes the user to a web view of the recipe
        //inside the app
        mCBApiUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent displayUrl = new Intent(getContext(), WebActivity.class);
                displayUrl.putExtra("website", url);
                startActivity(displayUrl);
            }
        });
        //sets view for the recipe
        mCBApiTitle.setText(title);
        mCBApiWebsiteSource.setText(source);
        Picasso.with(mCBApiImage.getContext()).load(image).fit().into(mCBApiImage);
        mCBApiCategory.setText(category);
        //per requirment the logo for Edamam and a link to their webpage
        mEdamamSavedRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://developer.edamam.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


    }


    public void onButtonPressed(Uri uri) {
        if (mApiCookbookListener != null) {
            mApiCookbookListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mApiCookbookListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mApiCookbookListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
