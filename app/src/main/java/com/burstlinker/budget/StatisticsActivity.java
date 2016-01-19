package com.burstlinker.budget;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by Alan Solitar on 2016/01/18.
 */
public class StatisticsActivity extends Activity
{
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
       // db = new DBHandler(this,null,null,1);
        //db.getRecords();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        NumFrag nfrag = new NumFrag();
        ChartFrag cfrag = new ChartFrag();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.container1, nfrag,"nfrag");
        transaction.add(R.id.container2, cfrag,"cfrag");
        transaction.commit();

    }
}
