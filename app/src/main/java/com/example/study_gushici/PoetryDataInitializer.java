package com.example.study_gushici;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class PoetryDataInitializer {
    public static void initializePoetryData(Context context) {
        PoetryDatabaseHelper dbHelper = new PoetryDatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        List<Poetry> poems = PoetryScraper.scrapePoems();
        for (Poetry poem : poems) {
            ContentValues values = new ContentValues();
            values.put(PoetryDatabaseHelper.COLUMN_TITLE, poem.getTitle());
            values.put(PoetryDatabaseHelper.COLUMN_AUTHOR, poem.getAuthor());
            values.put(PoetryDatabaseHelper.COLUMN_DYNASTY, poem.getDynasty());
            values.put(PoetryDatabaseHelper.COLUMN_CONTENT, poem.getContent());
            db.insert(PoetryDatabaseHelper.TABLE_NAME, null, values);
        }
        db.close();
    }
}