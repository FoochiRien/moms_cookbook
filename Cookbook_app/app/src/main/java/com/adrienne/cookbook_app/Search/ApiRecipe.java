package com.adrienne.cookbook_app.Search;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Admin on 5/19/17.
 */

public class ApiRecipe {

    private String apiImage, apiTitle, apiWebsiteSource;
    private Double apiServings;


    public ApiRecipe(String apiImage, String apiTitle, String apiWebsiteSource,
                     Double apiServings) {
        this.apiImage = apiImage;
        this.apiTitle = apiTitle;
        this.apiWebsiteSource = apiWebsiteSource;
        this.apiServings = apiServings;

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

    public Double getApiServings() {
        return apiServings;
    }


}


