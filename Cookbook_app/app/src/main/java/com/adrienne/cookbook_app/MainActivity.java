package com.adrienne.cookbook_app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.adrienne.cookbook_app.Add_recipe.ManualEnterRecipeActivity;
import com.adrienne.cookbook_app.My_cookbook.MyCookbookFragment;

import com.adrienne.cookbook_app.My_cookbook.db_cookbook.DBAssetHelper;

import com.adrienne.cookbook_app.Search.CookbookPagerAdapter;
import com.adrienne.cookbook_app.Search.SearchFragment;


public class MainActivity extends AppCompatActivity implements SearchFragment.SearchOnFragmentInteractionListener, MyCookbookFragment.CookbookOnFragmentInteractionListener {

    public static final String TAG = "MAINACTIVITY: ";
    public static final String KEY = "ApiRecipeKey-title";
    public static final String KEY1 = "ApiRecipeKey-image";
    public static final String KEY2 = "ApiRecipeKey-website";
    public static final String KEY3 = "ApiRecipeKey-serving";
    public static final String KEY4 = "ApiRecipeKey-url";

    CookbookPagerAdapter mPagerAdapter;

    ImageView mHomeButton;


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

        mHomeButton = (ImageView) findViewById(R.id.to_home);
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You are home. Start searching.", Toast.LENGTH_SHORT).show();
            }
        });


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

        } else{
            //todo create load image to connect
            Toast.makeText(this, "You have fallen off the grid. Try again later.", Toast.LENGTH_LONG).show();
        }

//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(.getWindowToken(), 0);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }


    @Override
    public void onFragmentInteraction() {

    }

// do not delete
}
