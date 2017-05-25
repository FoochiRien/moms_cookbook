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

public class DirectionsRecyclerViewAdapter extends RecyclerView.Adapter {

   private List<Directions> mDirectionsList;
    public static final int VIEW_TYPE_ADD = 1;
    public static final int VIEW_TYPE_INGREDIENT = 2;

    DirectionsRecyclerViewAdapter mAdapter;

    public DirectionsRecyclerViewAdapter(List<Directions> directionsList) {
        mDirectionsList = directionsList;
    }

    @Override
    public int getItemViewType(int position) {
        if(mDirectionsList.size() == position){
            return VIEW_TYPE_ADD;
        } return VIEW_TYPE_INGREDIENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ADD){
            //inflate layout

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new DirectionsViewHolder(inflater.inflate(R.layout.custom_add_button_for_direction_recycler, parent, false));
        } else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new DirectionsViewHolder(inflater.inflate(R.layout.directions_for_manual_recipe, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//        if(holder instanceof AddViewHolder)
//        {   AddViewHolder addViewHolder = (AddViewHolder) holder;
//            addViewHolder.mAddButton.getText().toString();
//            //TODO ADD THINGS
//
//        }
//        else
//        { DirectionsViewHolder directionsViewHolder = (DirectionsViewHolder) holder;
//            directionsViewHolder.mRecipeDirections.getText().toString();
//            directionsViewHolder.mRecipeDirections.addTextChangedListener(new CustomTextWatcher());
//            //TODO INGREDIENT THINGS
//        }
    }



    @Override
    public int getItemCount() {
        return mDirectionsList.size()+1;
    }

    public class AddViewHolder extends RecyclerView.ViewHolder{

        Button mAddButton;

        public AddViewHolder(View itemView) {
            super(itemView);
            mAddButton = (Button) itemView.findViewById(R.id.add_to_directions_list);


        }
    }

    public class DirectionsViewHolder extends RecyclerView.ViewHolder{

        EditText mRecipeDirections;



        public DirectionsViewHolder(View itemView) {
            super(itemView);

            mRecipeDirections = (EditText) itemView.findViewById(R.id.recipe_directions_for_manual);

        }
    }

    public void swapData(List<Directions> newDirectionList){
        mDirectionsList = newDirectionList;
        notifyDataSetChanged();
    }
    public class CustomTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(mDirectionsList.get(start).toString().trim().isEmpty()){
                mDirectionsList.get(start).setDirections(null);
            } else {
                mDirectionsList.get(start).setDirections(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
