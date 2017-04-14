package com.example.arya.easecentral;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by arya's on 4/10/2017.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "myapp.db";
    public static final int DB_VERSION = 1;

    //table for password
    public static final String USER_TABLE = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASS = "password";

    //table for storing favourites
    public static final String USER_TABLE_FAVS = "favs";
    public static final String COLUMN_ID_FAVS = "_id";
    public static final String COLUMN_IMAGES_FAVS = "images";
    public static final String COLUMN_TITLE_FAVS = "title";
    public static final String COLUMN_URL_FAVS = "url";
    public static final String COLUMN_COMMENTS_FAVS = "comments";

    //table for storing image links
    public static final String USER_TABLE_IMAGES = "images";
    public static final String COLUMN_ID_IMAGES = "_id";
    public static final String COLUMN_POSITION_IMAGES = "position";
    public static final String COLUMN_LINKS_IMAGES = "imageLinks";

    //creating the table for the credentials
    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
             + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
             + COLUMN_USERNAME + " TEXT,"
             + COLUMN_PASS + " TEXT);";

    //creating the table for favorites
    public static final String CREATE_TABLE_FAVS = "CREATE TABLE " + USER_TABLE_FAVS + "("
            + COLUMN_ID_FAVS + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_IMAGES_FAVS + " TEXT,"
            + COLUMN_TITLE_FAVS + " TEXT,"
            + COLUMN_URL_FAVS + " TEXT,"
            + COLUMN_COMMENTS_FAVS + " TEXT);";

    //creating the table for storing images
    public static final String CREATE_TABLE_IMAGES = "CREATE TABLE " + USER_TABLE_IMAGES + "("
            + COLUMN_ID_IMAGES + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_POSITION_IMAGES + " INTEGER,"
            + COLUMN_LINKS_IMAGES + " TEXT);";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    //creating the tables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_FAVS);
        sqLiteDatabase.execSQL(CREATE_TABLE_IMAGES);
    }

    //updating the tables in case of change
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_FAVS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_IMAGES);
        onCreate(sqLiteDatabase);
    }

    //adding users with their respective username and password to the credentials table
    public void addUser(String username, String password){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASS, password);
        db.insert(USER_TABLE, null, values);
    }

    //querying the credentials table to get the particular name and password
    public  boolean getUser(String username, String password) {
        String selectQuery = "select * from  " + USER_TABLE + " where " +
                COLUMN_USERNAME + " = " + "'" + username + "'" + " and " + COLUMN_PASS + " = " + "'" + password + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        return false;
    }

    //adding the favourites of a particular user to the table (images, titles,links and comments aee added)
    public void addUserFavs(int position,String title, String url, String comments){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        String imageLink=getImageLink(position);
        Log.i("Arya","PositionSent"+position);
        Log.i("Arya","LinkReceived"+imageLink.toString());
        values.put(COLUMN_IMAGES_FAVS,imageLink);
        values.put(COLUMN_TITLE_FAVS, title);
        values.put(COLUMN_URL_FAVS, url);
        values.put(COLUMN_COMMENTS_FAVS, comments);
        db.insert(USER_TABLE_FAVS, null, values);
    }

    //getting information from the favourites table
    public Cursor getInformation(SQLiteDatabase db){
         Cursor cursor;
         String[] projection = {COLUMN_IMAGES_FAVS,COLUMN_TITLE_FAVS,COLUMN_URL_FAVS,COLUMN_COMMENTS_FAVS};
         cursor=db.query(USER_TABLE_FAVS,projection,null,null,null,null,null);
         return cursor;
    }

    //adding the URL links to the links table
    public void addLinks(ArrayList<String> image){
        if (!checkForTables()){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            for (int i = 0; i < image.size(); i++) {
                values.put(COLUMN_POSITION_IMAGES, i);
                values.put(COLUMN_LINKS_IMAGES, image.get(i));
                db.insert(USER_TABLE_IMAGES, null, values);
            }
        }
    }

    //getting the particular link from the links table
    public String getImageLink(int position){

        String selectQuery = "select * from  " + USER_TABLE_IMAGES + " where " +
                COLUMN_POSITION_IMAGES + " = " + "'" + position + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext()){
            String imageLink=cursor.getString(2);
            buffer.append(imageLink);
            break;
        }
        cursor.close();
        return buffer.toString();
    }

    //checking if the table is empty
    public boolean checkForTables(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " +USER_TABLE_IMAGES, null);
        if(cursor != null){
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            if(count > 0){
                return true;
            }
            cursor.close();
        }
        return false;
    }

    //checking if a particular URL exist in the table
    public boolean ifItemExists(String url) {
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor;
        String checkQuery = "SELECT " + COLUMN_URL_FAVS + " FROM " + USER_TABLE_FAVS +
                                    " WHERE " + COLUMN_URL_FAVS + "= '"+url + "'";
        cursor= db.rawQuery(checkQuery,null);
        if (cursor.getCount() > 0){
            return true;
        }
        cursor.close();
        return false;
    }

    //deleting favourites table and links table on user logout
    public void deleteAll(){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(USER_TABLE_FAVS,null,null);
        db.delete(USER_TABLE_IMAGES,null,null);
        db.close();
    }

}
