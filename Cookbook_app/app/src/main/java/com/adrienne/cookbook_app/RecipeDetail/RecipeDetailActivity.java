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
import android.widget.ImageView;
import android.widget.TextView;

import com.adrienne.cookbook_app.R;
import com.adrienne.cookbook_app.Search.SearchFragment;
import com.squareup.picasso.Picasso;

public class RecipeDetailActivity extends AppCompatActivity implements SearchFragment.SearchOnFragmentInteractionListener {


    ImageView mApiImage;
    View mFavApiRecipe;
    TextView mApiTitle, mApiWebsiteSource, mApiServing;

    public static final String KEY = "ApiRecipeKey-title";
    public static final String KEY1 = "ApiRecipeKey-image";
    public static final String KEY2 = "ApiRecipeKey-website";
    public static final String KEY3 = "ApiRecipeKey-serving";
    public static final String KEY4 = "ApiRecipeKey-url";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Recipe");

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_all_other_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_all_other_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });



        mFavApiRecipe = findViewById(R.id.api_recipe_detail_bookmark);
        mApiImage = (ImageView) findViewById(R.id.api_recipe_detail_image);
        mApiTitle = (TextView) findViewById(R.id.api_recipe_detail_title);
        mApiWebsiteSource = (TextView) findViewById(R.id.api_recipe_detail_source);




        Intent displayApiRecipeIntent = getIntent();
        String apiRecipe = displayApiRecipeIntent.getStringExtra(KEY);
        String apiImage = displayApiRecipeIntent.getStringExtra(KEY1);
        String apiWebsite = displayApiRecipeIntent.getStringExtra(KEY2);
        String apiServing = displayApiRecipeIntent.getStringExtra(KEY3);
        String apiUrl = displayApiRecipeIntent.getStringExtra(KEY4);

        mApiTitle.setText(apiRecipe);
        Picasso.with(mApiImage.getContext()).load(apiImage).into(mApiImage);
        mApiWebsiteSource.setText(apiWebsite);




    }

    @Override
    public void onFragmentInteraction() {

    }
}
