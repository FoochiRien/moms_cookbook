package com.adrienne.cookbook_app.EditRecipe;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adrienne.cookbook_app.DetailViewofRecipe.SavedAPIRecipe.WebActivity;
import com.adrienne.cookbook_app.MainActivity;
import com.adrienne.cookbook_app.My_cookbook.MyRecipe;
import com.adrienne.cookbook_app.My_cookbook.db_cookbook.RecipeSQLiteOpenHelper;
import com.adrienne.cookbook_app.R;
import com.squareup.picasso.Picasso;

public class EditApiRecipeActivity extends AppCompatActivity {

    //Edit recipe that was saved from the API search

    public static final String TAG = "API MANUAL RECIPE";
    public static final String EDIT_RECIPE_ID = "Recipe_Id";
    public static final String KEY4 = "ApiRecipeKey-url";

    ImageView mEditApiImage, mEditFavApiRecipe, mEditEdamamImage_apiresults;
    TextView mEditApiTitle, mEditApiWebsiteSource, mEditApiUrl;
    EditText mEditCategories;

    private RecipeSQLiteOpenHelper mDBHelper;

    String editApiCategories;
    long editApiRecipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_result_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_api_result_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Moms Cookbook");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        Intent editRecipeIntent = getIntent();
        editApiRecipeId = editRecipeIntent.getLongExtra(EDIT_RECIPE_ID,editApiRecipeId);

        mDBHelper = RecipeSQLiteOpenHelper.getInstance(getApplicationContext());
        MyRecipe myRecipes = mDBHelper.getRecipeDisplay(editApiRecipeId);

        mEditApiImage = (ImageView) findViewById(R.id.api_recipe_detail_image);
        mEditApiTitle = (TextView) findViewById(R.id.api_recipe_detail_title);
        mEditApiWebsiteSource = (TextView) findViewById(R.id.api_recipe_detail_source);
        mEditCategories = (EditText) findViewById(R.id.enter_categories_for_apirecipe);
        mEditFavApiRecipe = (ImageView) findViewById(R.id.api_recipe_detail_bookmark);
        mEditApiUrl = (TextView) findViewById(R.id.api_recipe_detail_url);
        mEditEdamamImage_apiresults = (ImageView) findViewById(R.id.edamam_searchresults);

        final String apiTitle = myRecipes.getTitle() ;
        final String apiImage = myRecipes.getImage();
        final String apiWebsite = myRecipes.getSourceTitle();
        Float apiServing2 = myRecipes.getServings();
        final String apiServing = apiServing2 + "";
        final String apiUrl = myRecipes.getSourceUrl() ;
        String apiCategories = myRecipes.getCategory();

        mEditApiTitle.setText(apiTitle);
        mEditApiWebsiteSource.setText(apiWebsite);
        mEditCategories.setText(apiCategories);
        mEditApiUrl.setText(apiUrl);

        Picasso.with(mEditApiImage.getContext()).load(apiImage).into(mEditApiImage);

        mEditFavApiRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editApiCategories = mEditCategories.getText().toString();
                Log.d(TAG, "onClick: apicategories" + editApiCategories);
                mDBHelper.updateApiRecipetoCookbook(editApiRecipeId, apiImage, apiTitle, apiServing,
                        editApiCategories, apiWebsite, apiUrl, null);
                Log.d(TAG, "onClick: ");
                Toast.makeText(EditApiRecipeActivity.this, "Recipe has been updated.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        mEditApiUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent displayUrl = new Intent(EditApiRecipeActivity.this, WebActivity.class);
                displayUrl.putExtra(KEY4, apiUrl);
                Log.d(TAG, "onClick: " + apiUrl);
                startActivity(displayUrl);
            }
        });
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        mEditEdamamImage_apiresults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://developer.edamam.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
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
                startActivity(new Intent(EditApiRecipeActivity.this, MainActivity.class));
                break;
            case R.id.menu_delete:
                mDBHelper.removeRecipeFromCookbook(editApiRecipeId);
                Log.d(TAG, "onOptionsItemSelected: delete recipe" + editApiRecipeId);
                Toast.makeText(EditApiRecipeActivity.this, "BYE, BYE, BYE. It's gone from you cookbook",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditApiRecipeActivity.this, MainActivity.class));
                break;
            case R.id.menu_bookmark:
                int bookmarked = mDBHelper.searchBookmark(editApiRecipeId);
                if (bookmarked == 1) {
                    mDBHelper.removeBookmark(editApiRecipeId);
                    item.setIcon(R.drawable.ic_bookmark_item);
                    Toast.makeText(EditApiRecipeActivity.this, "Removing from your Cookbook.",
                            Toast.LENGTH_LONG).show();
                } else {
                    mDBHelper.addBookmark(editApiRecipeId);
                    item.setIcon(R.drawable.ic_bookmark_recipe);
                    Toast.makeText(EditApiRecipeActivity.this, "Added to your cookbook.", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.menu_searchapi:
                startActivity(new Intent(EditApiRecipeActivity.this, MainActivity.class));
                break;
        }
            return super.onOptionsItemSelected(item);
        }

}
