package com.adrienne.cookbook_app.My_cookbook.db_cookbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;


import com.adrienne.cookbook_app.My_cookbook.MyRecipe;
import com.adrienne.cookbook_app.Search.EdamamAPI.EdamamResult.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Admin on 5/20/17.
 */

public class RecipeSQLiteOpenHelper extends SQLiteOpenHelper {

    //___________________Define Database _________________

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cookbook.db";
    private static final String RECIPE_TABLE_NAME = "recipe";

    private static final String COL_ID = "recipe_id";
    private static final String COL_IMAGE = "image";
    private static final String COL_TITLE = "title";
    private static final String COL_SERVINGS = "servings";
    private static final String COL_COOKTIME = "cooktime";
    private static final String COL_CATEGORY = "category";
    private static final String COL_NOTES = "notes";
    private static final String COL_SOURCETITLE = "sourcetitle";
    private static final String COL_SOURCEWEBSITE = "sourcewebsite";
    private static final String COL_BOOKMARKED = "bookmarked";
    private static final String COL_VIEW_TO_SHOW = "view_to_show";
    private static final String COL_DIRECTIONS = "directions";
    //todo add col for view to show add to DB


    private static final String CREATE_RECIPE_TABLE = "CREATE TABLE " + RECIPE_TABLE_NAME + "(" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_IMAGE + " TEXT, " +
            COL_TITLE + " TEXT, " +
            COL_SERVINGS + " INTEGER, " +
            COL_COOKTIME + " INTEGER, " +
            COL_CATEGORY + " TEXT, " +
            COL_NOTES + " TEXT, " +
            COL_SOURCETITLE + " TEXT, " +
            COL_SOURCEWEBSITE + " TEXT, " +
            COL_BOOKMARKED + " INTEGER, " +
            COL_DIRECTIONS + " TEXT, " +
            COL_VIEW_TO_SHOW + " TEXT )";


    private static final String INGREDIENTS_TABLE_NAME = "ingredients";
    private static final String COL_INGRE_ID = "ingredient_id";
    private static final String COL_RECIPE_ID = "recipe_id";
    private static final String COL_INGREDIENTS = "ingredients";


    private static final String CREATE_INGREDIENTS_TABLE = "CREATE TABLE " +
            INGREDIENTS_TABLE_NAME + "(" +
            COL_INGRE_ID + " INTEGER PRIMARY KEY, " +
            COL_RECIPE_ID + " INTEGER, " +
            COL_INGREDIENTS + " TEXT )";


    //-------------Singleton -------------------

    private static RecipeSQLiteOpenHelper sInstance;

    public static RecipeSQLiteOpenHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RecipeSQLiteOpenHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private RecipeSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // ----------------         ---------------------------
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_RECIPE_TABLE);
        db.execSQL(CREATE_INGREDIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RECIPE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + INGREDIENTS_TABLE_NAME);
        this.onCreate(db);
    }

    // add recipe to cookbook from api
    public void addApiRecipetoCookbook(String apiImage, String apiRecipe, String apiServing,
                                       String apiCategories, String apiWebsite, String apiUrl) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_IMAGE, apiImage);
        values.put(COL_TITLE, apiRecipe);
        values.put(COL_SERVINGS, apiServing);
        values.put(COL_COOKTIME, "");
        values.put(COL_CATEGORY, apiCategories);
        values.put(COL_NOTES, "");
        values.put(COL_SOURCETITLE, apiWebsite);
        values.put(COL_SOURCEWEBSITE, apiUrl);
        values.put(COL_BOOKMARKED, "");
        values.put(COL_VIEW_TO_SHOW, "api");
        db.insert(RECIPE_TABLE_NAME, null, values);
        db.close();
    }

    public void addManualRecipetoCookbook( String manualTitle, String manualServing,
                                       String manualCategories, String manualCooktime,
                                           String manualNotes, String manualBookmark,
                                           String manualDirections, List<String> ingredients) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_IMAGE, "");
        values.put(COL_TITLE, manualTitle);
        values.put(COL_SERVINGS, manualServing);
        values.put(COL_COOKTIME, manualCooktime);
        values.put(COL_CATEGORY, manualCategories);
        values.put(COL_NOTES, manualNotes);
        values.put(COL_SOURCETITLE, "");
        values.put(COL_SOURCEWEBSITE, "");
        values.put(COL_BOOKMARKED, "");
        values.put(COL_VIEW_TO_SHOW, "manual");
        values.put(COL_DIRECTIONS, manualDirections);
        long recipeid = db.insert(RECIPE_TABLE_NAME, null, values);
        for(String ingredient : ingredients)
        {
            insertIngredients(ingredient, recipeid);
        }
        db.close();
    }


    public void insertIngredients(String ingredient, long recipeId){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_RECIPE_ID, recipeId);
        values.put(COL_INGREDIENTS, ingredient);
        db.insert(INGREDIENTS_TABLE_NAME, null, values);
    }
    //
