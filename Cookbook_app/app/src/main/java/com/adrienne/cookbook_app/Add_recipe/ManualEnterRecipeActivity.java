package com.adrienne.cookbook_app.Add_recipe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.adrienne.cookbook_app.MainActivity;
import com.adrienne.cookbook_app.My_cookbook.Directions;
import com.adrienne.cookbook_app.My_cookbook.DirectionsRecyclerViewAdapter;
import com.adrienne.cookbook_app.My_cookbook.Ingredients;
import com.adrienne.cookbook_app.My_cookbook.IngredientsRecyclerViewAdapter;
import com.adrienne.cookbook_app.My_cookbook.db_cookbook.RecipeSQLiteOpenHelper;
import com.adrienne.cookbook_app.R;

import java.util.ArrayList;
import java.util.List;

public class ManualEnterRecipeActivity extends AppCompatActivity {

    public static final String TAG = "MANUAL ENTER";

    //todo enter constants
    EditText mRecipeTitle, mRecipeNotes, mRecipeCategory, mRecipeServing, mCookTime;
    Button mSaveRecipe;


    private String recipeTitle, recipeNotes, recipeCategory,recipeServing, recipeCooktime;

    ImageView mHomeButton;

    IngredientsRecyclerViewAdapter mIngredientsAdapter;
    DirectionsRecyclerViewAdapter mDirectionsAdapter;

    private RecipeSQLiteOpenHelper mDBHelper;

    List<Ingredients> recipeIngredients;
    List<Directions> recipeDirections;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_enter_recipe);

        mDBHelper = RecipeSQLiteOpenHelper.getInstance(getApplicationContext());

        mRecipeTitle = (EditText) findViewById(R.id.enter_recipe_title);
        mRecipeNotes = (EditText) findViewById(R.id.enter_recipe_notes);
        mRecipeCategory = (EditText) findViewById(R.id.enter_recipe_category);
        mRecipeServing = (EditText) findViewById(R.id.enter_recipe_serving);
        mCookTime = (EditText) findViewById(R.id.enter_recipe_cooktime);
        mSaveRecipe = (Button) findViewById(R.id.save_recipe_button);




        mSaveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecipeTitle.getText().length() <= 0) {
                    Toast.makeText(ManualEnterRecipeActivity.this, "Please enter a title or Return to home.",
                            Toast.LENGTH_SHORT).show();
                    mRecipeTitle.setError("Please enter a title or Return to home.");
                } else {
                    recipeTitle = mRecipeTitle.getText().toString();
                    recipeNotes = mRecipeNotes.getText().toString();
                    recipeCategory = mRecipeCategory.getText().toString();
                    recipeServing = mRecipeServing.getText().toString();
                    recipeCooktime = mCookTime.getText().toString();
                    mDBHelper.addManualRecipetoCookbook(recipeTitle, recipeServing,
                            recipeCategory, recipeCooktime, recipeNotes, null);
                    Log.d(TAG, "onClick: save button" + recipeTitle);
                    Toast.makeText(ManualEnterRecipeActivity.this, "Your recipe has been added.",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ManualEnterRecipeActivity.this, MainActivity.class));
                }
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.directions_recyclerview);
        mDirectionsAdapter = new DirectionsRecyclerViewAdapter(new ArrayList<Directions>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(mDirectionsAdapter);

        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.ingredients_recylerview);
        mIngredientsAdapter = new IngredientsRecyclerViewAdapter(new ArrayList<Ingredients>());
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView2.setAdapter(mIngredientsAdapter);


        mHomeButton = (ImageView) findViewById(R.id.to_home_button);
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManualEnterRecipeActivity.this, MainActivity.class));
            }
        });
    }
}
