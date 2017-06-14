package com.adrienne.cookbook_app.DetailViewofRecipe;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.adrienne.cookbook_app.APISearch.SearchFragment;
import com.adrienne.cookbook_app.DetailViewofRecipe.ManualEnteredRecipe.ManualEnterRecipeFragment;
import com.adrienne.cookbook_app.DetailViewofRecipe.SavedAPIRecipe.ApiDetaiRecipeFragment;
import com.adrienne.cookbook_app.MainActivity;
import com.adrienne.cookbook_app.My_cookbook.CookbookRecyclerViewAdapter;
import com.adrienne.cookbook_app.My_cookbook.db_cookbook.RecipeSQLiteOpenHelper;
import com.adrienne.cookbook_app.R;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_recipe_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mom's Cookbook");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        //This intent comes from the cookbook fragment. This only holds the view and the recipeid.
        //the view to show inflates the proper fragment to display the content.
        Intent displayRecipeIntent = getIntent();
        final String viewToShow = displayRecipeIntent.getStringExtra(View_To_Show);
        recipeId = displayRecipeIntent.getLongExtra(RECIPE_ID, recipeId);

        Bundle bundle = new Bundle();
        bundle.putLong(ManualEnterRecipeFragment.RECIPE_ID, recipeId);

        /*Due to how the information is entered I decided to do multiple layouts to reflect
        * the information provided. */

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_home:
                startActivity(new Intent(RecipeDetailActivity.this, MainActivity.class));
                finish();
                return true;
            case R.id.menu_delete:
                mDBHelper.removeRecipeFromCookbook(recipeId);
                Toast.makeText(RecipeDetailActivity.this, "BYE, BYE, BYE. It's gone from you cookbook",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RecipeDetailActivity.this, MainActivity.class));
                return true;
            case R.id.menu_bookmark:
                //checks to see if the item is already bookmarked in the DB
                //if so then removes item if not add item
                //todo add visual image to page for bookmark
                int bookmarked = mDBHelper.searchBookmark(recipeId);
                if (bookmarked == 1) {
                    mDBHelper.removeBookmark(recipeId);
                    item.setIcon(R.drawable.ic_bookmark_item);
                    Toast.makeText(RecipeDetailActivity.this, "Removing from your Cookbook.",
                            Toast.LENGTH_LONG).show();


                } else {
                    mDBHelper.addBookmark(recipeId);
                    item.setIcon(R.drawable.ic_bookmark_recipe);
                    Toast.makeText(RecipeDetailActivity.this, "Added to your cookbook.", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.menu_searchapi:
                //returns user to the Main activity for search capability
                Intent intent = new Intent(RecipeDetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
