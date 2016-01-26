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
    static final String PROVIDER_NAME = "com.burstlinker.budget.PurchaseProvider"
    static final String path = "purch";
    static final String URL = "content://" + PROVIDER_NAME +"/"+ path;
    static final Uri CONTENT_URI = Uri.parse(URL);

    //databse info
    static final String DATABASE_NAME = "budget.db";
    static final String TABLE_NAME = "items";
    static final int VERSION =1;
    //databse columns
    static final String COL_1 = "id";
    static final String COL_2 = "name";
    static final String COL_3 = "price";
    static final String COL_4 = "date";
    static final String COL_5 = "note";
    static final String COL_6 = "category";

    //uri
    static final UriMatcher uriMatcher;
    static final int PURCHASES=1;
    static final int PURCHASE_ID=2;
    static
    {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME,path,PURCHASES);
    }

    //Query string
    static final String DB_CREATE = "CREATE TABLE "+ TABLE_NAME + " ( "+
            COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COL_2 + " TEXT," +
            COL_3 + " DECIMAL(10,2),"+
            COL_4 + " INTEGER,"+
            COL_5 + " TEXT," +
            COL_6 + " TEXT" + ")";

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
        switch(uriMatcher.match(uri))
        {
            case PURCHASES:
                queryBuilder.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Unkown URI " + uri);
        }
        Cursor cursor = queryBuilder.query(db,projection,selection,selectionArgs,null,null,sortOrder);
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
                return "vnd.android.cursor.dir/" +path;
            case PURCHASE_ID
                return "vnd.android.cursor.item/" +path;
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
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return 0;
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



