package com.adrienne.cookbook_app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.adrienne.cookbook_app.My_cookbook.MyCookbookFragment;
import com.adrienne.cookbook_app.RecipeDetail.RecipeDetailActivity;
import com.adrienne.cookbook_app.My_cookbook.db_cookbook.DBAssetHelper;
//import com.adrienne.cookbook_app.Recipe.RecipeFragment;
import com.adrienne.cookbook_app.RecipeDetail.RecipeFragment;
import com.adrienne.cookbook_app.Search.ApiRecipe;
import com.adrienne.cookbook_app.Search.CookbookPagerAdapter;
import com.adrienne.cookbook_app.Search.EdamamAPI.EdamamResult.K;
import com.adrienne.cookbook_app.Search.SearchFragment;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchFragment.SearchOnFragmentInteractionListener, MyCookbookFragment.OnFragmentInteractionListener {

    public static final String TAG = "MAINACTIVITY: ";
    public static final String KEY = "ApiRecipeKey-title";
    public static final String KEY1 = "ApiRecipeKey-image";
    public static final String KEY2 = "ApiRecipeKey-website";
    public static final String KEY3 = "ApiRecipeKey-serving";
    public static final String KEY4 = "ApiRecipeKey-url";

    CookbookPagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new CookbookPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.viewpager_tablayout);
        tabLayout.setupWithViewPager(viewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });






        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

        } else{
            //todo create load image to connect
            Toast.makeText(this, "SummaryList ", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onFragmentInteraction() {

    }

// do not delete
}
