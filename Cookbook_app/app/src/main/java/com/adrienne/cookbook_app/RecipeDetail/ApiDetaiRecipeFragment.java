package com.adrienne.cookbook_app.RecipeDetail;

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
import android.widget.Toast;

import com.adrienne.cookbook_app.MainActivity;
import com.adrienne.cookbook_app.My_cookbook.MyRecipe;
import com.adrienne.cookbook_app.My_cookbook.db_cookbook.RecipeSQLiteOpenHelper;
import com.adrienne.cookbook_app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ApiDetaiRecipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ApiDetaiRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApiDetaiRecipeFragment extends Fragment {

    //We spent time trying to figure out why this one isnt working when the manual does and
    //it uses the same method calls, but at this point I'm not sure why. It throws an error message
    //which is actually a toast and it doesn't crash. The user isn't able to view a detail of the
    //recipe directly they would have to do another search.

    public static final String TAG = "API COOKBOOK FRAG";

    public static final String RECIPE_ID = "recipeId";

    TextView mCBApiTitle, mCBApiWebsiteSource, mCBApiUrl;
    ImageView mCBApiImage;
    ImageView mBookmark, mDelete, mHome;

    RecipeSQLiteOpenHelper mDBHelper;
    List<MyRecipe> mRecipeList;

    private OnFragmentInteractionListener mApiCookbookListener;

    public ApiDetaiRecipeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ApiDetaiRecipeFragment newInstance(Bundle bundle) {
        ApiDetaiRecipeFragment fragment = new ApiDetaiRecipeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_api_detai_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        final long recipeId = bundle.getLong(RECIPE_ID);
        Log.d(TAG, "onViewCreated:  recipeId" + recipeId);
        List<MyRecipe> myRecipes = RecipeSQLiteOpenHelper.getInstance(getContext()).getRecipeDisplay(recipeId);
//        mDBHelper = RecipeSQLiteOpenHelper.getInstance(getContext());
//        mRecipeList = mDBHelper.getRecipeDisplay(recipeId);
//        Log.d(TAG, "onViewCreated: title " + );
        if(mRecipeList!=null && mRecipeList.size()>0) {
            String title = myRecipes.get(0).getTitle();
            String source = myRecipes.get(0).getSourceTitle();
            final String url = myRecipes.get(0).getSourceUrl();
            String image = myRecipes.get(0).getImage();

            mCBApiTitle = (TextView) view.findViewById(R.id.recipefromapi_detail_title);
            mCBApiUrl = (TextView) view.findViewById(R.id.recipefromapi_detail_url);
            mCBApiWebsiteSource = (TextView) view.findViewById(R.id.recipefromapi_detail_source);
            mCBApiImage = (ImageView) view.findViewById(R.id.recipefromapi_detail_image);



            mCBApiUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent displayUrl = new Intent(getContext(), WebActivity.class);
                    displayUrl.putExtra("website", url);
                    startActivity(displayUrl);
                }
            });

            mCBApiTitle.setText(title);
            mCBApiWebsiteSource.setText(source);
            Picasso.with(mCBApiImage.getContext()).load(image).fit().into(mCBApiImage);
        } else {
            Toast.makeText(getContext(), "Sorry about that. Return to home.", Toast.LENGTH_SHORT).show();
        }

        mBookmark = (ImageView) view.findViewById(R.id.add_to_bookmark);
        mDelete = (ImageView) view.findViewById(R.id.delete_recipe);
        mHome = (ImageView) view.findViewById(R.id.to_home_button);

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
