package com.adrienne.cookbook_app.My_cookbook.db_cookbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    private static final String DIRECTIONS_TABLE_NAME = "directions";
    private static final String COL_RECIPE_DIRECTIONS = "recipe_directions_title";
    private static final String COL_DIRECTIONS = "directions";

    private static final String INGREDIENTS_TABLE_NAME = "ingredients";
    private static final String COL_RECIPE_INGREDIENTS = "recipe_ingredients_title";
    private static final String COL_INGREDIENTS = "ingredients";

    private static final String CREATE_RECIPE_TABLE = "CREATE TABLE" + RECIPE_TABLE_NAME + "(" +
            COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_IMAGE + "TEXT, " +
            COL_TITLE + "TEXT, " +
            COL_SERVINGS + "INTEGER, " +
            COL_COOKTIME + "INTEGER, " +
            COL_CATEGORY + "TEXT, " +
            COL_NOTES + "TEXT, " +
            COL_SOURCETITLE + "TEXT, " +
            COL_SOURCEWEBSITE + "TEXT, " +
            COL_BOOKMARKED + "INTEGER )";

    private static final String CREATE_DIRECTIONS_TABLE = "CREATE TABLE" +
            DIRECTIONS_TABLE_NAME + "(" +
            COL_ID + "INTEGER PRIMARY KEY, " +
            COL_RECIPE_DIRECTIONS + "TEXT, " +
            COL_DIRECTIONS + "TEXT )";

    private static final String CREATE_INGREDIENTS_TABLE = "CREATE TABLE" +
            INGREDIENTS_TABLE_NAME + "(" +
            COL_ID + "INTEGER PRIMARY KEY, " +
            COL_RECIPE_INGREDIENTS + "TEXT, " +
            COL_INGREDIENTS + "TEXT )";


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
        db.execSQL(CREATE_DIRECTIONS_TABLE);
        db.execSQL(CREATE_INGREDIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RECIPE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DIRECTIONS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + INGREDIENTS_TABLE_NAME);
        this.onCreate(db);
    }

    // add recipe to cookbook from api
    public void addApiRecipetoCookbook(String apiImage, String apiRecipe, String apiServing,
                                       String apiCategories, String apiWebsite, String apiUrl){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_IMAGE, apiImage );
        values.put(COL_TITLE, apiRecipe );
        values.put(COL_SERVINGS, apiServing);
        values.put(COL_COOKTIME, "");
        values.put(COL_CATEGORY, apiCategories);
        values.put(COL_NOTES, "");
        values.put(COL_SOURCETITLE, apiWebsite);
        values.put(COL_SOURCEWEBSITE, apiUrl);
        values.put(COL_BOOKMARKED, "");
        db.insert(RECIPE_TABLE_NAME,null, values);
        db.close();
    }

    //----Cookbook search. The user is able to search through recipes in their
    //----own cookbook.

//    public List<MyRecipe> getAllRecipes() {
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.query(RECIPE_TABLE_NAME,
//                null, null, null, null, null, null);
//
//        List<MyRecipe> myrecipes = new ArrayList<>();
//
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//           INGREDIENTS_TABLE_NAME.toString()
//                String image = cursor.getString(cursor.getColumnIndex(COL_IMAGE));
//                String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
//                String ingredients = cursor.getString(cursor.getColumnIndex(COL_INGREDIENTS));
//                String directions = cursor.getString(cursor.getColumnIndex(COL_DIRECTIONS));
//                float servings = cursor.getFloat(cursor.getColumnIndex(COL_SERVINGS));
//                float cooktime = cursor.getFloat(cursor.getColumnIndex(COL_COOKTIME));
//                String category = cursor.getString(cursor.getColumnIndex(COL_CATEGORY));
//                String notes = cursor.getString(cursor.getColumnIndex(COL_NOTES));
//                String sourcetitle = cursor.getString(cursor.getColumnIndex(COL_SOURCETITLE));
//                String sourcewebsite = cursor.getString(cursor.getColumnIndex(COL_SOURCEWEBSITE));
//                String bookmarked = cursor.getString(cursor.getColumnIndex(COL_BOOKMARKED));
//
//                MyRecipe recipeforshow = new MyRecipe( image,  sourcetitle, title,
//                        notes, category,sourcewebsite, servings, cooktime ,
//                        INGREDIENTS_TABLE_NAME.ingredients, directions, bookmarked);
//                myrecipes.add(recipeforshow);
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
//        return myrecipes;
//    }

//    public List<MyRecipe> getAllRecipes() {
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.query(RECIPE_TABLE_NAME,
//                null, null, null, null, null, null);
//        List<MyRecipe> myrecipe = new ArrayList<>();
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                long id = cursor.getLong(cursor.getColumnIndex(COL_ID));
//                String url = cursor.getString(cursor.getColumnIndex(COL_IMAGE));
//                String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
//                String ingredients = cursor.getString(cursor.getColumnIndex(COL_INGREDIENTS));
//                String directions = cursor.getString(cursor.getColumnIndex(COL_DIRECTIONS));
//                float servings = cursor.getFloat(cursor.getColumnIndex(COL_SERVINGS));
//                float cooktime = cursor.getFloat(cursor.getColumnIndex(COL_COOKTIME));
//                String category = cursor.getString(cursor.getColumnIndex(COL_CATEGORY));
//                String notes = cursor.getString(cursor.getColumnIndex(COL_NOTES));
//                String sourcetitle = cursor.getString(cursor.getColumnIndex(COL_SOURCETITLE));
//                String sourcewebsite = cursor.getString(cursor.getColumnIndex(COL_SOURCEWEBSITE));
//                String bookmarked = cursor.getString(cursor.getColumnIndex(COL_BOOKMARKED));
//
//                MyRecipe myRecipe = new MyRecipe(id, url, title, ingredients, directions,
//                        servings, cooktime, category, notes, sourcetitle, sourcewebsite, bookmarked);
//                myrecipe.add(myrecipe);
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
//        return myrecipe;
    //search
    //add
    //remove


//--end bracket dont delete
}

