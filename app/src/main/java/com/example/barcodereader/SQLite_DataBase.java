package com.example.barcodereader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLite_DataBase  extends SQLiteOpenHelper
{

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "Product";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "name";
    private static final String COL_3 = "price";
    private static final String COL_4 = "barcode";



    public SQLite_DataBase(@Nullable Context context) {
        super(context, TABLE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, barcode VARCHAR)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }



    public boolean insertData(String name, String price, String barcode)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, price);
        contentValues.put(COL_4, barcode);

        long result = db.insert(TABLE_NAME, null,contentValues);

        if (result==-1) { return false;}
        else
        { return true; }
    }


    public Cursor getData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }



    public Cursor getDataByBacode(String barCode)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE barcode ='"+barCode+"'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }



}
