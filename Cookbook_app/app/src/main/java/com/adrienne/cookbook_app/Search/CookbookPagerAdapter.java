package com.adrienne.cookbook_app.Search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.adrienne.cookbook_app.My_cookbook.MyCookbookFragment;
import com.adrienne.cookbook_app.Recipe.RecipeFragment;

/**
 * Created by Admin on 5/19/17.
 */

public class CookbookPagerAdapter extends FragmentPagerAdapter {
    public CookbookPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return MyCookbookFragment.newInstance();
            case 1:
                return SearchFragment.newInstance();
            case 2:
                return RecipeFragment.newInstance();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position){
        switch(position) {
            case 0:
                return "Cookbook";
            case 1:
                return "Web Search";
            case 2:
                return "Recipe";
            default:
                return null;
        }
    }

}
