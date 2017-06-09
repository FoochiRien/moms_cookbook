package com.adrienne.cookbook_app.DetailViewofRecipe.ManualEnteredRecipe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adrienne.cookbook_app.R;

import java.util.List;

/**
 * Created by Admin on 5/30/17.
 */

public class DisplayRecyclerViewAdapter extends RecyclerView.Adapter<DisplayRecyclerViewAdapter.DisplayIngredientsViewHolder> {

    private List<String> mDisplayIngredientsList;

    public DisplayRecyclerViewAdapter(List<String> displayIngredientsList) {
        mDisplayIngredientsList = displayIngredientsList;
    }

    @Override
    public DisplayRecyclerViewAdapter.DisplayIngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new DisplayRecyclerViewAdapter.DisplayIngredientsViewHolder(inflater.inflate(R.layout.display_ingredients_for_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(DisplayRecyclerViewAdapter.DisplayIngredientsViewHolder holder, int position) {
        //todo find by id
        holder.mIngredients.setText(mDisplayIngredientsList.get(position));

    }

    @Override
    public int getItemCount() {
        return mDisplayIngredientsList.size();
    }

    public void swapData(List<String> newDisplayIngredients){
        mDisplayIngredientsList = newDisplayIngredients;
        notifyDataSetChanged();
    }

public class DisplayIngredientsViewHolder extends RecyclerView.ViewHolder{

    TextView mIngredients;

    public DisplayIngredientsViewHolder(View itemView) {
        super(itemView);

        mIngredients = (TextView) itemView.findViewById(R.id.display_ingredients_for_recipe);
    }
}

}


