package com.adrienne.cookbook_app.Search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.adrienne.cookbook_app.My_cookbook.MyCookbookFragment;
import com.adrienne.cookbook_app.RecipeDetail.RecipeFragment;

/**
 * Created by Admin on 5/19/17.
 */

public class CookbookPagerAdapter extends FragmentPagerAdapter {
    public CookbookPagerAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {

        Bundle bundledArgs = new Bundle();
        bundledArgs.putString();
        switch(position){
            case 0:
                bundledArgs.putString();
                return MyCookbookFragment.newInstance(bundledArgs);
            case 1:
                return SearchFragment.newInstance();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position){
        switch(position) {
            case 0:
                return "Cookbook";
            case 1:
                return "Web Search";
            default:
                return null;
        }
    }

}
