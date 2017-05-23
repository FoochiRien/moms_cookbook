package com.adrienne.cookbook_app.My_cookbook;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrienne.cookbook_app.R;

/**
 * Created by Admin on 5/21/17.
 */

public class CookbookViewHolder extends RecyclerView.ViewHolder {

    View mRootView;
    ImageView mRecipeImage;
    TextView mRecipeTitle, mCategory, mServing;


    public CookbookViewHolder(View itemView) {
        super(itemView);

        mRootView = itemView.findViewById(R.id.mycookbook_layout_recyclerview);
        mRecipeImage = (ImageView) itemView.findViewById(R.id.cookbook_image);
        mRecipeTitle = (TextView) itemView.findViewById(R.id.cookbook_title);
        mServing = (TextView) itemView.findViewById(R.id.cookbook_serving);
        mCategory = (TextView) itemView.findViewById(R.id.cookbook_category);

    }

}
