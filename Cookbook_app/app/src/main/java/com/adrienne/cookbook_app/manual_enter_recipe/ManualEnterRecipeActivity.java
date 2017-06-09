package com.adrienne.cookbook_app.manual_enter_recipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.adrienne.cookbook_app.MainActivity;

import com.adrienne.cookbook_app.My_cookbook.db_cookbook.RecipeSQLiteOpenHelper;
import com.adrienne.cookbook_app.R;

import java.util.ArrayList;
import java.util.List;

public class ManualEnterRecipeActivity extends AppCompatActivity {

    public static final String TAG = "MANUAL ENTER";

    //todo enter constants
    EditText mRecipeTitle, mRecipeNotes, mRecipeCategory, mRecipeServing, mCookTime,
    mRecipeDirections;
    Button mSaveRecipe;


    private String recipeTitle, recipeNotes, recipeCategory,recipeServing, recipeCooktime,
            recipeDirections;

    private List<String> mIngredients;


    IngredientsRecyclerViewAdapter mIngredientsAdapter;

    private RecipeSQLiteOpenHelper mDBHelper;

    /*User can enter recipe manually. This was made way to complicated by under thinking. I am going
    * to redo the input abilities to stream line the input process and the display process.*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_enter_recipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_manual_enter_recipe);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mom's Cookbook");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        mDBHelper = RecipeSQLiteOpenHelper.getInstance(getApplicationContext());

        mRecipeTitle = (EditText) findViewById(R.id.enter_recipe_title);
        mRecipeNotes = (EditText) findViewById(R.id.enter_recipe_notes);
        mRecipeCategory = (EditText) findViewById(R.id.enter_recipe_category);
        mRecipeServing = (EditText) findViewById(R.id.enter_recipe_serving);
        mRecipeDirections = (EditText) findViewById(R.id.enter_recipe_directions);
        mCookTime = (EditText) findViewById(R.id.enter_recipe_cooktime);
        mSaveRecipe = (Button) findViewById(R.id.save_recipe_button);
        //todo ingredients

        /*Pretty straight forward data entry however, there is a recyclerview used to capture the
        * ingredients and directions. The idea was so that they could be kept in order. */

        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.ingredients_recylerview);
        mIngredientsAdapter = new IngredientsRecyclerViewAdapter(new ArrayList<String>());
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView2.setAdapter(mIngredientsAdapter);

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
                    recipeDirections = mRecipeDirections.getText().toString();
                    mIngredients = mIngredientsAdapter.getIngredients();
                    mDBHelper.addManualRecipetoCookbook(recipeTitle, recipeServing,
                            recipeCategory, recipeCooktime, recipeNotes, null,  recipeDirections,
                            mIngredients);
                    Log.d(TAG, "onClick: save button" + recipeTitle);
                    Toast.makeText(ManualEnterRecipeActivity.this, "Your recipe has been added.",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ManualEnterRecipeActivity.this, MainActivity.class));
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu_layout, menu);
        MenuItem delete = menu.findItem(R.id.menu_delete);
        MenuItem bookmark = menu.findItem(R.id.menu_bookmark);
        MenuItem searchapi = menu.findItem(R.id.menu_searchapi);
        delete.setVisible(false);
        bookmark.setVisible(false);
        searchapi.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.menu_home:
                startActivity(new Intent(ManualEnterRecipeActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
