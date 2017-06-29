package com.adrienne.cookbook_app.EditRecipe;

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
import android.widget.Toast;

import com.adrienne.cookbook_app.MainActivity;
import com.adrienne.cookbook_app.My_cookbook.MyRecipe;
import com.adrienne.cookbook_app.My_cookbook.db_cookbook.RecipeSQLiteOpenHelper;
import com.adrienne.cookbook_app.R;
import com.adrienne.cookbook_app.manual_enter_recipe.IngredientsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class EditManualRecipeActivity extends AppCompatActivity {

    //Edit recipe manually entered

    public static final String TAG = "EDIT MANUAL RECIPE";
    public static final String EDIT_RECIPE_ID = "Recipe_Id";

    EditText mEditRecipeTitle, mEditRecipeNotes, mEditRecipeCategory, mEditRecipeServing, mEditCookTime,
            mEditRecipeDirections;
    Button mEditSaveRecipe;


    private String editRecipeTitle, editRecipeNotes, editRecipeCategory,
            editRecipeServing, editRecipeCooktime, editRecipeDirections, editRecipeBookmark;
            long editRecipeId;
    private List<String> mEditIngredients;


    EditIngredientsRecyclerViewAdapter mEditIngredientsAdapter;

    private RecipeSQLiteOpenHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_enter_recipe);
        /* activity uses the layout from the manual enter recipe activity
        * */

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_manual_enter_recipe);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mom's Cookbook");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        mEditRecipeTitle = (EditText) findViewById(R.id.enter_recipe_title);
        mEditRecipeNotes = (EditText) findViewById(R.id.enter_recipe_notes);
        mEditRecipeCategory = (EditText) findViewById(R.id.enter_recipe_category);
        mEditRecipeServing = (EditText) findViewById(R.id.enter_recipe_serving);
        mEditRecipeDirections = (EditText) findViewById(R.id.enter_recipe_directions);
        mEditCookTime = (EditText) findViewById(R.id.enter_recipe_cooktime);
        mEditSaveRecipe = (Button) findViewById(R.id.save_recipe_button);

        //Intent for the recipe id
        Intent editRecipeIntent = getIntent();
        editRecipeId = editRecipeIntent.getLongExtra(EDIT_RECIPE_ID,editRecipeId);

        mDBHelper = RecipeSQLiteOpenHelper.getInstance(getApplicationContext());
        MyRecipe myRecipes = mDBHelper.getRecipeDisplay(editRecipeId);
        //pulling information from the intent
        String title = myRecipes.getTitle();
        String notes = myRecipes.getNotes();
        String category = myRecipes.getCategory();
        float serving2 = myRecipes.getServings();
        String serving = serving2 + " servings";
        float cooktime2 = myRecipes.getCookTime();
        String cooktime = cooktime2 + "";
        String directions = myRecipes.getDirections();
        final int bookmark2 = myRecipes.getBookmarked();
        final String bookmark = bookmark2 + "";

        //setting the edit text fields, setup for the edit
        mEditRecipeTitle.setText(title);
        mEditRecipeNotes.setText(notes);
        mEditRecipeCategory.setText(category);
        mEditRecipeServing.setText(serving);
        mEditRecipeDirections.setText(directions);
        mEditCookTime.setText(cooktime);

        //recyclerview for the ingredients uses the adapter from the manual enter activity
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.ingredients_recylerview);
        mEditIngredientsAdapter = new EditIngredientsRecyclerViewAdapter(mDBHelper.getAllIngredients(editRecipeId));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView2.setAdapter(mEditIngredientsAdapter);

        mEditSaveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditRecipeTitle.getText().length() <= 0) {
                    Toast.makeText(EditManualRecipeActivity.this, "Please enter a title or Return to home.",
                            Toast.LENGTH_SHORT).show();
                    mEditRecipeTitle.setError("Please enter a title or Return to home.");
                } else {

                    editRecipeTitle = mEditRecipeTitle.getText().toString();
                    editRecipeNotes = mEditRecipeNotes.getText().toString();
                    editRecipeCategory = mEditRecipeCategory.getText().toString();
                    editRecipeServing = mEditRecipeServing.getText().toString();
                    editRecipeCooktime = mEditCookTime.getText().toString();
                    editRecipeDirections = mEditRecipeDirections.getText().toString();
                    editRecipeBookmark = bookmark;
                    mEditIngredients = mEditIngredientsAdapter.getIngredients();
                    mDBHelper.updateManualRecipetoCookbook(editRecipeId, editRecipeTitle, editRecipeServing,
                            editRecipeCategory, editRecipeCooktime, editRecipeNotes,editRecipeBookmark,
                            editRecipeDirections, mEditIngredients);
                    Log.d(TAG, "onClick: save button" + editRecipeTitle);
                    Toast.makeText(EditManualRecipeActivity.this, "Your recipe has been updated.",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditManualRecipeActivity.this, MainActivity.class));
                }
            }
        });

    }
    //items not to be viewed on the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu_layout, menu);
        MenuItem edit = menu.findItem(R.id.menu_edit);
        edit.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
    //items to be viewed on the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                startActivity(new Intent(EditManualRecipeActivity.this, MainActivity.class));
                break;
            case R.id.menu_delete:
                mDBHelper.removeRecipeFromCookbook(editRecipeId);
                Log.d(TAG, "onOptionsItemSelected: delete recipe" + editRecipeId);
                Toast.makeText(EditManualRecipeActivity.this, "BYE, BYE, BYE. It's gone from you cookbook",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditManualRecipeActivity.this, MainActivity.class));
                break;
            case R.id.menu_bookmark:
                int bookmarked = mDBHelper.searchBookmark(editRecipeId);
                if (bookmarked == 1) {
                    mDBHelper.removeBookmark(editRecipeId);
                    item.setIcon(R.drawable.ic_bookmark_item);
                    Toast.makeText(EditManualRecipeActivity.this, "Removing from your Cookbook.",
                            Toast.LENGTH_LONG).show();
                } else {
                    mDBHelper.addBookmark(editRecipeId);
                    item.setIcon(R.drawable.ic_bookmark_recipe);
                    Toast.makeText(EditManualRecipeActivity.this, "Added to your cookbook.", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.menu_searchapi:
                startActivity(new Intent(EditManualRecipeActivity.this, MainActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
