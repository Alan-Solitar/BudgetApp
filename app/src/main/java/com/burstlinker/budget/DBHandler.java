package com.burstlinker.budget;

import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

/**
 * Created by BurstLinker 2 on 2015/11/26.
 */
public class DBHandler extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "budget.db";
    private static final String PURCHASE_TABLE = "items";
    private static final int VERSION =1;
    //columns
    private static final String COL_1 = "id";
    private static final String COL_2 = "item";
    private static final String COL_3 = "price";
    private static final String COL_4 = "date";



    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = "CREATE TABLE "+PURCHASE_TABLE + " ( "+
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT "+
                COL_2 + " TEXT" +
                COL_3 + " DECIMAL(10,2)"+
                COL_4 + " INTEGER";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String query = "DROP TABLE IF EXISTS " + PURCHASE_TABLE;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean addRecord(Purchase purchase)
    {

        //extract data from item;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, purchase.getName());
        contentValues.put(COL_3, purchase.getPrice());
        contentValues.put(COL_4, purchase.getDate());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(PURCHASE_TABLE,null, contentValues);
        db.close();
        return true;
    }
    public boolean deleteRecord(String name)
    {
        String query = "DELETE FROM"+PURCHASE_TABLE + " WHERE " +COL_4 +"=\""+ name + "\"";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
        return true;
    }
    public Cursor getRecords()
    {
        SQLiteDatabase db =getWritableDatabase();
        String query = "SELECT * FROM "+ PURCHASE_TABLE;
        Cursor resultSet = db.rawQuery(query,null);
        return resultSet;
    }


}
























