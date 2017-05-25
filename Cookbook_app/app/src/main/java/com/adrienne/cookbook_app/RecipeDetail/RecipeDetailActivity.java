package com.adrienne.cookbook_app.RecipeDetail;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    public static final String View_To_Show = "View";
    public static final String RECIPE_ID = "Recipe_Id";


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
        bundle.putLong(ManualEnterRecipeFragment.RECIPE_ID, recipeId);

        if(viewToShow != null) {

            switch (viewToShow) {
                case "api":
                    Fragment apiDetailRecipeFrag = ApiDetaiRecipeFragment.newInstance(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.apirecipe_detail_screen, apiDetailRecipeFrag).commit();
                    return ;
                case "manual":
                    Fragment manualEnterFrag = ManualEnterRecipeFragment.newInstance(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.apirecipe_detail_screen, manualEnterFrag).commit();
                    return ;
//                case "photo":
//                    Fragment photodetailFrag = PhotoDetailRecipeFragment.newInstance(bundle);
//                    getSupportFragmentManager().beginTransaction()
//                            .add(R.id.fragment_photodetail_cookbook, photodetailFrag).commit();
//                    return;
                default:
                    return ;
            }
        } else {
            Log.d(TAG, "onCreate: viewtoshow " + viewToShow);
            Toast.makeText(this, "There was an error loading your recipe. Please try again",
                    Toast.LENGTH_SHORT).show();
            Intent movetohome = new Intent(RecipeDetailActivity.this, MainActivity.class);
            startActivity(movetohome);
        }

    }
    @Override
    public void onFragmentInteraction() {

    }
}
