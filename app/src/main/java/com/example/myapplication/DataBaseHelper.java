package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="MyApp.db";
    public static final String USERS_TABLE_NAME="user_table";
    public static final String COLUMN_ID="ID";
    public static final String COLUMN_USERNAME="USERNAME";
    public static final String COLUMN_PASSWORD="PASSWORD";
    public static final String COLUMN_INSTRUMENT="INSTRUMENT";
    public static final String COLUMN_ISBAND="ISBAND";
    public static final String COLUMN_DESCRIPTION="DESCRIPTION";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USERS_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT NOT NULL, PASSWORD TEXT NOT NULL, INSTRUMENT TEXT, ISBAND INTEGER NOT NULL CHECK (ISBAND IN (0,1)), DESCRIPTION TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertUser(String userName, String password, String instrument, int isBand, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, userName);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_INSTRUMENT, instrument);
        contentValues.put(COLUMN_ISBAND, isBand);
        contentValues.put(COLUMN_DESCRIPTION, description);
        long result = db.insert(USERS_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public String getUsers() {
        StringBuffer buffer = new StringBuffer();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + USERS_TABLE_NAME, null);

        while (result.moveToNext()) {
            buffer.append("ID: " + result.getString(0) + "\n");
            buffer.append("USERNAME: " + result.getString(1) + "\n");
            buffer.append("PASSWORD: " + result.getString(2) + "\n");
            buffer.append("INSTRUMENT: " + result.getString(3) + "\n");
            buffer.append("ISBAND: " + result.getString(4) + "\n");
        }

        return buffer.toString();
    }

    public User getUser(String username){
        StringBuffer buffer = new StringBuffer();
        SQLiteDatabase db = this.getWritableDatabase();
        User user = null;
        Cursor result = db.rawQuery("select * from " + USERS_TABLE_NAME + " where " + COLUMN_USERNAME + " like '" + username +"'", null);
        if(result.getCount() > 0){
            result.moveToNext();
            String userName = result.getString(1);
            String password = result.getString(2);
            String instrument = result.getString(3);
            boolean isBand = result.getInt(4) == 1 ? true : false;
            user = new User(userName, password, instrument, isBand);
        }
        return user;
    }
}
