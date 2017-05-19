package com.adrienne.cookbook_app.Search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.adrienne.cookbook_app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Admin on 5/19/17.
 */

public class ApiRecipeRecyclerViewAdapter extends RecyclerView.Adapter<ApiRecipeViewHolder>{

    private List<ApiRecipe> mApiRecipeList;

    public ApiRecipeRecyclerViewAdapter(List<ApiRecipe> apiRecipeList) {
        mApiRecipeList = apiRecipeList;
    }

    @Override
    public ApiRecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ApiRecipeViewHolder(inflater.inflate(R.layout.api_reciperesult_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ApiRecipeViewHolder holder, int position) {
    final ApiRecipe currentApiRecipe = mApiRecipeList.get(position);
//        Picasso.with(holder.mApiImage.getContext()).load().into(holder.mApiImage);
        holder.mApiTitle.setText(currentApiRecipe.getApiTitle());
        holder.mApiCategory.setText(currentApiRecipe.getApiCategory());
        holder.mApiWebsiteSource.setText(currentApiRecipe.getApiWebsiteSource());
        holder.mApiServing.setText(currentApiRecipe.getApiServings());
//        holder.mApiTime.(currentApiRecipe.getApiTime());
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
