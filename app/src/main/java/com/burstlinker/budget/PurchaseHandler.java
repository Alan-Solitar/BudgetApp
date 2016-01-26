package com.burstlinker.budget;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

/**
 * Created by Alan Solitar on 2016/01/26.
 */
public class PurchaseHandler
{
Context context;
    PurchaseHandler(Context context)
    {
        this.context=context;
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
        Uri uri = context.getContentResolver().insert(PurchaseProvider.CONTENT_URI, contentValues);
    }
}
