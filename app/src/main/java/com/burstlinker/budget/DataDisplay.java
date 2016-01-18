package com.burstlinker.budget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class DataDisplay extends AppCompatActivity
{
    private ArrayList<Purchase> purchases=null;
    private MyAdapter adapter;
    private DBHandler db;
    private RecyclerView recycle;
    private RecyclerView.LayoutManager myLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);
        db= new DBHandler(this,null,null,1);
        purchases = db.getRecords();
        recycle = (RecyclerView) findViewById(R.id.my_recycler_view);
        myLayoutManager = new LinearLayoutManager(this);
        recycle.setLayoutManager(myLayoutManager);



        adapter = new MyAdapter(purchases,this);
        recycle.setAdapter(adapter);

    }

    @Override
    protected void onStart()
    {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
