package com.adrienne.cookbook_app.RecipeDetail;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adrienne.cookbook_app.My_cookbook.db_cookbook.RecipeSQLiteOpenHelper;
import com.adrienne.cookbook_app.R;
import com.adrienne.cookbook_app.Search.SearchFragment;
import com.squareup.picasso.Picasso;

public class RecipeDetailActivity extends AppCompatActivity implements SearchFragment.SearchOnFragmentInteractionListener {

    public static final String TAG = "recipe activity";

    ImageView mApiImage, mFavApiRecipe;
    TextView mApiTitle, mApiWebsiteSource, mApiUrl;
    EditText mCategories;

    String apiCategories;

    public RecipeSQLiteOpenHelper mDBHelper;

    public static final String KEY = "ApiRecipeKey-title";
    public static final String KEY1 = "ApiRecipeKey-image";
    public static final String KEY2 = "ApiRecipeKey-website";
    public static final String KEY3 = "ApiRecipeKey-serving";
    public static final String KEY4 = "ApiRecipeKey-url";
    public static final String View_To_Show = "view-to-show";
    public static final String RECIPE_ID = "recipeId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        mDBHelper = RecipeSQLiteOpenHelper.getInstance(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Recipe");

        //todo switch to chose view for detail of recipe
        Intent displayRecipeIntent = getIntent();
        final String viewToShow = displayRecipeIntent.getStringExtra(View_To_Show);
        final String recipeId = displayRecipeIntent.getStringExtra()

        public String returnView() {

            switch (viewToShow) {
                case "api":
                    return apiRecipeDisplay(RECIPE_ID);
                case "manual":
                    return;
                case "photo":
                    return;
                default:
                    return null;
            }
        }




    }
//todo  android:id="@+id/apirecipe_detail_screen"   android:id="@+id/manuelyentered_recipe_layout"
    //todo add add for ingre and directions in db

    public void apiRecipeDisplay(String recipeId){
        //view for the recipe detail
        mApiImage = (ImageView) findViewById(R.id.api_recipe_detail_image);
        mApiTitle = (TextView) findViewById(R.id.api_recipe_detail_title);
        mApiWebsiteSource = (TextView) findViewById(R.id.api_recipe_detail_source);
        mCategories = (EditText) findViewById(R.id.enter_categories_for_apirecipe);
        mFavApiRecipe = (ImageView) findViewById(R.id.api_recipe_detail_bookmark);
        mApiUrl = (TextView) findViewById(R.id.api_recipe_detail_url);

        // recieves information from the api to be displayed
        Intent displayApiRecipeIntent = getIntent();
        final String apiRecipe = displayApiRecipeIntent.getStringExtra(KEY);
        final String apiImage = displayApiRecipeIntent.getStringExtra(KEY1);
        final String apiWebsite = displayApiRecipeIntent.getStringExtra(KEY2);
        final String apiServing = displayApiRecipeIntent.getStringExtra(KEY3);
        final String apiUrl = displayApiRecipeIntent.getStringExtra(KEY4);

        // information displayed on the main screen
        mApiTitle.setText(apiRecipe);
        Picasso.with(mApiImage.getContext()).load(apiImage).into(mApiImage);
        mApiWebsiteSource.setText(apiWebsite);

        //if the user decides they want to save a recipe by clicking on the favorite button.
        // They can enter categories to go along with the above information.
        mFavApiRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDBHelper.addApiRecipetoCookbook(apiImage,apiRecipe, apiServing,apiCategories,
                        apiWebsite, apiUrl);
                Toast.makeText(RecipeDetailActivity.this, "Congrats. The recipe has been added " +
                                "to your cookbook.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        //due to the limitations of the api the directions are not provided, therefore to view
        //the complete recipe the user will have to view it on the web.
        apiCategories = mCategories.getText().toString();
        mApiUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent displayUrl = new Intent(RecipeDetailActivity.this, WebActivity.class);
                displayUrl.putExtra(KEY4, apiUrl);
                Log.d(TAG, "onClick: " + apiUrl);
                startActivity(displayUrl);
            }
        });



    }


    @Override
    public void onFragmentInteraction() {

    }
}
