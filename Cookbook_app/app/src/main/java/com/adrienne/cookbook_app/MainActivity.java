package com.adrienne.cookbook_app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import com.adrienne.cookbook_app.APISearch.ApiRecipe;
import com.adrienne.cookbook_app.My_cookbook.MyRecipe;
import com.adrienne.cookbook_app.manual_enter_recipe.ManualEnterRecipeActivity;
import com.adrienne.cookbook_app.My_cookbook.MyCookbookFragment;

import com.adrienne.cookbook_app.My_cookbook.db_cookbook.DBAssetHelper;

import com.adrienne.cookbook_app.APISearch.CookbookPagerAdapter;
import com.adrienne.cookbook_app.APISearch.SearchFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SearchFragment.SearchOnFragmentInteractionListener, MyCookbookFragment.CookbookOnFragmentInteractionListener {

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

        /* The Main Activity hows the Cookbook app and the Api Search.
        * From here the user is able to view the recipes that they have added manually or
        * from an search on the API. */

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new CookbookPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.viewpager_tablayout);
        tabLayout.setupWithViewPager(viewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mom's Cookbook");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);


        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_addrecipe){
                 startActivity(new Intent(MainActivity.this, ManualEnterRecipeActivity.class));

                } else if (id == R.id.nav_mycookbook){

                } else if (id == R.id.nav_search) {
                }

                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


            checkNetworkConnection();

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }


    @Override
    public void onFragmentInteraction() {

    }

    public void checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            //api web search populates- action beings on the fragment

        } else {
            //if no connection show message and check for api results.
            ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
            Snackbar.make(viewPager, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Connect", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkNetworkConnection();
                            SearchFragment.newInstance().getApiResults();
                        }
                    })
                    .show();

        }

    }

// do not delete
}
