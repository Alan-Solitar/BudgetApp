package com.burstlinker.budget;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Alan Solitar on 2016/01/25.
 */
public class PurchaseProvider extends ContentProvider
{
    //uri info
    static final String PROVIDER_NAME = "com.burstlinker.budget.PurchaseProvider";
    static final String purchasePath = "Purch";
    static final String purchaseCategoryPath = "cat1";
    static final String catOccurrencePath = "cat2";
    static final String URL = "content://" + PROVIDER_NAME +"/"+ purchasePath;
    static final String URL2 = "content://" + PROVIDER_NAME +"/"+ purchaseCategoryPath;
    static final String URL3 = "content://" + PROVIDER_NAME +"/"+ catOccurrencePath;
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final Uri CONTENT_URI_PURCHASE_CATEGORY = Uri.parse(URL2);
    static final Uri CONTENT_URI_CAT_OCCURRENCE = Uri.parse(URL3);

    //databse info
    static final String DATABASE_NAME = "budget.db";
    static final String TABLE_NAME = "items";
    static final int VERSION =1;
    //databse columns
    static final String ID = "id";
    static final String NAME = "name";
    static final String PRICE= "price";
    static final String DATE = "date";
    static final String NOTE = "note";
    static final String CATEGORY = "category";

    //uri
    static final UriMatcher uriMatcher;
    static final int PURCHASES=1;
    static final int PURCHASE_CATEGORY=2;
    static final int CATEGORY_OCCURRENCE=3;
    static
    {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME,purchasePath,PURCHASES);
        uriMatcher.addURI(PROVIDER_NAME,purchaseCategoryPath,PURCHASE_CATEGORY);
        uriMatcher.addURI(PROVIDER_NAME,catOccurrencePath,CATEGORY_OCCURRENCE);

    }

    //Query string
    private static final String DB_CREATE = "CREATE TABLE "+ TABLE_NAME + " ( "+
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            NAME + " TEXT," +
            PRICE + " DECIMAL(10,2),"+
            DATE + " INTEGER,"+
            NOTE + " TEXT," +
            CATEGORY + " TEXT" + ")";
    private static final String CAT_QUERY =
            "SELECT + " +CATEGORY+
                    " FROM "+ TABLE_NAME +
                    " WHERE "+PRICE +" = (SELECT MAX( " + PRICE + ") FROM " + TABLE_NAME +")";

    private static final String CAT_OCURRENCE_QUERY = "SELECT "+CATEGORY +", COUNT(*) FROM "+TABLE_NAME +
        " GROUP BY "+CATEGORY;

    //db object
    private SQLiteDatabase db;

    //projection map
    HashMap<String,String> values;





    @Override
    public boolean onCreate()
    {
        DBHandler handler = new DBHandler (getContext());
        db = handler.getWritableDatabase();
        if(db!=null)
        {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);
        Cursor cursor=null;
        switch(uriMatcher.match(uri))
        {
            case PURCHASES:
                queryBuilder.setProjectionMap(values);
                break;
            case PURCHASE_CATEGORY:
                cursor = db.rawQuery(CAT_QUERY,null);
                return cursor;
            case CATEGORY_OCCURRENCE:
                return cursor = db.rawQuery(CAT_OCURRENCE_QUERY,null);
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        cursor = queryBuilder.query(db,projection,selection,selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri)
    {
        switch(uriMatcher.match(uri))
        {
            case PURCHASES:
                return "vnd.android.cursor.dir/" +purchasePath;
            case PURCHASE_CATEGORY:
                return "vnd.android.cursor.item/" +purchaseCategoryPath;
            case CATEGORY_OCCURRENCE:
                return "vnd.android.cursor.item/" +catOccurrencePath;

            default:
                throw new IllegalArgumentException("Unsupported uri"+uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        long rowid = db.insert(TABLE_NAME,null,values);
        if(rowid>0)
        {
            Uri _uri =ContentUris.withAppendedId(CONTENT_URI,rowid);
            getContext().getContentResolver().notifyChange(_uri,null);
            return uri;
        }
        else
        {
            Toast.makeText(getContext(),"Insertion Failed",Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        int rowsDeleted =0;
        switch(uriMatcher.match(uri))
        {
            case PURCHASES:
                rowsDeleted = db.delete(TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported uri"+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        int rowsUpdated =0;
        switch(uriMatcher.match(uri))
        {
            case PURCHASES:
                rowsUpdated = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported uri"+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return rowsUpdated;
    }

    private static class DBHandler extends SQLiteOpenHelper
    {

        DBHandler(Context context)
        {
            super(context,DATABASE_NAME,null,VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
            db.execSQL(query);
            onCreate(db);
        }
    }

}



