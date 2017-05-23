package com.adrienne.cookbook_app.My_cookbook;

import android.renderscript.Sampler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.adrienne.cookbook_app.R;
import com.adrienne.cookbook_app.Search.ApiRecipeViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Admin on 5/21/17.
 */

public class CookbookRecyclerViewAdapter extends RecyclerView.Adapter<CookbookViewHolder> {

    private List<MyRecipe> mMyRecipeList;
    private MyCookbookFragment.OnFragmentInteractionListener mcookbookListener;

    public CookbookRecyclerViewAdapter(List<MyRecipe> myRecipeList) {
        mMyRecipeList = myRecipeList;
    }


    @Override
    public CookbookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CookbookViewHolder(inflater.inflate(R.layout.mycookbook_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(CookbookViewHolder holder, int position) {
    final MyRecipe  currentMyRecipe = mMyRecipeList.get(position);

        Picasso.with(holder.mRecipeImage.getContext()).load(currentMyRecipe.getImage()).into(holder.mRecipeImage);
        holder.mRecipeTitle.setText(currentMyRecipe.getTitle());
        holder.mCategory.setText(currentMyRecipe.getCategory());
        holder.mServing.setText(String.valueOf(currentMyRecipe.getServings()));

    }

    @Override
    public int getItemCount() {
        return mMyRecipeList.size();
    }

    public void getnewCookbook(List<MyRecipe> newMyRecipe){
        mMyRecipeList = newMyRecipe;
        notifyDataSetChanged();
    }
}
