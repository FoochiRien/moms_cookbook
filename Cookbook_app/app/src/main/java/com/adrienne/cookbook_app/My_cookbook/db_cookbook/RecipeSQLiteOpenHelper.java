package com.adrienne.cookbook_app.My_cookbook.db_cookbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.adrienne.cookbook_app.My_cookbook.Directions;
import com.adrienne.cookbook_app.My_cookbook.Ingredients;
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
    //todo add col for view to show add to DB

    private static final String DIRECTIONS_TABLE_NAME = "directions";
    private static final String COL_RECIPE_D_ID = "recipe_directions_id";
    private static final String COL_DIRECTIONS = "directions";

    private static final String INGREDIENTS_TABLE_NAME = "ingredients";
    private static final String COL_RECIPE_ID = "recipe_ingredients_id";
    private static final String COL_INGREDIENTS = "ingredients";

    private static final String CREATE_RECIPE_TABLE = "CREATE TABLE" + RECIPE_TABLE_NAME + "(" +
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
            COL_VIEW_TO_SHOW + " TEXT )";


    private static final String CREATE_DIRECTIONS_TABLE = "CREATE TABLE" +
            DIRECTIONS_TABLE_NAME + "(" +
            COL_ID + " INTEGER PRIMARY KEY, " +
            COL_RECIPE_D_ID + " INTEGER, " +
            COL_DIRECTIONS + " TEXT )";

    private static final String CREATE_INGREDIENTS_TABLE = "CREATE TABLE" +
            INGREDIENTS_TABLE_NAME + "(" +
            COL_ID + " INTEGER PRIMARY KEY, " +
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
                                           String manualNotes, String manualBookmark) {

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
        db.insert(RECIPE_TABLE_NAME, null, values);
        db.close();
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
                List directions = getAllDirections(recipeId);
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



    public List<Ingredients> getAllIngredients(long recipeid) {
        SQLiteDatabase db = getReadableDatabase();

//        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
//        builder.setTables(RECIPE_TABLE_NAME + " JOIN " + INGREDIENTS_TABLE_NAME +
//                " ON " + RECIPE_TABLE_NAME + "." + COL_ID +
//                " = " + INGREDIENTS_TABLE_NAME + "." + COL_RECIPE_ID);




        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(RECIPE_TABLE_NAME + " JOIN " + INGREDIENTS_TABLE_NAME +
                " ON " + COL_ID +
                " = " + COL_RECIPE_ID);

        String selection = recipeid + "";

        Cursor cursor = builder.query(db,
                null, selection, null, null, null, null, null);

        List<Ingredients> myingredients = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                long recipe_id = cursor.getLong(cursor.getColumnIndex(COL_RECIPE_ID));
                String ingredient = cursor.getString(cursor.getColumnIndex(COL_INGREDIENTS));

                myingredients.add(new Ingredients(ingredient));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return myingredients;


    }

    public void insertRecipe(MyRecipe myRecipe){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_IMAGE, myRecipe.getImage());
        values.put(COL_TITLE, myRecipe.getTitle());
        values.put(COL_SERVINGS, myRecipe.getServings());
        values.put(COL_COOKTIME, myRecipe.getCookTime());
        values.put(COL_CATEGORY, myRecipe.getCategory());
        values.put(COL_NOTES, myRecipe.getNotes());
        values.put(COL_SOURCETITLE, myRecipe.getSourceTitle());
        values.put(COL_SOURCEWEBSITE, myRecipe.getSourceUrl());
        values.put(COL_BOOKMARKED, myRecipe.getBookmarked());
        values.put(COL_VIEW_TO_SHOW, myRecipe.getViewToShow());


        long recipeId = db.insert(RECIPE_TABLE_NAME, null, values);
        for(Ingredients ingredient : myRecipe.getIngredients())
        {
            insertIngredients(ingredient, recipeId);
        }
        for(Directions directions : myRecipe.getDirections())
        {
            insertDirections(directions, recipeId);
        }


    }

    public void insertIngredients(Ingredients ingredient, long recipeId){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_RECIPE_ID, recipeId);
        values.put(COL_INGREDIENTS, ingredient.getIngredients());
        db.insert(INGREDIENTS_TABLE_NAME, null, values);
    }

    public void insertDirections(Directions direction, long recipeId){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_RECIPE_ID, recipeId);
        values.put(COL_DIRECTIONS, direction.getDirections());
        db.insert(DIRECTIONS_TABLE_NAME, null, values);
    }

    public List<Directions> getAllDirections(long recipeid) {

        SQLiteDatabase db = getReadableDatabase();

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(RECIPE_TABLE_NAME + " JOIN " + DIRECTIONS_TABLE_NAME +
                " ON " + RECIPE_TABLE_NAME + "." + COL_ID +
                " = " + DIRECTIONS_TABLE_NAME + "." + COL_RECIPE_D_ID);

        String selection = recipeid + "";

        Cursor cursor = builder.query(db,
                null, selection, null, null, null, null);

        List<Directions> mydirections = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                long recipe_id = cursor.getLong(cursor.getColumnIndex(COL_RECIPE_D_ID));
                String directions = cursor.getString(cursor.getColumnIndex(COL_DIRECTIONS));

                mydirections.add(new Directions(directions));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return mydirections;
    }

    public List<MyRecipe> getRecipe(long recipeId) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = COL_RECIPE_ID + " = ?";
        String recipe_id = Long.toString(recipeId);
        Cursor cursor = db.query(RECIPE_TABLE_NAME,
                new String[]{},
                selection,
                new String[]{recipe_id},
                null,
                null,
                null);

        List<MyRecipe> myrecipes = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                long recipe_Id = cursor.getInt(cursor.getColumnIndex(COL_ID));

                String image = cursor.getString(cursor.getColumnIndex(COL_IMAGE));
                String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                List ingredients = getAllIngredients(recipeId);
                List directions = getAllDirections(recipeId);
                float servings = cursor.getFloat(cursor.getColumnIndex(COL_SERVINGS));
                float cooktime = cursor.getFloat(cursor.getColumnIndex(COL_COOKTIME));
                String category = cursor.getString(cursor.getColumnIndex(COL_CATEGORY));
                String notes = cursor.getString(cursor.getColumnIndex(COL_NOTES));
                String sourcetitle = cursor.getString(cursor.getColumnIndex(COL_SOURCETITLE));
                String sourcewebsite = cursor.getString(cursor.getColumnIndex(COL_SOURCEWEBSITE));
                int bookmarked = cursor.getInt(cursor.getColumnIndex(COL_BOOKMARKED));
                String viewtoshow = cursor.getString(cursor.getColumnIndex(COL_VIEW_TO_SHOW));


                MyRecipe recipeforshow = new MyRecipe(recipe_Id, image, sourcetitle, title,
                        notes, category, sourcewebsite, servings, cooktime, directions,
                        ingredients, bookmarked, viewtoshow);
                myrecipes.add(recipeforshow);
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
                List directions = getAllDirections(recipeId);
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
                List directions = getAllDirections(recipeId);
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
        db.delete(DIRECTIONS_TABLE_NAME, whereclause, new String[]{String.valueOf(COL_RECIPE_D_ID)});
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
                List directions = getAllDirections(recipeId);
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
                List directions = getAllDirections(recipeId);
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

