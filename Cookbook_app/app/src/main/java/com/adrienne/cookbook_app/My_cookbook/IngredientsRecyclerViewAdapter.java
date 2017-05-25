package com.adrienne.cookbook_app.My_cookbook;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.adrienne.cookbook_app.R;


import java.util.List;

/**
 * Created by Admin on 5/24/17.
 */

public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<Ingredients> mIngredientsList;
    public static final int VIEW_TYPE_ADD = 1;
    public static final int VIEW_TYPE_INGREDIENT = 2;
    private String ingredient;

    public IngredientsRecyclerViewAdapter(List<Ingredients> ingredientsList) {
        mIngredientsList = ingredientsList;
    }

    @Override
    public int getItemViewType(int position) {
        if (mIngredientsList.size() == position) {
            return VIEW_TYPE_ADD;
        }
        return VIEW_TYPE_INGREDIENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ADD) {
            //inflate layout
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new AddViewHolder(inflater.inflate(R.layout.custom_add_button_for_direction_recycler, parent, false));
        } else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new IngredientsViewHolder(inflater.inflate(R.layout.ingredient_for_manual_recipe, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof AddViewHolder) {
//            //TODO ADD THINGS
//            AddViewHolder addViewHolder = (AddViewHolder) holder;
//            ((AddViewHolder) holder).mAddButton.getText().toString();

//            super.onBindViewHolder(addViewHolder, mIngredientsList.get(position) );
//            mIngredientsList.add(position, mIngredientsList.get(position).setIngredients(mIngredientsList);
//
//        } else {
//            //TODO INGREDIENT THINGS
//            IngredientsViewHolder ingredientsViewHolder = (IngredientsViewHolder) holder;
//            ingredientsViewHolder.mRecipeIngredients.setText(mIngredientsList.get(position).getIngredients());
//            ingredientsViewHolder.mRecipeIngredients.addTextChangedListener(new CustomTextWatcher());
//        }

    }



    @Override
    public int getItemCount() {
        return mIngredientsList.size()+1;
    }

    public class AddViewHolder extends RecyclerView.ViewHolder{

        Button mAddButton;

        public AddViewHolder(View itemView) {
            super(itemView);
            mAddButton = (Button) itemView.findViewById(R.id.add_to_ingredients_list);


        }
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder{

        EditText mRecipeIngredients;


        public IngredientsViewHolder(View itemView) {
            super(itemView);

            mRecipeIngredients = (EditText) itemView.findViewById(R.id.recipe_ingredient);

        }
    }

    public void swapData(List<Ingredients> newIngredientsList){
        mIngredientsList = newIngredientsList;
        notifyDataSetChanged();
    }

    public class CustomTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(mIngredientsList.get(start).toString().trim().isEmpty()){
                mIngredientsList.get(start).setIngredients(null);
            } else {
                mIngredientsList.get(start).setIngredients(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
