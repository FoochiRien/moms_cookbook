package com.adrienne.cookbook_app.My_cookbook.db_cookbook;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Admin on 5/20/17.
 */

public class DBAssetHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "recipe.DB";
    private static final int DATABASE_VERSION = 1;


    public DBAssetHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}
