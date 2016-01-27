package com.burstlinker.budget;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import android.net.Uri;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alan Solitar on 2016/01/26.
 */
public class PurchaseHandler
{
    String price=null;
    Uri uriAll=null;
    Uri uriCategory=null;
    Uri uriCatOccurrence=null;
    ContentResolver resolver=null;
    Context context=null;
    PurchaseHandler(Context context)
    {
        this.context=context;
        resolver= context.getContentResolver();
        uriAll = PurchaseProvider.CONTENT_URI;
        uriCategory= PurchaseProvider.CONTENT_URI_PURCHASE_CATEGORY;
        uriCatOccurrence = PurchaseProvider.CONTENT_URI_CAT_OCCURRENCE;
        price = PurchaseProvider.PRICE;
    }

    public void insert(Purchase purchase)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PurchaseProvider.NAME, purchase.getName());
        contentValues.put(PurchaseProvider.PRICE, purchase.getPrice());
        contentValues.put(PurchaseProvider.DATE, purchase.getDate());
        contentValues.put(PurchaseProvider.NOTE, purchase.getNotePath());
        //I am storing the name of of the enum, not the int value
        contentValues.put(PurchaseProvider.CATEGORY, purchase.getCategory().name());
        Uri _uri = resolver.insert(uriAll, contentValues);
    }
    public ArrayList<Purchase> getRecords()
    {
        //array to store purchase items
        ArrayList<Purchase> purchases = new ArrayList<Purchase>();
        //the columns we want
        String[] projection = {PurchaseProvider.ID,PurchaseProvider.NAME,PurchaseProvider.PRICE,PurchaseProvider.DATE,
                PurchaseProvider.NOTE,PurchaseProvider.CATEGORY};
        Cursor resultSet = resolver.query(uriAll, projection, null, null, null);
        Purchase current;
        while(resultSet.moveToNext())
        {
            current = new Purchase();
            current.setID(resultSet.getInt(0));
            current.setName(resultSet.getString(1));
            current.setPrice(resultSet.getFloat(2));
            current.setDate(resultSet.getLong(3));
            current.setNotePath(resultSet.getString(4));
            current.setCategory(resultSet.getString(5));
            purchases.add(current);
        }
        resultSet.close();
        return purchases;
    }


    public boolean deleteRecord(int id)
    {
        String idToDelete = Integer.toString(id);
        String where = "id = ?";
        long idDelted = resolver.delete(uriAll, where, new String[]{idToDelete});
        return true;
    }

    public float getAverage()
    {
        float avg=0;
        String[] projection = {"AVG("+PurchaseProvider.PRICE+ ") AS average"};
        Cursor cursor = resolver.query(uriAll, projection, null, null, null);
        if(cursor!=null&&cursor.getCount()>0)
        {
            cursor.moveToLast();
            avg = cursor.getFloat(0);
            Log.e("Alan's tag", Float.toString(avg));
        }
        return avg;
    }
    public float getSum()
    {
        float sum=0;
        String[] projection = {"SUM("+price+ ") AS price_sum"};
        Cursor cursor = resolver.query(uriAll,projection,null,null,null);

        if(cursor!=null&&cursor.getCount()>0)
        {
            cursor.moveToLast();

            sum = cursor.getFloat(0);
            Log.e("Alan's tag", Float.toString(sum));
        }
        return sum;
    }
    public String getMode()
    {
        Cursor cursor = resolver.query(uriCategory,null,null,null,null);
        String mode = "";

        if(cursor!=null&&cursor.getCount()>0)
        {
            cursor.moveToLast();
            mode = cursor.getString(0);
            Log.e("Alan's tag", mode);
        }
        return mode;
    }

    public HashMap<String,Integer> getCategoryOcurrences()
    {
        String cat;
        int occurences;
        HashMap<String,Integer> map = new HashMap<>();


        Cursor cursor = resolver.query(uriCatOccurrence,null,null,null,null);
        while(cursor.moveToNext())
        {
            cat=cursor.getString(0);
            occurences=cursor.getInt(1);
            map.put(cat,occurences);
        }


        return map;
    }
}
