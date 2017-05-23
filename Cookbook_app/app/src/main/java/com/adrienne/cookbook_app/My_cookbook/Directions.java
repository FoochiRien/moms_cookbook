package com.adrienne.cookbook_app.My_cookbook;

/**
 * Created by Admin on 5/22/17.
 */

public class Directions {

    private String mDirectionsRecipeTitle, mDirections;

    public Directions(String directionsRecipeTitle, String directions) {
        mDirectionsRecipeTitle = directionsRecipeTitle;
        mDirections = directions;
    }

    public String getDirectionsRecipeTitle() {
        return mDirectionsRecipeTitle;
    }

    public void setDirectionsRecipeTitle(String directionsRecipeTitle) {
        mDirectionsRecipeTitle = directionsRecipeTitle;
    }

    public String getDirections() {
        return mDirections;
    }

    public void setDirections(String directions) {
        mDirections = directions;
    }
}
