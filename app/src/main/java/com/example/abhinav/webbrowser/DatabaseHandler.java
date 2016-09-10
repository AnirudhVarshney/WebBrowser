package com.example.abhinav.webbrowser;

/**
 * Created by ABHINAV on 02-06-2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_BOOKMARK = "bookmark";
    private static final String TABLE_History = "history";


    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_url = "url";
    private static final String KEY_TIME = "time";
    private static final String KEY_OTP = "otp";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_History + "("
       + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
        + KEY_url + " TEXT,"
                + KEY_TIME + " TEXT " +")";
        String CREATE_BOOKMARK_TABLE = "CREATE TABLE " + TABLE_BOOKMARK + "("
                + KEY_url + " TEXT" +")";
        db.execSQL(CREATE_HISTORY_TABLE);
        db.execSQL(CREATE_BOOKMARK_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_History);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKMARK);


        // Create tables again
        onCreate(db);
    }


    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addHistory(History_model history_model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_url, history_model.getUrl()); //  url
        values.put(KEY_TIME, history_model.getTime()); //  Time

        // Inserting Row
        db.insert(TABLE_History, null, values);
        db.close(); // Closing database connection
    }
    void addBookmark(Bookamark_model bookamark_model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_url, bookamark_model.getUrl()); //  url

        // Inserting Row
        db.insert(TABLE_BOOKMARK, null, values);
        db.close(); // Closing database connection
    }

    // Deleting Contacts
    public void clearHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_History);
    }
    public void clearSelectedHistory(String urls) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("ani in data",urls);
        db.execSQL("delete from " + TABLE_History+" where "+KEY_TIME+" = '"+urls+"'");
    }
    public void clearBookmark() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_BOOKMARK);
    }
    //Getting All Contacts
    public List<History_model> getAllHistory() {
        List<History_model> historyList = new ArrayList<History_model>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_History + " ORDER BY " + KEY_TIME +" DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                History_model history_model = new History_model();
                // contact.setID(Integer.parseInt(cursor.getString(0)));
                history_model.setUrl(cursor.getString(1));
                history_model.setTime(cursor.getString(2));
                // Adding contact to list
                historyList.add(history_model);
            } while (cursor.moveToNext());
        }

        // return contact list
        return historyList;
    }
    public List<Bookamark_model> getAllBookmarks() {
        List<Bookamark_model> bookmarkList = new ArrayList<Bookamark_model>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BOOKMARK + " ORDER BY " + KEY_url +" DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Bookamark_model bookamark_model = new Bookamark_model();
                // contact.setID(Integer.parseInt(cursor.getString(0)));
                bookamark_model.setUrl(cursor.getString(0));

                // Adding contact to list
                bookmarkList.add(bookamark_model);
            } while (cursor.moveToNext());
        }

        // return contact list
        return bookmarkList;
    }

}