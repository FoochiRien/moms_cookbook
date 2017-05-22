package com.adrienne.cookbook_app.RecipeDetail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrienne.cookbook_app.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import static com.adrienne.cookbook_app.MainActivity.KEY;
import static com.adrienne.cookbook_app.MainActivity.KEY1;
import static com.adrienne.cookbook_app.MainActivity.KEY2;
import static com.adrienne.cookbook_app.MainActivity.KEY3;
import static com.adrienne.cookbook_app.MainActivity.KEY4;


public class RecipeFragment extends Fragment {

    public static final String TAG = "RecipeFragment ";
    private OnFragmentInteractionListener mListener;

    private String mApiRecipeTitle, mApiRecipeImage, mApiRecipeWebsite, mApiRecipeUrl,
    mApiRecipeServing;


//    public RecipeFragment() {
//        // Required empty public constructor
//    }

    public static RecipeFragment newInstance(Bundle bundle) {
        RecipeFragment recipeFragment = new RecipeFragment();

        recipeFragment.setArguments(bundle);
        return recipeFragment;
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
        View recipeView = inflater.inflate(R.layout.api_recipe_detail_layout, container, false);
        TextView apiRecipeTitleView = (TextView) recipeView.findViewById(R.id.api_recipe_detail_title);
        ImageView apiRecipeView = (ImageView) recipeView.findViewById(R.id.api_recipe_detail_image);
        TextView apiRecipeSourceView = (TextView) recipeView.findViewById(R.id.api_recipe_detail_source);

        Bundle bundle = getArguments();
        mApiRecipeTitle = bundle.getString(KEY);
        mApiRecipeImage = bundle.getString(KEY1);
        mApiRecipeWebsite = bundle.getString(KEY2);
        mApiRecipeServing = bundle.getString(KEY3);
        mApiRecipeUrl = bundle.getString(KEY4);

        Log.d(TAG, "onCreateView: RECIPE FRAGMENT " + mApiRecipeTitle);

        apiRecipeTitleView.setText(mApiRecipeTitle);
        apiRecipeSourceView.setText(mApiRecipeWebsite);
        Picasso.with(getContext()).load(mApiRecipeImage).into(apiRecipeView);


        // Inflate the layout for this fragment
        return recipeView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
