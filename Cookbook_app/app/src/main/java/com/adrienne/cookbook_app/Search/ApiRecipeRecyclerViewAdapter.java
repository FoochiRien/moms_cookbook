package com.adrienne.cookbook_app.Search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrienne.cookbook_app.MainActivity;
import com.adrienne.cookbook_app.R;
import com.adrienne.cookbook_app.RecipeDetail.RecipeDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Admin on 5/19/17.
 */

public class ApiRecipeRecyclerViewAdapter extends RecyclerView.Adapter<ApiRecipeViewHolder>{

    private List<ApiRecipe> mApiRecipeList;
    private SearchFragment.SearchOnFragmentInteractionListener  mSearchListner;

    public ApiRecipeRecyclerViewAdapter(List<ApiRecipe> apiRecipeList) {
        mApiRecipeList = apiRecipeList;
    }

    @Override
    public ApiRecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ApiRecipeViewHolder(inflater.inflate(R.layout.api_reciperesult_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final ApiRecipeViewHolder holder, final int position) {
    final ApiRecipe currentApiRecipe = mApiRecipeList.get(position);
        Picasso.with(holder.mApiImage.getContext()).load(currentApiRecipe.getApiImage()).into(holder.mApiImage);
        holder.mApiTitle.setText(currentApiRecipe.getApiTitle());
        holder.mApiWebsiteSource.setText(currentApiRecipe.getApiWebsiteSource());
        holder.mApiServing.setText(String.valueOf(currentApiRecipe.getApiServings()));

        holder.mRootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ApiRecipe apiRecipe = mApiRecipeList.get(holder.getAdapterPosition());
                String title = apiRecipe.getApiTitle();
                String image =  apiRecipe.getApiImage();
                String website = apiRecipe.getApiWebsiteSource();
                String servings = apiRecipe.getApiServings().toString();
                String url = apiRecipe.getApiUrl();
                Intent displayApiRecipeIntent = new Intent(v.getContext().getApplicationContext(),
                        RecipeDetailActivity.class);

                displayApiRecipeIntent.putExtra(MainActivity.KEY, title);
                displayApiRecipeIntent.putExtra(MainActivity.KEY1, image);
                displayApiRecipeIntent.putExtra(MainActivity.KEY2, website);
                displayApiRecipeIntent.putExtra(MainActivity.KEY3, servings);
                displayApiRecipeIntent.putExtra(MainActivity.KEY4, url);
                v.getContext().startActivity(displayApiRecipeIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return  mApiRecipeList.size();
    }

    public void swapData(List<ApiRecipe> newApiRecipeList){
    mApiRecipeList = newApiRecipeList;
        notifyDataSetChanged();

    }

}
