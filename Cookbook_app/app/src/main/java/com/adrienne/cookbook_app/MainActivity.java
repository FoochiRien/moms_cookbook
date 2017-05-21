package com.adrienne.cookbook_app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.Toast;

import com.adrienne.cookbook_app.My_cookbook.MyCookbookFragment;
import com.adrienne.cookbook_app.My_cookbook.db_cookbook.DBAssetHelper;
import com.adrienne.cookbook_app.Recipe.RecipeFragment;
import com.adrienne.cookbook_app.Search.CookbookPagerAdapter;
import com.adrienne.cookbook_app.Search.SearchFragment;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener, RecipeFragment.OnFragmentInteractionListener, MyCookbookFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

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




    }


    @Override
    public void onFragmentInteraction() {

    }


}
