package com.adrienne.cookbook_app.Search;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Admin on 5/19/17.
 */

public class ApiRecipeViewHolder {

    ImageView mApiImage;
    TextView mApiTitle, mApiWebsiteSource, mApiCategory, mApiDirections, mApiIngredients,
            mApiServing, mApiTime;

    public ApiRecipeViewHolder(ImageView apiImage, TextView apiTitle, TextView apiWebsiteSource,
                               TextView apiCategory, TextView apiDirections, TextView apiIngredients,
                               TextView apiServing, TextView apiTime) {

        mApiImage = apiImage;
        mApiTitle = apiTitle;
        mApiWebsiteSource = apiWebsiteSource;
        mApiCategory = apiCategory;
        mApiDirections = apiDirections;
        mApiIngredients = apiIngredients;
        mApiServing = apiServing;
        mApiTime = apiTime;
    }



}
