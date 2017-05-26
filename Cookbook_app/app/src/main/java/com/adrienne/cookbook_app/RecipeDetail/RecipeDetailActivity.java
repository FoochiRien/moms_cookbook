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

        //This intent comes from the cookbook fragment. The only holds the view and the recipeid.
        //the view to show inflates the proper fragment to display the content.
        Intent displayRecipeIntent = getIntent();
        final String viewToShow = displayRecipeIntent.getStringExtra(View_To_Show);
        recipeId = displayRecipeIntent.getLongExtra(RECIPE_ID, recipeId);

        Bundle bundle = new Bundle();
        bundle.putLong(ManualEnterRecipeFragment.RECIPE_ID, recipeId);

        /*Due to how the information is entered I decided to do multiple layouts to be reflective
        * of the information provided. */

        if(viewToShow != null) {

            switch (viewToShow) {
                case "api":
                    Fragment apiDetailRecipeFrag = ApiDetaiRecipeFragment.newInstance(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.apirecipe_detail_screen, apiDetailRecipeFrag).commit();
                    //While the inflating of the fragment works, the passing of the recipeid
                    //isnt working properly, but i didnt have time to figure it out. I'm going to
                    //continue to work on the project, so it will be corrected eventually.

                    return ;
                case "manual":
                    Fragment manualEnterFrag = ManualEnterRecipeFragment.newInstance(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.apirecipe_detail_screen, manualEnterFrag).commit();
                    return ;
                /*As I wasn't able to finish the app as I planned  and I do intent to finish it the
                way I planned, I greyed out the section instead of deleting. Didn't want to lose the
                 work. this section out. */

//                case "photo":
//                    Fragment photodetailFrag = PhotoDetailRecipeFragment.newInstance(bundle);
//                    getSupportFragmentManager().beginTransaction()
//                            .add(R.id.fragment_photodetail_cookbook, photodetailFrag).commit();
//                    return;
                default:
                    return ;
            }
        } else {
            //Instead of the app crashing if the intent doesnt work and error message is displayed.
            //which comes across as a toast.

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
