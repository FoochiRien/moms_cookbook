package com.adrienne.cookbook_app.RecipeDetail;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.adrienne.cookbook_app.MainActivity;
import com.adrienne.cookbook_app.My_cookbook.db_cookbook.RecipeSQLiteOpenHelper;
import com.adrienne.cookbook_app.R;
import com.adrienne.cookbook_app.Search.SearchFragment;

public class RecipeDetailActivity extends AppCompatActivity implements
        ApiDetaiRecipeFragment.OnFragmentInteractionListener, PhotoDetailRecipeFragment.OnFragmentInteractionListener,
        ManualEnterRecipeFragment.OnFragmentInteractionListener {

    public static final String TAG = "recipe activity";

    public RecipeSQLiteOpenHelper mDBHelper;

    //Keys for Cookbook Intent
    public static final String View_To_Show = "view-to-show";
    public static final String RECIPE_ID = "recipeId";

   ImageView mBookmark, mDelete, mHome;
    long recipeId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mDBHelper = RecipeSQLiteOpenHelper.getInstance(this);



        Intent displayRecipeIntent = getIntent();
        final String viewToShow = displayRecipeIntent.getStringExtra(View_To_Show);
        recipeId = displayRecipeIntent.getLongExtra(RECIPE_ID, recipeId);

        Bundle bundle = new Bundle();
        bundle.putLong(RECIPE_ID, recipeId);

        if(viewToShow != null) {

            switch (viewToShow) {
                case "api":
                    Fragment apiDetailRecipeFrag = ApiDetaiRecipeFragment.newInstance(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_api_recipe_cookbook, apiDetailRecipeFrag).commit();
                    return ;
                case "manual":
                    Fragment manualEnterFrag = ManualEnterRecipeFragment.newInstance(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_manualrecipe_cookbook, manualEnterFrag).commit();
                    return ;
                case "photo":
                    Fragment photodetailFrag = PhotoDetailRecipeFragment.newInstance(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_photodetail_cookbook, photodetailFrag).commit();
                    return;
                default:
                    return ;
            }
        } else {
            Toast.makeText(this, "There was an error loading your recipe. Please try again",
                    Toast.LENGTH_SHORT).show();
        }


        mBookmark = (ImageView) findViewById(R.id.add_to_bookmark);
        mBookmark.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int bookmarked = mDBHelper.searchBookmark(recipeId);
                if (bookmarked == 1) {
                    Toast.makeText(RecipeDetailActivity.this, "Removing from your Cookbook.",
                            Toast.LENGTH_LONG).show();

                } else {
                    mDBHelper.addBookmark(recipeId);
                    Toast.makeText(RecipeDetailActivity.this, "Added to your cookbook.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });


        mDelete = (ImageView) findViewById(R.id.delete_recipe);
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDBHelper.removeRecipeFromCookbook(recipeId);
                Toast.makeText(RecipeDetailActivity.this, "BYE, BYE, BYE. It's gone from you cookbook",
                        Toast.LENGTH_SHORT).show();

            }
        });

        mHome = (ImageView) findViewById(R.id.to_home_button);
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecipeDetailActivity.this, MainActivity.class));
            }
        });

    }
    @Override
    public void onFragmentInteraction() {

    }
}
