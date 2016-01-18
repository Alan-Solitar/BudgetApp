/**
 * Created by BurstLinker 2 on 2015/11/26.
 */
package com.burstlinker.budget;

import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;


public class DBHandler extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "budget.db";
    private static final String PURCHASE_TABLE = "items";
    private static final int VERSION =1;
    //columns
    private static final String COL_1 = "id";
    private static final String COL_2 = "name";
    private static final String COL_3 = "price";
    private static final String COL_4 = "date";
    private static final String COL_5 = "note";
    private static final String COL_6 = "category";



    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = "CREATE TABLE "+PURCHASE_TABLE + " ( "+
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COL_2 + " TEXT," +
                COL_3 + " DECIMAL(10,2),"+
                COL_4 + " INTEGER,"+
                COL_5 + " TEXT," +
                COL_6 + " TEXT" + ")";
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
        contentValues.put(COL_5, purchase.getNotePath());
        //I am storing the name of of the enum, not the int value
        contentValues.put(COL_6, purchase.getCategory().name());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(PURCHASE_TABLE, null, contentValues);
        db.close();
        return true;
    }
    public boolean deleteRecord(int id)
    {
        String query = "DELETE FROM"+PURCHASE_TABLE + " WHERE " +COL_1 +"=\""+ id+ "\"";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
        return true;
    }
    public float getAverage()
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT AVG("+COL_3+ ") FROM "+ PURCHASE_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null)
        {
            cursor.moveToLast();
        }
        float avg = cursor.getFloat(0);
        Log.e("Alan's tag", Float.toString(avg));
        return avg;
    }
    public float getSum()
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT SUM("+COL_3+ ") FROM "+ PURCHASE_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null)
        {
            cursor.moveToLast();
        }
        float sum = cursor.getFloat(0);
        Log.e("Alan's tag",Float.toString(sum));
        return sum;
    }
    public String getMode()
    {
        SQLiteDatabase db = getWritableDatabase();
        String query =
        "SELECT + " +COL_6+
        " FROM "+ PURCHASE_TABLE +
        " WHERE "+COL_3 +" = (SELECT MAX( " + COL_3 + ") FROM " + PURCHASE_TABLE +")";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToLast();
        String mode = cursor.getString(0);
        Log.e("Alan's tag",mode);
        return mode;
    }


    public ArrayList<Purchase> getRecords()
    {
        ArrayList<Purchase> purchases = new ArrayList<Purchase>();
        SQLiteDatabase db =getWritableDatabase();
        String query = "SELECT * FROM "+ PURCHASE_TABLE;
        Cursor resultSet = db.rawQuery(query,null);

        while(resultSet.moveToNext())
        {
            Purchase current = new Purchase();
            current.setID(resultSet.getInt(0));
            current.setName(resultSet.getString(1));
            current.setPrice(resultSet.getFloat(2));
            current.setDate(resultSet.getLong(3));
            current.setNotePath(resultSet.getString(4));
            current.setCategory(resultSet.getString(5));
            purchases.add(current);
        }
        resultSet.close();
        db.close();
        return purchases;
    }


}
























