package com.example.deathdevilt_t.googlemaps_testv1.SqliteDatabase.UserInformation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by DeathDevil.T_T on 01-Jun-17.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserDB";
    public static final String CONTACTS_TABLE_NAME = "UserInformation";
    public static final String CONTACTS_COLUMN_DISPLAY_NAME = "displayName";
    public static final String CONTACTS_COLUMN_EMAIL_ADDRESS = "email";
    public static final String CONTACTS_COLUMN_IMAGE_URI = "imageUri";
    private Context Context2;
    private SQLiteDatabase dbSQLite;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbSQLite = db;
            db.execSQL("CREATE TABLE UserInformation" +
                    "(displayName text, email text, imageUri text)"
                    );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS UserInformation");
        onCreate(db);
    }
    public boolean insertContact(String displayName,String email,String imageUri){

        SQLiteDatabase db = this.getWritableDatabase();

//        SQLiteDatabase db = this.getWritableDatabase();
//        String ROW1 = "INSERT INTO " + CONTACTS_TABLE_NAME + " ("
//                + CONTACTS_COLUMN_DISPLAY_NAME + ", " + CONTACTS_COLUMN_EMAIL_ADDRESS + ", "
//                + CONTACTS_COLUMN_IMAGE_URI + ") Values ('"+displayName+"', '"+email+"', '"+imageUri+"')";
//        db.execSQL(ROW1);
        return true;
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from UserInformation",null);
        return res;
    }
    public boolean updateContact (String displayName, String email, String imageUri) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("displayName", displayName);
        contentValues.put("email", email);
        contentValues.put("imageUri", imageUri);
        dbSQLite.update("UserInformation", contentValues, "", new String[] { } );
        return true;
    }

    public Integer deleteContact () {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "",
                new String[] {  });
    }

    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from UserInformation", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_DISPLAY_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
