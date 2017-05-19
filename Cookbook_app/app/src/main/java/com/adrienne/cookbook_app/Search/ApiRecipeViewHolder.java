package com.adrienne.cookbook_app.Search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrienne.cookbook_app.R;

/**
 * Created by Admin on 5/19/17.
 */

public class ApiRecipeViewHolder extends RecyclerView.ViewHolder{

    ImageView mApiImage;
    TextView mApiTitle, mApiWebsiteSource, mApiCategory, mApiDirections, mApiIngredients,
            mApiServing, mApiTime;
    View mRootview;


    public ApiRecipeViewHolder(View itemView) {
        super(itemView);

        mRootview = itemView.findViewById(R.id.api_searchresultrecipe_layout);
        mApiImage = (ImageView) itemView.findViewById(R.id.api_recipe_image);
        mApiTitle = (TextView) itemView.findViewById(R.id.api_recipe_title);
        mApiWebsiteSource = (TextView) itemView.findViewById(R.id.api_recipe_source_website);
        mApiCategory = (TextView) itemView.findViewById(R.id.api_recipe_category);
        mApiServing = (TextView) itemView.findViewById(R.id.api_recipe_servings);
        mApiTime = (TextView) itemView.findViewById(R.id.api_recipe_time);

    }


}