//    public void addPhotoRecipetoCookbook(String manualImage, String manualTitle, String manualServing,
//                                       String manualCategories, String manualCooktime,
//                                         String manualNotes, String manualBookmark) {
//
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COL_IMAGE, manualImage);
//        values.put(COL_TITLE, manualTitle);
//        values.put(COL_SERVINGS, manualServing);
//        values.put(COL_COOKTIME, manualCooktime);
//        values.put(COL_CATEGORY, manualCategories);
//        values.put(COL_NOTES, manualNotes);
//        values.put(COL_SOURCETITLE, "");
//        values.put(COL_SOURCEWEBSITE, "");
//        values.put(COL_BOOKMARKED, "");
//        values.put(COL_VIEW_TO_SHOW, "photo");
//        db.insert(RECIPE_TABLE_NAME, null, values);
//        db.close();
//    }


    //REMOVE ITEM FROM COOKBOOK


    //----Cookbook search. The user is able to search through recipes in their
    //----own cookbook.

    public List<MyRecipe> getAllRecipes() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(RECIPE_TABLE_NAME,
                null, null, null, null, null, null);

        List<MyRecipe> myrecipes = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                long recipeId = cursor.getLong(cursor.getColumnIndex(COL_ID));

                String image = cursor.getString(cursor.getColumnIndex(COL_IMAGE));
                String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                List ingredients = getAllIngredients(recipeId);
                float servings = cursor.getFloat(cursor.getColumnIndex(COL_SERVINGS));
                float cooktime = cursor.getFloat(cursor.getColumnIndex(COL_COOKTIME));
                String category = cursor.getString(cursor.getColumnIndex(COL_CATEGORY));
                String notes = cursor.getString(cursor.getColumnIndex(COL_NOTES));
                String sourcetitle = cursor.getString(cursor.getColumnIndex(COL_SOURCETITLE));
                String sourcewebsite = cursor.getString(cursor.getColumnIndex(COL_SOURCEWEBSITE));
                int bookmarked = cursor.getInt(cursor.getColumnIndex(COL_BOOKMARKED));
                String viewtoshow = cursor.getString(cursor.getColumnIndex(COL_VIEW_TO_SHOW));
                String directions = cursor.getString(cursor.getColumnIndex(COL_DIRECTIONS));


                MyRecipe recipeforshow = new MyRecipe(recipeId, image, sourcetitle, title,
                        notes, category, sourcewebsite, servings, cooktime, directions,
                        ingredients, bookmarked, viewtoshow);
                myrecipes.add(recipeforshow);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return myrecipes;
    }



    public List<String> getAllIngredients(long recipeid) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = COL_RECIPE_ID + " = ?";
        String recipe_id = Long.toString(recipeid);

        Cursor cursor = db.query(INGREDIENTS_TABLE_NAME,
                null, selection, new String[]{recipe_id}, null, null, null, null);

        List<String> myingredients = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                long recipe_Id = cursor.getLong(cursor.getColumnIndex(COL_RECIPE_ID));
                String ingredient = cursor.getString(cursor.getColumnIndex(COL_INGREDIENTS));

                myingredients.add(ingredient);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return myingredients;


    }


    public MyRecipe getRecipeDisplay(long recipeId) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = COL_ID + " = ?";
        String recipe_id = Long.toString(recipeId);
        Cursor cursor = db.query(RECIPE_TABLE_NAME,
                null,
                selection,
                new String[]{recipe_id},
                null,
                null,
                null);

       MyRecipe myrecipes = null;

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                long recipe_Id = cursor.getInt(cursor.getColumnIndex(COL_ID));

                String image = cursor.getString(cursor.getColumnIndex(COL_IMAGE));
                String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                List ingredients = getAllIngredients(recipeId);
                String directions = cursor.getString(cursor.getColumnIndex(COL_DIRECTIONS));
                float servings = cursor.getFloat(cursor.getColumnIndex(COL_SERVINGS));
                float cooktime = cursor.getFloat(cursor.getColumnIndex(COL_COOKTIME));
                String category = cursor.getString(cursor.getColumnIndex(COL_CATEGORY));
                String notes = cursor.getString(cursor.getColumnIndex(COL_NOTES));
                String sourcetitle = cursor.getString(cursor.getColumnIndex(COL_SOURCETITLE));
                String sourcewebsite = cursor.getString(cursor.getColumnIndex(COL_SOURCEWEBSITE));
                int bookmarked = cursor.getInt(cursor.getColumnIndex(COL_BOOKMARKED));
                String viewtoshow = cursor.getString(cursor.getColumnIndex(COL_VIEW_TO_SHOW));


                myrecipes = new MyRecipe(recipe_Id, image, sourcetitle, title,
                        notes, category, sourcewebsite, servings, cooktime, directions,
                        ingredients, bookmarked, viewtoshow);

                cursor.moveToNext();
            }
        }
        cursor.close();
        return myrecipes;
    }


    //search
    public List<MyRecipe> sortByTitle() {
        SQLiteDatabase db = getReadableDatabase();
//todo check variation with period and without out try putting coltitile in selction spot and only
        //todo collate no case in order by
        String orderby = COL_TITLE + " COLLATE NOCASE ASC";

        Cursor cursor = db.query(RECIPE_TABLE_NAME,
                null, null, null, null, null, orderby);

        List<MyRecipe> myrecipes = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                long recipeId = cursor.getInt(cursor.getColumnIndex(COL_ID));

                String image = cursor.getString(cursor.getColumnIndex(COL_IMAGE));
                String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                List ingredients = getAllIngredients(recipeId);
                String directions = cursor.getString(cursor.getColumnIndex(COL_DIRECTIONS));
                float servings = cursor.getFloat(cursor.getColumnIndex(COL_SERVINGS));
                float cooktime = cursor.getFloat(cursor.getColumnIndex(COL_COOKTIME));
                String category = cursor.getString(cursor.getColumnIndex(COL_CATEGORY));
                String notes = cursor.getString(cursor.getColumnIndex(COL_NOTES));
                String sourcetitle = cursor.getString(cursor.getColumnIndex(COL_SOURCETITLE));
                String sourcewebsite = cursor.getString(cursor.getColumnIndex(COL_SOURCEWEBSITE));
                int bookmarked = cursor.getInt(cursor.getColumnIndex(COL_BOOKMARKED));
                String viewtoshow = cursor.getString(cursor.getColumnIndex(COL_VIEW_TO_SHOW));


                MyRecipe recipeforshow = new MyRecipe(recipeId, image, sourcetitle, title,
                        notes, category, sourcewebsite, servings, cooktime, directions,
                        ingredients, bookmarked, viewtoshow);
                myrecipes.add(recipeforshow);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return myrecipes;
    }

    //search the titles or categories
    public List<MyRecipe> searchByTitleOrCategory(String query) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = COL_TITLE + " LIKE ? " + " OR " + COL_CATEGORY + " LIKE ?";

        Cursor cursor = db.query(RECIPE_TABLE_NAME,
                null,
                selection,
                new String[]{" % " + query + " % " + query + " % "}, null, null, null);

        List<MyRecipe> myrecipes = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                long recipeId = cursor.getInt(cursor.getColumnIndex(COL_ID));

                String image = cursor.getString(cursor.getColumnIndex(COL_IMAGE));
                String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                List ingredients = getAllIngredients(recipeId);
                 String directions = cursor.getString(cursor.getColumnIndex(COL_DIRECTIONS));
                float servings = cursor.getFloat(cursor.getColumnIndex(COL_SERVINGS));
                float cooktime = cursor.getFloat(cursor.getColumnIndex(COL_COOKTIME));
                String category = cursor.getString(cursor.getColumnIndex(COL_CATEGORY));
                String notes = cursor.getString(cursor.getColumnIndex(COL_NOTES));
                String sourcetitle = cursor.getString(cursor.getColumnIndex(COL_SOURCETITLE));
                String sourcewebsite = cursor.getString(cursor.getColumnIndex(COL_SOURCEWEBSITE));
                int bookmarked = cursor.getInt(cursor.getColumnIndex(COL_BOOKMARKED));
                String viewtoshow = cursor.getString(cursor.getColumnIndex(COL_VIEW_TO_SHOW));


                MyRecipe recipeforshow = new MyRecipe(recipeId, image, sourcetitle, title,
                        notes, category, sourcewebsite, servings, cooktime, directions,
                        ingredients, bookmarked, viewtoshow);
                myrecipes.add(recipeforshow);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return myrecipes;
    }

    public void removeRecipeFromCookbook(long recipeid) {

        SQLiteDatabase db = getWritableDatabase();
        String whereclause = recipeid + " = ? ";

        db.delete(RECIPE_TABLE_NAME, whereclause, new String[]{String.valueOf(COL_ID)});
        db.delete(INGREDIENTS_TABLE_NAME, whereclause, new String[]{String.valueOf(COL_RECIPE_ID)});
        db.close();

    }

    //ADD BOOKMARK
    public void addBookmark(long id) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BOOKMARKED, "1");
        db.update(RECIPE_TABLE_NAME,values,COL_ID+ " = "+ id,null );
        db.close();
    }

    //
    public void removeBookmark(long id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BOOKMARKED, "0");
        db.update(RECIPE_TABLE_NAME,values,COL_ID+ " = "+ id,null );
        db.close();
    }

    public List<MyRecipe> getBookmarkItems() {
        SQLiteDatabase db = getReadableDatabase();

        String selection = COL_BOOKMARKED + " = ?";

        Cursor cursor = db.query(RECIPE_TABLE_NAME,
                new String[]{},
                selection,
                new String[]{"1"},
                null,
                null,
                null);

        List<MyRecipe> myrecipes = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                long recipeId = cursor.getInt(cursor.getColumnIndex(COL_ID));

                String image = cursor.getString(cursor.getColumnIndex(COL_IMAGE));
                String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                List ingredients = getAllIngredients(recipeId);
                String directions = cursor.getString(cursor.getColumnIndex(COL_DIRECTIONS));
                float servings = cursor.getFloat(cursor.getColumnIndex(COL_SERVINGS));
                float cooktime = cursor.getFloat(cursor.getColumnIndex(COL_COOKTIME));
                String category = cursor.getString(cursor.getColumnIndex(COL_CATEGORY));
                String notes = cursor.getString(cursor.getColumnIndex(COL_NOTES));
                String sourcetitle = cursor.getString(cursor.getColumnIndex(COL_SOURCETITLE));
                String sourcewebsite = cursor.getString(cursor.getColumnIndex(COL_SOURCEWEBSITE));
                int bookmarked = cursor.getInt(cursor.getColumnIndex(COL_BOOKMARKED));
                String viewtoshow = cursor.getString(cursor.getColumnIndex(COL_VIEW_TO_SHOW));


                MyRecipe recipeforshow = new MyRecipe(recipeId, image, sourcetitle, title,
                        notes, category, sourcewebsite, servings, cooktime, directions,
                        ingredients, bookmarked, viewtoshow);
                myrecipes.add(recipeforshow);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return myrecipes;
    }

    public int searchBookmark(long recipeId) {
        SQLiteDatabase db = getReadableDatabase();


        String selection = COL_BOOKMARKED + " = ?";

        Cursor cursor = db.query(RECIPE_TABLE_NAME,
                new String[]{},
                selection,
                new String[]{"1"},
                null,
                null,
                null);

        int bookmarkSearch = 0;

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                recipeId = cursor.getInt(cursor.getColumnIndex(COL_ID));

                String image = cursor.getString(cursor.getColumnIndex(COL_IMAGE));
                String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                List ingredients = getAllIngredients(recipeId);
                String directions = cursor.getString(cursor.getColumnIndex(COL_DIRECTIONS));
                float servings = cursor.getFloat(cursor.getColumnIndex(COL_SERVINGS));
                float cooktime = cursor.getFloat(cursor.getColumnIndex(COL_COOKTIME));
                String category = cursor.getString(cursor.getColumnIndex(COL_CATEGORY));
                String notes = cursor.getString(cursor.getColumnIndex(COL_NOTES));
                String sourcetitle = cursor.getString(cursor.getColumnIndex(COL_SOURCETITLE));
                String sourcewebsite = cursor.getString(cursor.getColumnIndex(COL_SOURCEWEBSITE));
                int bookmarked = cursor.getInt(cursor.getColumnIndex(COL_BOOKMARKED));
                String viewtoshow = cursor.getString(cursor.getColumnIndex(COL_VIEW_TO_SHOW));

                bookmarkSearch = bookmarked;
                cursor.moveToNext();
            }
        }
        cursor.close();
        return bookmarkSearch;
    }

//--end bracket dont delete
}

