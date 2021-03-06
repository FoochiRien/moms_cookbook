package com.adrienne.cookbook_app.My_cookbook;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrienne.cookbook_app.R;
import com.adrienne.cookbook_app.DetailViewofRecipe.RecipeDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Admin on 5/21/17.
 */

public class CookbookRecyclerViewAdapter extends RecyclerView.Adapter<CookbookViewHolder> {

    private List<MyRecipe> mMyRecipeList;
    private MyCookbookFragment.CookbookOnFragmentInteractionListener mcookbookListener;


    public CookbookRecyclerViewAdapter(List<MyRecipe> myRecipeList) {
        mMyRecipeList = myRecipeList;
    }


    @Override
    public CookbookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CookbookViewHolder(inflater.inflate(R.layout.mycookbook_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final CookbookViewHolder holder, int position) {
        final MyRecipe currentMyRecipe = mMyRecipeList.get(position);

        if (currentMyRecipe.getImage() == null || currentMyRecipe.getImage().trim().isEmpty()){
            Picasso.with(holder.mRecipeImage.getContext()).load(R.drawable.defaultimage).fit().into(holder.mRecipeImage);
        } else {
            Picasso.with(holder.mRecipeImage.getContext()).load(currentMyRecipe.getImage()).fit().into(holder.mRecipeImage);
        }
        holder.mRecipeTitle.setText(currentMyRecipe.getTitle());
//        holder.mCategory.setText(currentMyRecipe.getCategory());
//        holder.mServing.setText(String.valueOf(currentMyRecipe.getServings()));
        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyRecipe myRecipe = mMyRecipeList.get(holder.getAdapterPosition());
                long recipeId = myRecipe.getRecipeId();
                String viewToShow = myRecipe.getViewToShow();
                Intent displayRecipeIntent = new Intent(v.getContext().getApplicationContext(),
                        RecipeDetailActivity.class);
                displayRecipeIntent.putExtra("Recipe_Id", recipeId);
                displayRecipeIntent.putExtra("View", viewToShow);
                v.getContext().startActivity(displayRecipeIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMyRecipeList.size();
    }

    public void getnewCookbook(List<MyRecipe> newMyRecipe) {
        mMyRecipeList = newMyRecipe;
        notifyDataSetChanged();
    }
}
