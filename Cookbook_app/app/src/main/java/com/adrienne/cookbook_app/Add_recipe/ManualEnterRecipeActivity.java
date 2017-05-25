package com.adrienne.cookbook_app.Add_recipe;

import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.adrienne.cookbook_app.My_cookbook.Directions;
import com.adrienne.cookbook_app.My_cookbook.Ingredients;
import com.adrienne.cookbook_app.My_cookbook.db_cookbook.RecipeSQLiteOpenHelper;
import com.adrienne.cookbook_app.R;

import java.util.List;

public class ManualEnterRecipeActivity extends AppCompatActivity {

    public static final String TAG = "MANUAL ENTER";

    //todo enter constants
    EditText mRecipeTitle, mRecipeNotes, mRecipeCategory, mRecipeServing, mCookTime;
    Button mSaveRecipe;


    private String recipeTitle, recipeNotes, recipeCategory,recipeServing, recipeCooktime;



    List<Ingredients> recipeIngredients;
    List<Directions> recipeDirections;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_enter_recipe);

        mRecipeTitle = (EditText) findViewById(R.id.enter_recipe_title);
        mRecipeNotes = (EditText) findViewById(R.id.enter_recipe_notes);
        mRecipeCategory = (EditText) findViewById(R.id.enter_recipe_category);
        mRecipeServing = (EditText) findViewById(R.id.enter_recipe_serving);
        mCookTime = (EditText) findViewById(R.id.enter_recipe_cooktime);
        mSaveRecipe = (Button) findViewById(R.id.save_recipe_button);


        recipeTitle = mRecipeTitle.getText().toString();
        recipeNotes = mRecipeNotes.getText().toString();
        recipeCategory = mRecipeCategory.getText().toString();
        recipeServing = mRecipeServing.getText().toString();
        recipeCooktime = mCookTime.getText().toString();

        mSaveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeSQLiteOpenHelper.getInstance(getApplicationContext())
                        .addManualRecipetoCookbook(recipeTitle,recipeServing,
                        recipeCategory, recipeCooktime, recipeNotes, null);
            }
        });

    }
}
