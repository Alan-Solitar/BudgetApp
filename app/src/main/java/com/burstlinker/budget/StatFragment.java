package com.burstlinker.budget;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Alan Solitar on 2016/01/18.
 */
public class StatFragment extends Fragment
{
    FragmentTransaction transaction;
    NumFrag nfrag;
    ChartFrag cfrag;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
       // db = new DBHandler(this,null,null,1);
        //db.getRecords();
        super.onCreate(savedInstanceState);
        nfrag = new NumFrag();
        cfrag = new ChartFrag();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.stat, container, false);
        if(nfrag.isAdded() && cfrag.isAdded() )
        {
            return view;
        }
        transaction = this.getChildFragmentManager().beginTransaction();
        transaction.add(R.id.container1, nfrag,"nfrag");
        transaction.add(R.id.container2, cfrag,"cfrag");
        transaction.commit();
        return view;
    }
}
