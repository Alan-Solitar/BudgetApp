package com.burstlinker.budget;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Alan Solitar on 2016/01/28.
 */
public class QuickFrag extends android.support.v4.app.Fragment
{
    private ArrayList<Purchase> purchases = null;
    private MyAdapter adapter;
    //private DBHandler db;
    private RecyclerView recycle;
    private RecyclerView.LayoutManager myLayoutManager;
    private PurchaseHandler purchaseHandler = null;
    private Context context;
    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //db= new DBHandler(this,null,null,1);
        //get records to populate the recycler view
        context = getActivity();
        purchaseHandler = new PurchaseHandler(getActivity());
        purchases = purchaseHandler.getRecords(PurchaseHandler.LIMIT.THREE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.quick_page, container, false);
        recycle = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        adapter = new MyAdapter(purchases, context,this.getActivity().getSupportFragmentManager());
        myLayoutManager = new LinearLayoutManager(context);
        recycle.setAdapter(adapter);
        recycle.setLayoutManager(myLayoutManager);
        recycle.addItemDecoration(new DividerItemDecoration(context));
        return view;
    }

}
