package com.example.study_gushici;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PoetryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "poetry.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "poems";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_DYNASTY = "dynasty";
    public static final String COLUMN_CONTENT = "content";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT, " +
            COLUMN_AUTHOR + " TEXT, " +
            COLUMN_DYNASTY + " TEXT, " +
            COLUMN_CONTENT + " TEXT);";

    public PoetryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}