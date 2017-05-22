package com.adrienne.cookbook_app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.adrienne.cookbook_app.My_cookbook.MyCookbookFragment;
import com.adrienne.cookbook_app.RecipeDetail.RecipeFragment;
import com.adrienne.cookbook_app.Search.ApiRecipe;
import com.adrienne.cookbook_app.Search.CookbookPagerAdapter;
import com.adrienne.cookbook_app.Search.EdamamAPI.EdamamResult.K;
import com.adrienne.cookbook_app.Search.SearchFragment;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchFragment.SearchOnFragmentInteractionListener, RecipeFragment.OnFragmentInteractionListener, MyCookbookFragment.OnFragmentInteractionListener {

    public static final String TAG = "MAINACTIVITY: ";
    public static final String KEY = "ApiRecipeKey-title";
    public static final String KEY1 = "ApiRecipeKey-image";
    public static final String KEY2 = "ApiRecipeKey-website";
    public static final String KEY3 = "ApiRecipeKey-serving";
    public static final String KEY4 = "ApiRecipeKey-url";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        CookbookPagerAdapter pagerAdapter = new CookbookPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.viewpager_tablayout);
        tabLayout.setupWithViewPager(viewPager);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

        } else{

            Toast.makeText(this, "SummaryList ", Toast.LENGTH_SHORT).show();
        }


        Intent displayApiRecipeIntent = getIntent();
        String apiRecipe = displayApiRecipeIntent.getStringExtra(KEY);
        Log.d(TAG, "onCreate: ------" + KEY + apiRecipe);
        String apiRecipeImage = displayApiRecipeIntent.getStringExtra(KEY1);
        String apiRecipeWebsite = displayApiRecipeIntent.getStringExtra(KEY2);
        String apiRecipeServing = displayApiRecipeIntent.getStringExtra(KEY3);
        String apiRecipeUrl = displayApiRecipeIntent.getStringExtra(KEY4);

        Bundle bundle = new Bundle();
        bundle.putString(KEY, apiRecipe);
        bundle.putString(KEY1, apiRecipeImage);
        bundle.putString(KEY2, apiRecipeWebsite);
        bundle.putString(KEY3, apiRecipeServing);
        bundle.putString(KEY4, apiRecipeUrl);

        Fragment recipeFragment = new RecipeFragment().newInstance(bundle);
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_recipe_container, recipeFragment).commit();

        Log.d(TAG, "onCreate: ----" + bundle);

    }


    @Override
    public void onFragmentInteraction() {

    }

// do not delete
}
