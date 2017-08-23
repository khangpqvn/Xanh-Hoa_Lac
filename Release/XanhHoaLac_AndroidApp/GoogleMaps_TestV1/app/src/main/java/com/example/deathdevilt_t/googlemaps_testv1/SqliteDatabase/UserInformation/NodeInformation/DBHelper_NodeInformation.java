package com.example.deathdevilt_t.googlemaps_testv1.SqliteDatabase.UserInformation.NodeInformation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by DeathDevil.T_T on 02-Jun-17.
 *  private String  id;
 private LatLng position;
 private String description;
 private String phone;
 private boolean isDelete;
 private String version;
 */


public class DBHelper_NodeInformation extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "NodeDB";
    public static final String CONTACTS_TABLE_NAME = "nodeInformation";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_LAT = "lat";
    public static final String CONTACTS_COLUMN_LNG = "lng";
    public static final String CONTACTS_COLUMN_DESCRIPTION = "description";
    public static final String CONTACTS_COLUMN_PHONE_NUMBER = "phoneNumber";
    public static final String CONTACTS_COLUMN_IS_DELETE = "isDelete";
    public DBHelper_NodeInformation(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("creaete table nodeInformation" +
                "(id text, lat text, lng text, description text, phoneNumber text, isDelete text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS nodeInformation");
        onCreate(db);
    }
    public boolean insertContact( String id, String phoneNumber, String lat, String lng, String description,  boolean isDelete, String version){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("phoneNumber",phoneNumber);
        contentValues.put("lat",lat);
        contentValues.put("lng",lng);
        contentValues.put("description",description);
        contentValues.put("isDelete",isDelete);
        contentValues.put("version",version);
        return true;
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from nodeInformation",null);
        return res;
    }
    public boolean updateContact (String id, String phoneNumber, String lat, String lng, String description,  boolean isDelete, String version) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("phoneNumber",phoneNumber);
        contentValues.put("lat",lat);
        contentValues.put("lng",lng);
        contentValues.put("description",description);
        contentValues.put("isDelete",isDelete);
        contentValues.put("version",version);
        db.update("UserInformation", contentValues, "id=?", new String[] { } );
        return true;
    }

    public Integer deleteContact () {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("nodeInformation",
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
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_ID)));
            res.moveToNext();
        }
        return array_list;
    }
}
