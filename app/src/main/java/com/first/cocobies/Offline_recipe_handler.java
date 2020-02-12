package com.first.cocobies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class Offline_recipe_handler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="client_offline_recipe";
    private static final String TABLE_PERSONNELDATA="personnel";
    private static final String KEY_ID="id";
    private static final String KEY_NAME="recipe_name";
    private static final String KEY_RECIPE_ID="recipe_id";
    private static final String KEY_LINK="link";
    private static final String KEY_DEC="product_dec";
    private static final String KEY_IMAGE="image";

    public Offline_recipe_handler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CLIENT_TABLE= "CREATE TABLE " + TABLE_PERSONNELDATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_LINK + " TEXT," + KEY_DEC + " TEXT," + KEY_IMAGE + " BLOB," + KEY_RECIPE_ID + " TEXT"  + ")";
        db.execSQL(CREATE_CLIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONNELDATA);
        onCreate(db);
    }



}
