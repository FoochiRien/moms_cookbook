package com.adrienne.cookbook_app.Search;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Admin on 5/19/17.
 */

public class ApiRecipe {

    private String apiImage, apiTitle, apiWebsiteSource, apiCategory, apiDirections, apiIngredients;
    private int apiServings;
    private float apiTime;

    public ApiRecipe(String apiImage, String apiTitle, String apiWebsiteSource, String apiCategory,
                     String apiDirections, String apiIngredients, int apiServings, float apiTime) {
        this.apiImage = apiImage;
        this.apiTitle = apiTitle;
        this.apiWebsiteSource = apiWebsiteSource;
        this.apiCategory = apiCategory;
        this.apiDirections = apiDirections;
        this.apiIngredients = apiIngredients;
        this.apiServings = apiServings;
        this.apiTime = apiTime;
    }

    public String getApiImage() {
        return apiImage;
    }

    public String getApiTitle() {
        return apiTitle;
    }

    public String getApiWebsiteSource() {
        return apiWebsiteSource;
    }

    public String getApiCategory() {
        return apiCategory;
    }

    public String getApiDirections() {
        return apiDirections;
    }

    public String getApiIngredients() {
        return apiIngredients;
    }

    public int getApiServings() {
        return apiServings;
    }

    public float getApiTime() {
        return apiTime;
    }
}


